package main

import (
	"context"
	"fmt"
	"log"
	"os"

	"github.com/ethereum/go-ethereum/rpc"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Please provide the transaction hash as an argument")
		return
	}

	transactionHash := os.Args[1]

	ethNodeUrl := os.Getenv("ETH_NODE_URL")
	if ethNodeUrl == "" {
		log.Fatal("ETH_NODE_URL is not set")
	}
	

	client, err := rpc.Dial(ethNodeUrl)
	if err != nil {
		log.Fatalf("Failed to connect to the Ethereum client: %v", err)
	}

	defer client.Close()

	ctx := context.Background()

	var txDetails map[string]interface{}
	err = client.CallContext(ctx, &txDetails, "eth_getTransactionByHash", transactionHash)
	if err != nil {
		log.Fatalf("Failed to get transaction details: %v", err)
	}

	if txDetails == nil || len(txDetails) == 0 {
		fmt.Println("Transaction not found")
		return
	}

	fmt.Println("Transaction Details:")
	fmt.Printf("Hash: %v\n", txDetails["hash"])
	fmt.Printf("Block Hash: %v\n", txDetails["blockHash"])
	fmt.Printf("Block Number: %v\n", txDetails["blockNumber"])
	fmt.Printf("From: %v\n", txDetails["from"])
	fmt.Printf("To: %v\n", txDetails["to"])
	fmt.Printf("Value: %v\n", txDetails["value"])
	fmt.Printf("Gas Price: %v\n", txDetails["gasPrice"])
	fmt.Printf("Gas Used: %v\n", txDetails["gasUsed"])
	fmt.Printf("Nonce: %v\n", txDetails["nonce"])
	fmt.Printf("Input Data: %v\n", txDetails["input"])

	// Additional details can be retrieved from 'txDetails'

	receipt, err := getTransactionReceipt(client, ctx, transactionHash)
	if err != nil {
		log.Fatalf("Failed to get transaction receipt: %v", err)
	}

	fmt.Println("\nTransaction Receipt:")
	fmt.Printf("Status: %v\n", receipt["status"])
	fmt.Printf("Gas Used: %v\n", receipt["gasUsed"])
	fmt.Printf("Cumulative Gas Used: %v\n", receipt["cumulativeGasUsed"])
	fmt.Printf("Logs: %v\n", receipt["logs"])
}

func getTransactionReceipt(client *rpc.Client, ctx context.Context, txHash string) (map[string]interface{}, error) {
	var receipt map[string]interface{}
	err := client.CallContext(ctx, &receipt, "eth_getTransactionReceipt", txHash)
	if err != nil {
		return nil, err
	}
	return receipt, nil
}
