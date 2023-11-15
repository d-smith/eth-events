package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"os"
)

func main() {
	url := os.Getenv("ETH_NODE_URL")
	if url == "" {
		log.Fatal("ETH_NODE_URL is not set")
	}

	requestBody, err := json.Marshal(map[string]interface{}{
		"id":      1,
		"jsonrpc": "2.0",
		"method":  "alchemy_getTransactionReceipts",
		"params": map[string]interface{}{
			"blockNumber": "0x9942AC",
		},
	})
	if err != nil {
		log.Fatalf("Error creating request body: %v", err)
	} else {
		log.Printf("Request body: %v", string(requestBody))
	}

	req, err := http.NewRequest("POST", url, bytes.NewBuffer(requestBody))
	if err != nil {
		log.Fatalf("Error creating HTTP request: %v", err)
	}

	req.Header.Set("Content-Type", "application/json")

	client := &http.Client{}

	resp, err := client.Do(req)
	if err != nil {
		log.Fatalf("Error sending request: %v", err)
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		log.Fatalf("Received non-OK response: %v", resp.StatusCode)
	}

	responseBody, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		log.Fatalf("Error reading response body: %v", err)
	}

	var responseData map[string]interface{}
	err = json.Unmarshal(responseBody, &responseData)
	if err != nil {
		log.Fatalf("Error decoding response JSON: %v", err)
	}

	fmt.Println("Transaction Receipts:")
	fmt.Println(responseData)
}
