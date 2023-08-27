package com.feiwanghub.subdataflownew.GraphComputation;

import lombok.RequiredArgsConstructor;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollectionView;

import java.util.Map;

/**
 * C = (A + 1) * B
 */
@RequiredArgsConstructor
public class CRecordCreateFn extends DoFn<KV<Integer, GraphRecord>, KV<Integer, GraphRecord>> {

    final private PCollectionView<Map<Integer, GraphRecord>> bRecView;

    @ProcessElement
    public void processElement(ProcessContext c) {
        /**
         * get the bRecords from side input (this is broadcast join)
         */
        final Map<Integer, GraphRecord> bRecMap = c.sideInput(bRecView);

        KV<Integer, GraphRecord> aRecKV = c.element();
        /**
         * get id of a record
         */
        Integer id = aRecKV.getKey();
        GraphRecord aval = aRecKV.getValue();
        GraphRecord bval = bRecMap.get(id);
        int cval = (aval.getRec() + 1) * bval.getRec();
        c.output(KV.of(id, new GraphRecord(id, cval)));
    }

}
