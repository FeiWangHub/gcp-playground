package com.feiwanghub.subdataflownew.wordcount;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class FormatKVAsTextFn extends DoFn<KV<String, Long>, String> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        c.output("%s:%s".formatted(c.element().getKey(), c.element().getValue()));
    }

}
