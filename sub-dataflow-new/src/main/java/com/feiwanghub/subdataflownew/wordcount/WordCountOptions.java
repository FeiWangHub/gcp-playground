package com.feiwanghub.subdataflownew.wordcount;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface WordCountOptions extends PipelineOptions {

    @Description("Path of the file to read from")
    @Default.String("gs://apache-beam-samples/shakespeare/kinglear.txt")
    String getInputFile();

    void setInputFile(String value);

    /**
     * GCP bucket for results
     */
    @Description("Path of the file to write to")
    @Default.String("gs://gcp-playground-dataflow/kinglear.txt")
    String getOutputFile();

    void setOutputFile(String value);

}
