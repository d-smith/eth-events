# flink-app

Flink stream processing app for blockchain events


## Misc

mvn archetype:generate \
    -DarchetypeGroupId=org.apache.flink \
    -DarchetypeArtifactId=flink-walkthrough-datastream-java \
    -DarchetypeVersion=1.18.0 \
    -DgroupId=org.ds \
    -DartifactId=flinkbc \
    -Dversion=0.1 \
    -Dpackage=flinkbc \
    -DinteractiveMode=false