package com.feiwanghub.subdataflownew.GraphComputation;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

/**
 * D = a * 2
 */
public class DRecordCreateFn extends DoFn<KV<Integer, GraphRecord>, KV<Integer, GraphRecord>> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        int cval = c.element().getValue().getRec() * 2;
        int id = c.element().getKey();
        c.output(KV.of(id, new GraphRecord(id, cval)));
    }

}
