package com.feiwanghub.subdataflownew.AggregationWithCoGroupBy;

import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

@Slf4j
public class EchoResultsFn extends DoFn<KV<String, Double>, Void> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        var result = c.element();
        log.info("id:" + result.getKey() + " | val:" + result.getValue());
    }

}
