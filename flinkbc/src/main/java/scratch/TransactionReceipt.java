package scratch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TransactionReceipt {
    public static void main(String[] args) {
        try {
            String endpoint = System.getenv("ETH_URL");
            if(endpoint == null || "".equals(endpoint)) {
                System.err.println("ALCHEMY_URL not specified");
                System.exit(1);
            }
            URL url = new URL(endpoint);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String payload = "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getTransactionByHash\",\"params\":[\"0xa9f5304d45c620517eb7b8f31e4bdc6e2c0db6c14c7f118947343dbca9a2dc90\"],\"id\":1}";

            OutputStream os = conn.getOutputStream();
            os.write(payload.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Response:");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

