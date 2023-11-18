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

public class TransactionMapper implements FlatMapFunction<String, TransactionReceipt> {

    private static Logger LOG = LoggerFactory.getLogger(TransactionMapper.class);
    private URL ethUrl;

    public TransactionMapper() throws MalformedURLException {
        String endpoint = System.getenv("ETH_URL");
        if (endpoint == null || "".equals(endpoint)) {
            throw new RuntimeException("ETH_URL not present in environment");
        }
        ethUrl = new URL(endpoint);
    }

    private JSONObject getTransactionData(String txnHash) throws Exception {
        //TODO - think about connection management
        HttpURLConnection conn = (HttpURLConnection) ethUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String payload = "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getTransactionReceipt\",\"params\":[\"" +
                txnHash + "\"],\"id\":1}";

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
        JSONObject result = jsonResponse.getJSONObject("result");

        LOG.info(String.valueOf(result));

        conn.disconnect();

        return result;
    }

    private String extractVal(JSONObject json, String key) {
        return json.isNull(key) ? null : json.getString(key);
    }

    @Override
    public void flatMap(String s, Collector<TransactionReceipt> collector) throws Exception {
        LOG.info("flatMap" + s);
        try {
            JSONObject txn = getTransactionData(s);
            LOG.info(String.valueOf(txn));

            TransactionReceipt tr = new TransactionReceipt();
            tr.setBlockHash(txn.getString("blockHash"));
            tr.setTo(extractVal(txn, "to"));
            tr.setBlockNumber(txn.getString("blockNumber"));
            tr.setFrom(txn.getString("from"));
            tr.setStatus(txn.getString("status"));
            tr.setType(extractVal(txn, "type"));
            tr.setContractAddress(extractVal(txn, "contractAddress"));
            tr.setCumulativeGasUsed(txn.getString("cumulativeGasUsed"));
            tr.setEffectiveGasPrice(txn.getString("effectiveGasPrice"));
            tr.setTransactionHash(txn.getString("transactionHash"));
            tr.setTransactionIndex(txn.getString("transactionIndex"));
            tr.setGasUsed(txn.getString("gasUsed"));

            collector.collect(tr);


        } catch (Throwable t) {
            LOG.warn(String.valueOf(t));
        }
    }
}
