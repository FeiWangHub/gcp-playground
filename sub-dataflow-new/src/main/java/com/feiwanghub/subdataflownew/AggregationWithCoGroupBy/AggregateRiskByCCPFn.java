package com.feiwanghub.subdataflownew.AggregationWithCoGroupBy;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class AggregateRiskByCCPFn extends DoFn<KV<String, Iterable<Double>>, KV<String, Double>> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        KV<String, Iterable<Double>> element = c.element();

        Double sum = Double.valueOf(0.0);
        for (double value : element.getValue()) {
            sum += value;
        }
        c.output(KV.of(element.getKey(), sum));
    }

}
