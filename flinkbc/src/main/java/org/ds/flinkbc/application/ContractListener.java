package org.ds.flinkbc.application;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.ds.flinkbc.connectors.NatsStreamSource;
import org.ds.flinkbc.functions.TransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ContractListener {
    private static Logger LOG = LoggerFactory.getLogger(ContractListener.class);
    private static final String NATS_URL = "nats://localhost:4222";

    private static final String CONTRACT_ADDR = "0xC0a4b9e04fB55B1b498c634FAEeb7C8dD5895b53";

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        NatsStreamSource nss = new NatsStreamSource(NATS_URL, "sc","transactions");

        Thread printingHook = new Thread(() -> nss.cancel());
        Runtime.getRuntime().addShutdownHook(printingHook);


        DataStream<String> txnStream = env.addSource(nss)
                .name("raw txn stream").uid("raw txn stream");

        //txnStream.flatMap(new TransactionMapper()).print();
        txnStream.flatMap(new TransactionMapper()).filter(tr -> {
            LOG.info(tr.getTo().toLowerCase());
            LOG.info(CONTRACT_ADDR.toLowerCase());
            return tr.getTo().toLowerCase().equals(CONTRACT_ADDR.toLowerCase());
        }).print();

        env.execute();
    }
}

