package com.feiwanghub.subdataflownew.AggregationWithCoGroupBy;

import com.feiwanghub.subdataflownew.AggregationWithCoGroupBy.dto.RiskProto;
import com.feiwanghub.subdataflownew.AggregationWithCoGroupBy.dto.TradeProto;
import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableReference;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.transforms.join.CoGroupByKey;
import org.apache.beam.sdk.transforms.join.KeyedPCollectionTuple;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TupleTag;

import java.io.IOException;
import java.util.List;

import static com.feiwanghub.subdataflownew.AggregationWithCoGroupBy.MockData.mockRiskRows;
import static com.feiwanghub.subdataflownew.AggregationWithCoGroupBy.MockData.mockTradeRows;

/**
 * input 2 bigquery tables, data from risk.csv and trades.csv
 * risk.csv has 4 columns: risk_id:str, trade_id:str, market_id:str, value:float
 * trades.csv has 3 columns: trade_id:str, ccy:str, counterparty:str
 * 1 Trade -> Many Risk
 * 1 CounterParty -> Many Trade
 */
@Slf4j
public class MainAggregationWithCoGroupBy {

    public static final TupleTag<TradeProto.Trade> counterpartyByTradeIdTag = new TupleTag<>();
    public static final TupleTag<RiskProto.Risk> riskValueByTradeIdTag = new TupleTag<>();

    private TableReference getTradeTable(AWCGBOptions options) {
        return new TableReference()
            .setProjectId(options.getProjectId())
            .setDatasetId(options.getDatasetId())
            .setTableId(options.getTradeTable());
    }

    private TableReference getRiskTable(AWCGBOptions options) {
        return new TableReference()
            .setProjectId(options.getProjectId())
            .setDatasetId(options.getDatasetId())
            .setTableId(options.getRiskTable());
    }

    private TableReference getResultsTable(AWCGBOptions options) {
        return new TableReference()
            .setProjectId(options.getProjectId())
            .setDatasetId(options.getDatasetId())
            .setTableId(options.getResultsTable());
    }

    private TableSchema getResultSchema() {
        TableSchema schema = new TableSchema();
        List<TableFieldSchema> fields = List.of(
            new TableFieldSchema().setName("counterparty").setType("STRING").setMode("REQUIRED"),
            new TableFieldSchema().setName("risk_value").setType("FLOAT").setMode("REQUIRED")
        );

        schema.setFields(fields);
        return schema;
    }

    private void runPipeline(String[] args) throws IOException {
        PipelineOptionsFactory.register(AWCGBOptions.class);
        AWCGBOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(AWCGBOptions.class);
        Pipeline pipeline = Pipeline.create(options);

//        PCollection<TableRow> tradeRows = pipeline.apply("Read trade table from BigQuery",
//            BigQueryIO.readTableRows().from(getTradeTable(options)));
        PCollection<TableRow> tradeRows = pipeline.apply(Create.of(mockTradeRows()));

        /**
         * Convert trade table to KV (key = tradeid, value = trade proto)
         * NOTE: a Counterparty has many trades
         */
        PCollection<KV<String, TradeProto.Trade>> counterpartyByTradeIdColl =
            tradeRows.apply("Convert trade table to KV (key = tradeid)",
                ParDo.of(new CounterpartyByTradeIdFn()));

//        PCollection<TableRow> riskRows = pipeline.apply("Read risk table from BigQuery",
//            BigQueryIO.readTableRows().from(getRiskTable(options)));
        PCollection<TableRow> riskRows = pipeline.apply(Create.of(mockRiskRows()));

        /**
         * Convert risk table to KV (key = tradeid, value = risk proto)
         * NOTE: a risk is associated with a trade, but a trade can have many risks
         */
        PCollection<KV<String, RiskProto.Risk>> riskByTradeIdColl =
            riskRows.apply("Convert risk table to KV (key = tradeid)",
                ParDo.of(new RiskByTradeIdFn()));

        /**
         * Join the Trade proto's keyed by trade id, with the Risk proto's keyed by trade id
         *
         */
        PCollection<KV<String, CoGbkResult>> coGroupByResult = KeyedPCollectionTuple
            .of(counterpartyByTradeIdTag, counterpartyByTradeIdColl)
            .and(riskValueByTradeIdTag, riskByTradeIdColl)
            .apply("join counterparty with risk via trade id", CoGroupByKey.create());

        /**
         * For each counterparty, sum all the risk values TODO deep dive
         */
        PCollection<KV<String, Double>> counterPartByRiskColl = coGroupByResult
            .apply("use the to join to pivot risk by counterparty", ParDo.of(new RiskValuebyCounterpartyFn()));

        /**
         * counterPartByRiskColl is now a PCollection which is likely to have many repeted counterparty keys
         * because a counterparty has many trades
         * Perform another group by - which will result in collating all the risks into a list by counterparty
         */
        PCollection<KV<String, Iterable<Double>>> riskGroupByCCP = counterPartByRiskColl
            .apply("Assemble list of list by counterparty", GroupByKey.create());

        /**
         * Now aggregate the list of risks by counterparty
         */
        PCollection<KV<String, Double>> riskByCounterparty = riskGroupByCCP
            .apply("Aggregate list of list by counterparty", ParDo.of(new AggregateRiskByCCPFn()));
        riskByCounterparty.apply("Echo to screen", ParDo.of(new EchoResultsFn()));

        /**
         * Convert KV to TableRow of BigQuery, write to BigQuery
         */
        PCollection<TableRow> resultRows = riskByCounterparty
            .apply("Convert from KV<String, Double> to TableRow", ParDo.of(new BuildRowFn()));

        if (options.getIsWriteToBigQuery()) {
            resultRows.apply("Write results to BigQuery",
                BigQueryIO.writeTableRows()
                    .withSchema(getResultSchema())
                    .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE)
                    .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                    .to(getResultsTable(options)));
        }

        pipeline.run().waitUntilFinish();
    }

    public static void main(String[] args) {
        MainAggregationWithCoGroupBy mainAggregationWithCoGroupBy = new MainAggregationWithCoGroupBy();
        try {
            mainAggregationWithCoGroupBy.runPipeline(args);
        } catch (IOException e) {
            log.error("error running pipeline", e);
        }
    }

    public interface AWCGBOptions extends PipelineOptions {

        @Description("Google project id")
        @Default.String("gcp-project-id")
        String getProjectId();

        void setProjectId(String value);

        @Description("BigQuery dataset id")
        @Default.String("dataflow_demo")
        String getDatasetId();

        void setDatasetId(String value);

        @Description("BigQuery table id for trade data")
        @Default.String("trade_table")
        String getTradeTable();

        void setTradeTable(String value);

        @Description("BigQuery table id for risk data")
        @Default.String("risk_table")
        String getRiskTable();

        void setRiskTable(String value);

        @Description("BigQuery table id for results data")
        @Default.String("results_table")
        String getResultsTable();

        void setResultsTable(String value);

        @Description("Write results to BigQuery?")
        @Default.Boolean(false)
        boolean getIsWriteToBigQuery();

        void setIsWriteToBigQuery(boolean value);
    }

}
