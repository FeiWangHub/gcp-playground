package com.feiwanghub.subdataflownew.wordcount;

import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;

import java.util.Arrays;
import java.util.List;

import static com.feiwanghub.subdataflownew.wordcount.ExtractWordListFn.TOKENIZER_PATTERN;

/**
 * Official Word Count Code
 * <a href="https://github.com/apache/beam/blob/master/examples/java/src/main/java/org/apache/beam/examples/WordCount.java">Official Word Count Code</a>
 */
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
         * the print ParDo prints out world list of each line
         */
        PCollection<List<String>> wordLists = lines
                .apply(MapElements.via(new ExtractWordListFn()))
                .apply(Filter.by((List<String> wordList) -> wordList != null && !wordList.isEmpty()));
        //wordLists.apply(ParDo.of(new PrintListDoFn()));

        // words extract option 2
        //PCollection<String> words = lines.apply(FlatMapElements.into(TypeDescriptors.strings())
        //        .via((String line) -> Arrays.asList(line.split(TOKENIZER_PATTERN))));
        //words.apply(ParDo.of(new PrintStringFn()));

        /**
         * Count the number of times each word occurs
         */
        PCollection<KV<String, Long>> wordCountKV = wordLists
                .apply(Flatten.iterables())
                .apply(Count.perElement());
        PCollection<String> resultPool = wordCountKV.apply(ParDo.of(new FormatKVAsTextFn()));
        resultPool.apply(ParDo.of(new PrintStringFn()));

        /**
         * Output to gcp bucket TODO
         */
        //resultPool.apply(TextIO.write().to(options.getOutputFile()));
        //resultPool.apply(TextIO.write().to("wordcout.txt"));

        pipeline.run().waitUntilFinish();
    }

    public static void main(String[] args) {
        WordCount wordCount = new WordCount();
        wordCount.runPipeline(args);
    }

}
