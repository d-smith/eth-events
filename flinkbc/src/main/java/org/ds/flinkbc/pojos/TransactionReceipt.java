package org.ds.flinkbc.pojos;


import lombok.Data;

/*
POJO to hold transaction receipt data, e.g.

{
   "blockHash":"0xd5e64d2450add353b9627fa515175621d11fe99b2881174123ac8113efb51078",
   "logsBloom":"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000008000000000000000000000000000000000000000000000000000000000008000000000000000000000000000000000000000000000000020000000000000000000800000100000000000000000010000000000000000000000000000000000000000000000000000000000000000000000000080000000000001000000000000000000001000000000000000000000000000000000002000000000000000000000000000000000000000000000000000020000000100000000000000000000000000000000000000000000000000000000000",
   "contractAddress":null,
   "transactionIndex":"0x0",
   "type":"0x2",
   "transactionHash":"0x9aedc8338c965f1e09cbaf92c600cdd54c7a762e7660703affb5ba23de5ed18f",
   "gasUsed":"0x8438",
   "blockNumber":"0x9942ac",
   "cumulativeGasUsed":"0x8438",
   "from":"0xa9ebd6956773395f75072b909e8b874470f13ed4",
   "to":"0xa375a26dbb09f5c57fb54264f393ad6952d1d2de",
   "effectiveGasPrice":"0x165a0bc6a",
   "logs":[
      {
         "blockHash":"0xd5e64d2450add353b9627fa515175621d11fe99b2881174123ac8113efb51078",
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
      }
   ],
   "status":"0x1"
}
 */
@Data
public class TransactionReceipt {
    //TODO - allow log section to be embedded in the POJO
    public String blockHash;
    public String contractAddress;
    public String transactionIndex;
    public String type;
    public String transactionHash;
    public String gasUsed;
    public String blockNumber;
    public String cumulativeGasUsed;
    public String from;
    public String to;
    public String effectiveGasPrice;
    public String status;


}
