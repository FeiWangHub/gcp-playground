package com.feiwanghub.subdataflownew.wordcount;

import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.Flatten;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import java.util.List;

@Slf4j
public class WordCount {

    private void runPipeline(String[] args) {
        PipelineOptionsFactory.register(WordCountOptions.class);
        WordCountOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(WordCountOptions.class);

        Pipeline pipeline = Pipeline.create(options);

        /**
         * Read lines from input file
         * The TextIO.Read transform reads a PCollection containing text lines from one or more text files.
         * Each element in the input PCollection represents one line of a text file.
         */
        PCollection<String> lines = pipeline.apply(TextIO.read().from(options.getInputFile()));

        /**
         * Convert lines of text into list of individual words
         * the print ParDo prints out world list of each line?? TODO
         */
        PCollection<List<String>> wordLists = lines.apply(MapElements.via(new ExtractWordListFn()));
        wordLists.apply(ParDo.of(new PrintListDoFn()));

        /**
         * Count the number of times each word occurs
         */
        PCollection<KV<String, Long>> wordCountKV = wordLists.apply(Flatten.iterables()).apply(Count.perElement());
        PCollection<String> resultStrPool = wordCountKV.apply(ParDo.of(new FormatKVAsTextFn()));
//        resultStrPool.apply(ParDo.of(new PrintStringFn()));

        /**
         * Output to gcp bucket TODO
         */
        //resultStrPool.apply(TextIO.write().to(options.getOutputFile()));

        pipeline.run().waitUntilFinish();
    }

    public static void main(String[] args) {
        WordCount wordCount = new WordCount();
        wordCount.runPipeline(args);
    }

}
