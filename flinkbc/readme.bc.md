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


    Event to transform:

    {
  "blockHash":"0x527cee6b9740bae6f3aae0ab9dc39bf69547dd7acc012530559c04be7161da25",
  "logsBloom":"0x02000000000000000000000000000000000000000000000000000000000800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000008000000000000000000000000000000000000000000000000000000008000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000400000000000000000004000000000000000000001000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000000000000000000080000000000000000001000000000008000000000",
  "contractAddress":null,
  "transactionIndex":"0x0",
  "transactionHash":"0xfcb33fa7150aca2acc3471922a8b1378132d29905c7ba586827ace7f108022da",
  "gasUsed":"0x8686",
  "blockNumber":"0x6",
  "cumulativeGasUsed":"0x8686",
  "from":"0x892bb2e4f6b14a2b5b82ba8d33e5925d42d4431f",
  "to":"0xc0a4b9e04fb55b1b498c634faeeb7c8dd5895b53",
  "effectiveGasPrice":"0x5aeca5fe",
  "logs":[
    {
      "blockHash":"0x527cee6b9740bae6f3aae0ab9dc39bf69547dd7acc012530559c04be7161da25",
      "address":"0xc0a4b9e04fb55b1b498c634faeeb7c8dd5895b53",
      "logIndex":"0x0",
      "transactionLogIndex":"0x0",
      "data":"0x0000000000000000000000000000000000000000000000000000000000000001",
      "removed":false,
      "topics":[
        "0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef",
        "0x000000000000000000000000892bb2e4f6b14a2b5b82ba8d33e5925d42d4431f",
        "0x0000000000000000000000009949f7e672a568bb3ebeb777d5e8d1c1107e96e5"
      ],
      "blockNumber":"0x6",
      "transactionIndex":"0x0",
      "transactionHash":"0xfcb33fa7150aca2acc3471922a8b1378132d29905c7ba586827ace7f108022da"
    }
  ],
  "status":"0x1"
}