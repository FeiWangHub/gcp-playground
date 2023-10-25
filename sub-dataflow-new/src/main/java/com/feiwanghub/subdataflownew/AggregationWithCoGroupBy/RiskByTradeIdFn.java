package com.feiwanghub.subdataflownew.AggregationWithCoGroupBy;

import com.feiwanghub.subdataflownew.AggregationWithCoGroupBy.dto.RiskProto;
import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class RiskByTradeIdFn extends DoFn<TableRow, KV<String, RiskProto.Risk>> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        TableRow riskRow = c.element();

        RiskProto.Risk risk = RiskProto.Risk.builder()
            .riskId(riskRow.get("risk_id").toString())
            .tradeId(riskRow.get("trade_id").toString())
            .marketId(riskRow.get("market_id").toString())
            .value(Double.parseDouble(riskRow.get("value").toString()))
            .build();

        c.output(KV.of(riskRow.get("trade_id").toString(), risk));
    }
}
