package scratch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;


public class Scratch {
    public static void main(String[] args) {
        try {
            String endpoint = System.getenv("ALCHEMY_URL");
            if(endpoint == null || "".equals(endpoint)) {
                System.err.println("ALCHEMY_URL not specified");
                System.exit(1);
            }
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String payload = "{\"id\":1,\"jsonrpc\":\"2.0\",\"method\":\"alchemy_getTransactionReceipts\",\"params\":[{\"blockNumber\":\"0x9942AC\"}]}";

            System.out.println(payload);
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

            // Extract data from the JSON response
            JSONObject result = jsonResponse.getJSONObject("result");
            JSONArray receipts = result.getJSONArray("receipts");
            System.out.println("Receipts:");
            System.out.println(receipts.length());
            System.out.println(receipts.getJSONObject(0));
            //System.out.println(receipts.getJSONObject(0).get("from"));

            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

