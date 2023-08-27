package com.feiwanghub.subdataflownew.GraphComputation;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class AvroToKVofGraphRecordFn extends DoFn<GraphRecord, KV<Integer, GraphRecord>> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        GraphRecord graphRecord = c.element();
        c.output(KV.of(graphRecord.getId(), graphRecord));
    }

}
