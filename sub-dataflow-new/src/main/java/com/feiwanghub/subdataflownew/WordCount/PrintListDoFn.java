package com.feiwanghub.subdataflownew.WordCount;

import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Slf4j
public class PrintListDoFn extends DoFn<List<String>, Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintListDoFn.class);

    @ProcessElement
    public void processElement(ProcessContext c) {
        //System.out.println(c.element());
        LOGGER.info("words: {}", c.element());
    }

}
