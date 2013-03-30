# scale clojure web application

Test how Clojure web application scales concurrent HTTP connections with [http-kit](https://github.com/http-kit/http-kit)

600K concurrent HTTP connections with 3G heap.

More info: [600k concurrent HTTP connections on PC, with Clojure & http-kit](http://http-kit.org/600k-concurrent-connection-http-kit.html)

If a more powerful hardware is at hand and you want to get a larger number:

Tuning TCP/IP buffer sizes

```sh
# edit run_server. give it more RAM, http-kit will use ~2k  per connection
java -server -Xms3072m -Xmx3072m  \
    -cp `lein classpath` clojure.main \
    -m main $@
```

```java
// edit ConcurrencyBench.java.
final static int PER_IP = 20000;  // 63K is the upper bound
final static InetSocketAddress ADDRS[] = new InetSocketAddress[30];
// 600k concurrent connections
final static int CONCURENCY = PER_IP * ADDRS.length;
```
