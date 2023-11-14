package org.ds.flinkbc.counters;

public class ConsumerCounter extends Counter {
    public ConsumerCounter() {
       super("{} consumed in {} ms - {} per second");
    }
}
