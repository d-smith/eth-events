package org.ds.flinkbc.pojos;

import lombok.Data;

import java.util.List;

/*
blockHash":"0xd5e64d2450add353b9627fa515175621d11fe99b2881174123ac8113efb51078",
         "address":"0xa375a26dbb09f5c57fb54264f393ad6952d1d2de",
         "logIndex":"0x0",
         "data":"0x00000000000000000000000000000000000000000000000000000002540be400",
         "removed":false,
         "topics":[
            "0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef",
            "0x0000000000000000000000000000000000000000000000000000000000000000",
            "0x000000000000000000000000a9ebd6956773395f75072b909e8b874470f13ed4"
         ],
         "blockNumber":"0x9942ac",
         "transactionIndex":"0x0",
         "transactionHash":"0x9aedc8338c965f1e09cbaf92c600cdd54c7a762e7660703affb5ba23de5ed18f"
 */
@Data
public class LogEntry {
    private String blockHash;
    private String address;
    private String logIndex;
    private String data;
    private Boolean removed;
    private List<String> topics;
    private String blockNumber;
    private String transactionIndex;
    private String transactionHash;
}
