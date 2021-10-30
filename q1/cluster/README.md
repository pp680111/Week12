配置了一个5个节点的集群，由6379、6380、6381、6382、6383五个Redis Server作为集群中的master节点，然后又配置了两个36379、36380节点作为6379和6380的slave节点

搭建过程中几个重要的操作记录如下

```
# 省略几个节点启动的操作
# 创建cluster集群
redis-cli --cluster create 127.0.0.1:6379 127.0.0.1:6380 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383

# 查询6379节点和6380节点在集群中的id
redis-cli -c -p 6379 cluster myid
redis-cli -c -p 6380 cluster myid

# 添加36379和36380作为3679和3680的slave节点，
redis-cli --cluster add-node 127.0.0.1:36379 127.0.0.1:6379 --cluster-slave --cluster-master-id cd563c226147a90dd7af81771030edccafa8966b
redis-cli --cluster add-node 127.0.0.1:36380 127.0.0.1:6379 --cluster-slave --cluster-master-id 656622e8d14654833e425320ca9c7f1dfec72baa
```

