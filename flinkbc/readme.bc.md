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


    Demo Sequence - contract transaction events in block:

    1. Start anvil
    2. Deploy contracts, get deployment address
    3. Start nats
    4. Configure stream and topics
    5. Start new-block-sub listener
    6. Start flink noting correct contract address
    7. Run cctpcli to generate contract events