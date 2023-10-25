package com.feiwanghub.subdataflownew.AggregationWithCoGroupBy;

import com.feiwanghub.subdataflownew.AggregationWithCoGroupBy.dto.RiskProto;
import com.feiwanghub.subdataflownew.AggregationWithCoGroupBy.dto.TradeProto;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.values.KV;

/**
 * sum all risk values of a counterparty
 */
public class RiskValuebyCounterpartyFn extends DoFn<KV<String, CoGbkResult>, KV<String, Double>> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        KV<String, CoGbkResult> coGroupByElement = c.element();
        /**
         * A trade has only one counterparty, so we can use getOnly() to get the counterparty.
         */
        TradeProto.Trade trade = coGroupByElement.getValue().getOnly(MainAggregationWithCoGroupBy.counterpartyByTradeIdTag);

        /**
         * TODO learn
         * A trade can have multiple risks, so we use getAll() to get all the risks.
         */
        Iterable<RiskProto.Risk> risks = coGroupByElement.getValue().getAll(MainAggregationWithCoGroupBy.riskValueByTradeIdTag);

        Double value = Double.valueOf(0.0);
        for (RiskProto.Risk risk : risks) {
            value += risk.getValue();
        }
        c.output(KV.of(trade.getCounterparty(), value));
    }

}
