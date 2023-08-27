package com.feiwanghub.subdataflownew.GraphComputation;

import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

@Slf4j
public class EchoResultsFn extends DoFn<KV<Integer, GraphRecord>, Void> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        log.info("id:%s | val:%s".formatted(c.element().getKey(), c.element().getValue().getRec()));
    }

}
