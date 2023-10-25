package com.feiwanghub.subdataflownew.GraphComputation;

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
        GraphRecord aRec = aceRec.getValue().getOnly(MainSimpleGraph.aRecTag);
        GraphRecord cRec = aceRec.getValue().getOnly(MainSimpleGraph.cRecTag);
        GraphRecord dRec = aceRec.getValue().getOnly(MainSimpleGraph.eRecTag);
        int fval = 5 * aRec.getRec() + 2 * cRec.getRec() + 10 * dRec.getRec();
        c.output(KV.of(aceRec.getKey(), new GraphRecord(aceRec.getKey(), fval)));
    }

}
