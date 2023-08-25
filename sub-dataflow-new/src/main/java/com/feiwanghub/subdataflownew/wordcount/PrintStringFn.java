package com.feiwanghub.subdataflownew.wordcount;

import org.apache.beam.sdk.transforms.DoFn;

public class PrintStringFn extends DoFn<String, Void> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        System.out.println(c.element());
    }

}
