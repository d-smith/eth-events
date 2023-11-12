package main

import (
	"context"
	"fmt"
	"log"
	"os"

	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/ethclient"
)

func main() {
	wsEndPoint := os.Getenv("WS_END_POINT")
	if wsEndPoint == "" {
		log.Fatal("WS_END_POINT is not set")
	}

	client, err := ethclient.Dial(wsEndPoint)
	if err != nil {
		log.Fatal(err)
	}

	headers := make(chan *types.Header)
	sub, err := client.SubscribeNewHead(context.Background(), headers)
	if err != nil {
		log.Fatal(err)
	}

	for {
		select {
		case err := <-sub.Err():
			log.Fatal(err)
		case header := <-headers:

			block, err := client.BlockByHash(context.Background(), header.Hash())
			if err != nil {
				log.Fatal(err)
			}

			fmt.Println("Block:", block.Hash().Hex())             // 0xbc10defa8dda384c96a17640d84de5578804945d347072e091b4e5f390ddea7f
			fmt.Println("  Block no.:", block.Number().Uint64())  // 3477413
			fmt.Println("  Block time:", block.Time())            // 1529525947
			fmt.Println("  Nonce:", block.Nonce())                // 130524141876765836
			fmt.Println("  No. Txns:", len(block.Transactions())) // 7
			for _, tx := range block.Transactions() {
				fmt.Println("    Tx hash:", tx.Hash().Hex()) // 0xdec1d607c6c9d9d3e...
			}
		}
	}
}
