# Block Events

Some simple experiments working with Ethereum block events. This repo is structured as follows:

* flinkbc - Flink application that can process block and transaction events, read from nats.io
* nats-consumer - Simple consumer that reads block and transaction events from nats.io
* nats-setup - creates the nats.io stream and topics
* nats.io - contains a script to starts nats.io using Docker
* new-block-sub - contains a program that subscribes to Ethereum block events and writes blocks and transactions to the corresponding nats.io topics
* run-flink - script and context for running standalone flink
* txnutil - some golang samples for eth_ and alchemy_ RPCs