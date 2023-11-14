package main

import (
	"context"
	"fmt"
	"log"

	"github.com/nats-io/nats.go"
	"github.com/nats-io/nats.go/jetstream"
)

func main() {
	// connect to nats server
	nc, err := nats.Connect(nats.DefaultURL)
	if err != nil {
		log.Fatal(err)
	}

	// create jetstream context from nats connection
	js, err := jetstream.New(nc)
	if err != nil {
		log.Fatal(err)
	}

	//ctx, cancel := context.WithTimeout(context.Background(), 30*time.Second)
	// cancel()

	cons, err := js.CreateOrUpdateConsumer(context.Background(), "Ethereum", jetstream.ConsumerConfig{
		AckPolicy:     jetstream.AckExplicitPolicy,
		FilterSubject: "blocks",
	})

	if err != nil {
		log.Fatal(err)
	}

	iter, _ := cons.Messages()
	for {
		msg, err := iter.Next()
		// Next can return error, e.g. when iterator is closed or no heartbeats were received
		if err != nil {
			//handle error
		}
		fmt.Printf("Received a JetStream message: %s\n", string(msg.Data()))
		msg.Ack()
	}

}
