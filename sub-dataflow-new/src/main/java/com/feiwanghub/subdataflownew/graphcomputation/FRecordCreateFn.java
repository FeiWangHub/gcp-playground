package com.feiwanghub.subdataflownew.graphcomputation;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.values.KV;

/**
 * F = 5 * A + 2 * C + 10 * E
 */
public class FRecordCreateFn extends DoFn<KV<Integer, CoGbkResult>, KV<Integer, GraphRecord>> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        KV<Integer, CoGbkResult> aceRec = c.element();
        GraphRecord aRec = aceRec.getValue().getOnly(SimpleGraph.aRecTag);
        GraphRecord cRec = aceRec.getValue().getOnly(SimpleGraph.cRecTag);
        GraphRecord dRec = aceRec.getValue().getOnly(SimpleGraph.eRecTag);
        int fval = 5 * aRec.getRec() + 2 * cRec.getRec() + 10 * dRec.getRec();
        c.output(KV.of(aceRec.getKey(), new GraphRecord(aceRec.getKey(), fval)));
    }

}
