package com.feiwanghub.subdataflownew.graphcomputation;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.values.KV;

/**
 * E = C * C + 2 * A * D
 */
public class ERecordCreateFn extends DoFn<KV<Integer, CoGbkResult>, KV<Integer, GraphRecord>> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        KV<Integer, CoGbkResult> acdRec = c.element();
        /**
         * NOTE: In this mode, the relationship is a one-to-one between the records, hence "getOnly"
         * If the relationship were one-to-many (as in tutorial 3) - it would be "getAll" which would return an Iterable
         */
        GraphRecord aRec = acdRec.getValue().getOnly(SimpleGraph.aRecTag);
        GraphRecord cRec = acdRec.getValue().getOnly(SimpleGraph.cRecTag);
        GraphRecord dRec = acdRec.getValue().getOnly(SimpleGraph.dRecTag);
        int eval = cRec.getRec() * cRec.getRec() + 2 * aRec.getRec() * dRec.getRec();
        c.output(KV.of(acdRec.getKey(), new GraphRecord(acdRec.getKey(), eval)));
    }

}
