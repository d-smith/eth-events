package main

import (
	"fmt"
	"log"

	nats "github.com/nats-io/nats.go"
)

func main() {
	// Connect to NATS
	nc, err := nats.Connect(nats.DefaultURL)
	if err != nil {
		log.Fatal(err)
	}

	defer nc.Close()

	js, err := nc.JetStream()
	if err != nil {
		log.Fatal(err)
	}

	// Create a stream
	si, err := js.AddStream(&nats.StreamConfig{
		Name:              "Ethereum",
		Subjects:          []string{"blocks", "transactions"},
		Discard:           nats.DiscardOld,
		MaxMsgsPerSubject: 1000000,
		Retention:         nats.LimitsPolicy,
		Storage:           nats.FileStorage,
	})

	if err != nil {
		log.Fatal(err)
	}

	fmt.Printf("Stream info: %+v\n", si)
}
