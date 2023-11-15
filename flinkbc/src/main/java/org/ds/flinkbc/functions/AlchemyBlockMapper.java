package org.ds.flinkbc.functions;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.ds.flinkbc.pojos.TransactionReceipt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AlchemyBlockMapper implements FlatMapFunction<String, TransactionReceipt> {

    private static Logger LOG = LoggerFactory.getLogger(AlchemyBlockMapper.class);
    private URL alchemyUrl;
    public AlchemyBlockMapper() throws MalformedURLException {
        String endpoint = System.getenv("ALCHEMY_URL");
        if(endpoint == null || "".equals(endpoint)) {
            throw new RuntimeException("ALCHEMY_URL not present in environment");
        }
        alchemyUrl = new URL(endpoint);
    }

    private JSONObject getBlockData(String blockNo) throws Exception {
        //TODO - think about connection management
        HttpURLConnection conn = (HttpURLConnection) alchemyUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String payload = "{\"id\":1,\"jsonrpc\":\"2.0\",\"method\":\"alchemy_getTransactionReceipts\",\"params\":[{\"blockNumber\":\"0x" +
                blockNo + "\"}]}";

        LOG.info(payload);
        OutputStream os = conn.getOutputStream();
        os.write(payload.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder response = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            response.append(output);
        }

        JSONObject jsonResponse = new JSONObject(response.toString());

        //LOG.info(String.valueOf(jsonResponse));


        conn.disconnect();

        return jsonResponse;
    }

    private String extractVal(JSONObject json, String key) {
        return json.isNull(key) ? null : json.getString(key);
    }

    @Override
    public void flatMap(String s, Collector<TransactionReceipt> collector) throws Exception {
        try {
            JSONObject blockData = getBlockData(s);
            JSONObject result = blockData.getJSONObject("result");
            JSONArray receipts = result.getJSONArray("receipts");
            int noTxns = receipts.length();
            for (int i = 0; i < noTxns; i++) {
                JSONObject txn = receipts.getJSONObject(i);
                LOG.info(String.valueOf(txn));
                TransactionReceipt tr = new TransactionReceipt();
                tr.setBlockHash(txn.getString("blockHash"));
                tr.setTo(extractVal(txn,"to"));
                tr.setBlockNumber(txn.getString("blockNumber"));
                tr.setFrom(txn.getString("from"));
                tr.setStatus(txn.getString("status"));
                tr.setType(txn.getString("type"));
                tr.setContractAddress(extractVal(txn,"contractAddress"));
                tr.setCumulativeGasUsed(txn.getString("cumulativeGasUsed"));
                tr.setEffectiveGasPrice(txn.getString("effectiveGasPrice"));
                tr.setTransactionHash(txn.getString("transactionHash"));
                tr.setTransactionIndex(txn.getString("transactionIndex"));
                tr.setGasUsed(txn.getString("gasUsed"));

                collector.collect(tr);
            }
        } catch(JSONException e) {
            LOG.info(e.getMessage());
        }
    }
}
