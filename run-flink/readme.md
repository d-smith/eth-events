# Flink Setup

Download flink and untar it somewhere. Set FLINK_HOME variable, add it to your path, e.g.

```
export FLINK_HOME=$HOME/flink/flink-1.18.0
export PATH=$PATH:$FLINK_HOME/bin
```

Configure for more task slots

* edit $FLINK_HOME/conf/flink-conf.yaml
* set taskmanager.numberOfTaskSlots to 4
* set rest.bind-address: 0.0.0.0 - needed to enable console access from host when running WSL

Start the cluster

```
start-cluster.sh
```


Console at http://localhost:8081/#/overview