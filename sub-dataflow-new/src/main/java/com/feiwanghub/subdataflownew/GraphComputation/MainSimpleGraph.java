package com.feiwanghub.subdataflownew.GraphComputation;

import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.View;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.transforms.join.CoGroupByKey;
import org.apache.beam.sdk.transforms.join.KeyedPCollectionTuple;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;
import org.apache.beam.sdk.values.TupleTag;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class MainSimpleGraph {

    public static final TupleTag<GraphRecord> aRecTag = new TupleTag<>();
    public static final TupleTag<GraphRecord> cRecTag = new TupleTag<>();
    public static final TupleTag<GraphRecord> dRecTag = new TupleTag<>();
    public static final TupleTag<GraphRecord> eRecTag = new TupleTag<>();

    private void runPipeline(String[] args) {
        PipelineOptionsFactory.register(SimpleGraphOptions.class);
        SimpleGraphOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().create().as(SimpleGraphOptions.class);
        Pipeline pipeline = Pipeline.create(options);

        List<GraphRecord> inputA = Arrays.asList(
            new GraphRecord(1, 1),
            new GraphRecord(2, 2),
            new GraphRecord(3, 3),
            new GraphRecord(4, 4),
            new GraphRecord(5, 5)
        );

        List<GraphRecord> inputB = Arrays.asList(
            new GraphRecord(1, 1),
            new GraphRecord(2, 2),
            new GraphRecord(3, 3),
            new GraphRecord(4, 4),
            new GraphRecord(5, 5)
        );

        PCollection<KV<Integer, GraphRecord>> aRecCollections = pipeline
            .apply("read avro A", Create.of(inputA))
            .apply("avro A to A records", ParDo.of(new AvroToKVofGraphRecordFn()));

        PCollection<KV<Integer, GraphRecord>> bRecCollections = pipeline
            .apply("read avro B", Create.of(inputB))
            .apply("avro B to B records", ParDo.of(new AvroToKVofGraphRecordFn()));

        /**
         * Convert bRecord to a PcollectionView to send it in as a side input
         * PCollectionView: An immutable PCollection, can be used as a side input for ParDo
         * The type is as same as PCollection
         */
        PCollectionView<Map<Integer, GraphRecord>> bRecView = bRecCollections
            .apply("Covert bRec to view", View.asMap());

        /** C = (A+1) * B , A is sent via pipeline and B via sideinput */
        PCollection<KV<Integer, GraphRecord>> cRecCollection = aRecCollections
            .apply("create C records", ParDo.of(new CRecordCreateFn(bRecView))
                .withSideInputs(bRecView));

        /** D = A * 2 */
        PCollection<KV<Integer, GraphRecord>> dRecCollection = aRecCollections
            .apply("create D records", ParDo.of(new DRecordCreateFn()));

        /**
         * Join A, C, D, to create E
         * E = C * C + 2 * A * D
         * CoGbkResult: A collection of values for a given key from all inputs
         * CoGroupByKey: Groups results across several PCollections by key
         * KeyedPCollectionTuple: A tuple of keyed PCollections
         */
        PCollection<KV<Integer, CoGbkResult>> acdJoinResult = KeyedPCollectionTuple
            .of(aRecTag, aRecCollections)
            .and(cRecTag, cRecCollection)
            .and(dRecTag, dRecCollection)
            .apply("create acd groupby records", CoGroupByKey.create());
        PCollection<KV<Integer, GraphRecord>> eRecCollection = acdJoinResult
            .apply("create E records", ParDo.of(new ERecordCreateFn()));

        /**
         * Join A, C, E, to create F
         * F = 5 * A + 2 * C + 10 * E
         */
        PCollection<KV<Integer, CoGbkResult>> aceJoinResult = KeyedPCollectionTuple
            .of(aRecTag, aRecCollections)
            .and(cRecTag, cRecCollection)
            .and(eRecTag, eRecCollection)
            .apply("create ace groupby records", CoGroupByKey.create());
        PCollection<KV<Integer, GraphRecord>> fRecCollection = aceJoinResult
            .apply("create F records", ParDo.of(new FRecordCreateFn()));

        /**
         * Echo results
         */
        fRecCollection.apply("print result", ParDo.of(new EchoResultsFn()));

        pipeline.run().waitUntilFinish();
    }

    public static void main(String[] args) {
        MainSimpleGraph mainSimpleGraph = new MainSimpleGraph();
        mainSimpleGraph.runPipeline(args);
    }

    public interface SimpleGraphOptions extends PipelineOptions {

        @Description("AVRO file containing A records")
        @Default.String("gs://dataflow_workshop/arec.avro")
        String getARecordsFile();

        void setARecordsFile(String value);

        @Description("AVRO file containing B records")
        @Default.String("gs://dataflow_workshop/brec.avro")
        String getBRecordsFile();

        void setBRecordsFile(String value);
    }

}
