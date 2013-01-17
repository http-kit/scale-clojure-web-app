# scale clojure web application

Test how Clojure web application scales concurrent HTTP connections with [http-kit](https://github.com/shenfeng/http-kit)

400K concurrent connections with 3G heap, clients are keep-alive, send a request every 10s ~ 90s, server responses with 1k ~ 10k chars

Detail results in `results` folder

## Test Machine

**CPU**: Intel(R) Core(TM) i7-2600 CPU @ 3.40GHz, 4 core, 8 threads

**RAM**: 16G @ 1333MHZ

**OS** : Linux 3.2.0-2-amd64 #1 SMP Sun Apr 15 16:47:38 UTC 2012 x86_64 GNU/Linux

### Run it youself

```sh
./run_server                # starts server
./run_test                  # run benchmark code
```

#### Linux configs

``` sh

# set up virtual network interface,
# test client bind to these IP, then connect
for i in `seq 21 87`; do sudo ifconfig eth0:$i 192.168.1.$i up ; done

# more ports for testing
sudo sysctl -w net.ipv4.ip_local_port_range="1025 65535"
# tcp read buffer, min, default, maximum
sudo sysctl -w net.ipv4.tcp_rmem="4096 4096 16777216"
# tcp write buffer, min, default, maximum
sudo sysctl -w net.ipv4.tcp_wmem="4096 4096 16777216"
echo 9999999 | sudo tee /proc/sys/fs/nr_open
echo 9999999 | sudo tee /proc/sys/fs/file-max

# edit /etc/security/limits.conf, add line
# * - nofile 9999999

```

#### Command to inspect socket status

```sh
cat /proc/net/sockstat
```

#### Test output

[logfile](https://github.com/shenfeng/scale-clojure-web-app/blob/master/results/logfile)

```sh
time 0s, concurrency: 100, total requests: 0, thoughput: 0.00M/s, 0.00 requests/seconds
time 1s, concurrency: 3100, total requests: 3000, thoughput: 14.32M/s, 2964.43 requests/seconds
time 2s, concurrency: 6600, total requests: 6500, thoughput: 15.73M/s, 3198.82 requests/seconds
time 3s, concurrency: 10300, total requests: 10200, thoughput: 16.58M/s, 3346.46 requests/seconds
time 4s, concurrency: 14400, total requests: 14300, thoughput: 17.55M/s, 3527.38 requests/seconds
time 5s, concurrency: 18800, total requests: 18700, thoughput: 18.34M/s, 3676.76 requests/seconds
...
time 23s, concurrency: 95300, total requests: 99193, thoughput: 21.40M/s, 4256.11 requests/seconds
time 24s, concurrency: 99253, total requests: 103795, thoughput: 21.45M/s, 4267.54 requests/seconds
time 25s, concurrency: 103399, total requests: 108628, thoughput: 21.56M/s, 4289.02 requests/seconds
time 26s, concurrency: 107552, total requests: 113527, thoughput: 21.67M/s, 4308.92 requests/seconds
time 27s, concurrency: 111800, total requests: 118503, thoughput: 21.77M/s, 4332.83 requests/seconds
time 28s, concurrency: 115853, total requests: 123452, thoughput: 21.88M/s, 4353.49 requests/seconds
...
time 56s, concurrency: 230700, total requests: 285423, thoughput: 25.37M/s, 5034.89 requests/seconds
time 57s, concurrency: 234674, total requests: 291952, thoughput: 25.49M/s, 5059.04 requests/seconds
time 58s, concurrency: 238800, total requests: 298723, thoughput: 25.62M/s, 5086.12 requests/seconds
time 59s, concurrency: 242853, total requests: 305490, thoughput: 25.76M/s, 5112.29 requests/seconds
time 60s, concurrency: 246900, total requests: 312392, thoughput: 25.90M/s, 5139.63 requests/seconds
...
time 63s, concurrency: 258998, total requests: 333171, thoughput: 26.30M/s, 5221.95 requests/seconds
time 65s, concurrency: 261900, total requests: 338326, thoughput: 26.04M/s, 5169.78 requests/seconds
time 66s, concurrency: 265644, total requests: 348010, thoughput: 26.38M/s, 5236.54 requests/seconds
time 67s, concurrency: 269700, total requests: 355253, thoughput: 26.52M/s, 5265.50 requests/seconds
time 68s, concurrency: 273800, total requests: 362640, thoughput: 26.67M/s, 5295.33 requests/seconds
time 69s, concurrency: 277800, total requests: 369993, thoughput: 26.82M/s, 5324.33 requests/seconds
time 70s, concurrency: 281700, total requests: 377308, thoughput: 26.95M/s, 5350.67 requests/seconds
...
time 82s, concurrency: 328500, total requests: 471910, thoughput: 28.75M/s, 5705.87 requests/seconds
time 83s, concurrency: 332453, total requests: 480212, thoughput: 28.90M/s, 5736.55 requests/seconds
time 84s, concurrency: 336400, total requests: 488725, thoughput: 29.06M/s, 5768.57 requests/seconds
time 85s, concurrency: 340053, total requests: 497111, thoughput: 29.21M/s, 5798.02 requests/seconds
time 86s, concurrency: 344053, total requests: 505918, thoughput: 29.38M/s, 5831.71 requests/seconds
time 87s, concurrency: 347700, total requests: 514348, thoughput: 29.53M/s, 5861.25 requests/seconds
time 88s, concurrency: 351553, total requests: 523122, thoughput: 29.68M/s, 5892.54 requests/seconds
...
time 96s, concurrency: 381400, total requests: 596005, thoughput: 30.98M/s, 6152.18 requests/seconds
time 97s, concurrency: 385153, total requests: 605549, thoughput: 31.16M/s, 6186.52 requests/seconds
time 98s, concurrency: 388800, total requests: 615085, thoughput: 31.32M/s, 6219.32 requests/seconds
time 99s, concurrency: 392553, total requests: 624937, thoughput: 31.50M/s, 6255.56 requests/seconds
time 100s, concurrency: 395969, total requests: 634565, thoughput: 31.65M/s, 6288.05 requests/seconds
time 101s, concurrency: 399846, total requests: 644542, thoughput: 31.84M/s, 6323.32 requests/seconds
time 104s, concurrency: 400000, total requests: 646431, thoughput: 31.26M/s, 6205.60 requests/seconds
time 424s, concurrency: 400000, total requests: 2923525, thoughput: 34.77M/s, 6894.76 requests/seconds
...
time 1488s, concurrency: 400000, total requests: 10453649, thoughput: 35.44M/s, 7022.62 requests/seconds
time 1490s, concurrency: 400000, total requests: 10461841, thoughput: 35.39M/s, 7018.59 requests/seconds

```

### CPU Usage

![cpu usage](https://raw.github.com/shenfeng/scale-clojure-web-app/master/results/cpu.png)

### Memory Usage

![heap memory usage](https://raw.github.com/shenfeng/scale-clojure-web-app/master/results/heap.png)
