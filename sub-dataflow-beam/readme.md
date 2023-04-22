## Download Original Project
```
mvn archetype:generate \
    -DarchetypeGroupId=org.apache.beam \
    -DarchetypeArtifactId=beam-sdks-java-maven-archetypes-examples \
    -DarchetypeVersion=2.37.0 \
    -DgroupId=org.example \
    -DartifactId=word-count-beam \
    -Dversion="0.1" \
    -Dpackage=org.apache.beam.examples \
    -DinteractiveMode=false
```

## Prepare Infra
* Enable Dataflow API
* Create Storage Bucket gcp-playground-bucket
* Make sure service account has permission of Dataflow, Storage, Dataflow
* Move to dir of word-count-beam

## Start Dataflow - WordCount
```
mvn -Pdataflow-runner compile \
    exec:java \
    -Dexec.mainClass=org.apache.beam.examples.WordCount \
    -Dexec.args="--project=genuine-cirrus-371119 \
    --gcpTempLocation=gs://gcp-playground-bucket/temp/ \
    --output=gs://gcp-playground-bucket/results/output \
    --runner=DataflowRunner \
    --region=us-central1" \
    -Pdataflow-runner
```