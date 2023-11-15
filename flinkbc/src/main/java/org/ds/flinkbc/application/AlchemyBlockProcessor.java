package org.ds.flinkbc.application;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.ds.flinkbc.connectors.NatsStreamSource;
import org.ds.flinkbc.functions.AlchemyBlockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlchemyBlockProcessor {
    private static Logger LOG = LoggerFactory.getLogger(AlchemyBlockProcessor.class);
    private static final String NATS_URL = "nats://localhost:4222";

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        NatsStreamSource nss = new NatsStreamSource(NATS_URL, "sc","blocks");
        DataStream<String> txnStream = env.addSource(nss)
                .name("raw txn stream").uid("raw txn stream");

        txnStream.flatMap(new AlchemyBlockMapper()).print();

        env.execute();
    }
}
