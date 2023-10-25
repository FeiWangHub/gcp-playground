package com.feiwanghub.subdataflownew.AggregationWithCoGroupBy;

import com.feiwanghub.subdataflownew.AggregationWithCoGroupBy.dto.TradeProto;
import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class CounterpartyByTradeIdFn extends DoFn<TableRow, KV<String, TradeProto.Trade>> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        TableRow tradeRow = c.element();

        TradeProto.Trade trade = TradeProto.Trade.builder()
            .tradeId(tradeRow.get("trade_id").toString())
            .ccy(tradeRow.get("ccy").toString())
            .counterparty(tradeRow.get("counterparty").toString())
            .build();

        c.output(KV.of(tradeRow.get("trade_id").toString(), trade));
    }

}
