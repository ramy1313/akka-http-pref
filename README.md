# Test Akka-http performance 

run project using 

    sbt compile:run
    
run gatling using 

    sbt "project test" "gatling:test"

## All the results are from the second run 

### Akka 2.4.8 on Core i5 machine
#### results from ab command 

    ab -c 100 -n 300000 http://localhost:8080/backend
is 

    Concurrency Level:      100
    Time taken for tests:   246.885 seconds
    Complete requests:      300000
    Failed requests:        0
    Total transferred:      57300000 bytes
    HTML transferred:       9300000 bytes
    Requests per second:    1215.14 [#/sec] (mean)
    Time per request:       82.295 [ms] (mean)
    Time per request:       0.823 [ms] (mean, across all concurrent requests)
    Transfer rate:          226.65 [Kbytes/sec] received

#### results from gatling 
ramp up

    rampUsers(52000) over (1 minute)
is completed in 61 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                      52000 (OK=52000  KO=0     )
    > min response time                                      1 (OK=1      KO=-     )
    > max response time                                  52338 (OK=52338  KO=-     )
    > mean response time                                  1212 (OK=1212   KO=-     )
    > std deviation                                       3641 (OK=3641   KO=-     )
    > response time 50th percentile                         54 (OK=54     KO=-     )
    > response time 75th percentile                       1100 (OK=1100   KO=-     )
    > response time 95th percentile                       6904 (OK=6902   KO=-     )
    > response time 99th percentile                      20107 (OK=20113  KO=-     )
    > mean requests/sec                                 838.71 (OK=838.71 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                         35633 ( 69%)
    > 800 ms < t < 1200 ms                                5885 ( 11%)
    > t > 1200 ms                                        10482 ( 20%)
    > failed                                                 0 (  0%)
    ================================================================================
at once

    atOnceUsers(3000)
is completed in 6 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                       3000 (OK=3000   KO=0     )
    > min response time                                      3 (OK=3      KO=-     )
    > max response time                                   3254 (OK=3254   KO=-     )
    > mean response time                                  1426 (OK=1426   KO=-     )
    > std deviation                                       1147 (OK=1147   KO=-     )
    > response time 50th percentile                       1324 (OK=1324   KO=-     )
    > response time 75th percentile                       2625 (OK=2625   KO=-     )
    > response time 95th percentile                       3119 (OK=3119   KO=-     )
    > response time 99th percentile                       3147 (OK=3147   KO=-     )
    > mean requests/sec                                428.571 (OK=428.571 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                          1129 ( 38%)
    > 800 ms < t < 1200 ms                                 265 (  9%)
    > t > 1200 ms                                         1606 ( 54%)
    > failed                                                 0 (  0%)
    ================================================================================
constant users per second

    constantUsersPerSec(850) during(1 minute)
is completed in 61 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                      51000 (OK=51000  KO=0     )
    > min response time                                      1 (OK=1      KO=-     )
    > max response time                                  54420 (OK=54420  KO=-     )
    > mean response time                                  1651 (OK=1651   KO=-     )
    > std deviation                                       5058 (OK=5058   KO=-     )
    > response time 50th percentile                         65 (OK=66     KO=-     )
    > response time 75th percentile                       1102 (OK=1102   KO=-     )
    > response time 95th percentile                       8657 (OK=8655   KO=-     )
    > response time 99th percentile                      31074 (OK=31074  KO=-     )
    > mean requests/sec                                836.066 (OK=836.066 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                         35474 ( 70%)
    > 800 ms < t < 1200 ms                                4986 ( 10%)
    > t > 1200 ms                                        10540 ( 21%)
    > failed                                                 0 (  0%)
    ================================================================================
    
### Akka 2.4.8 on Core i5 machine with auto fusing off and pre-fusing
#### results from ab command

    ab -c 100 -n 300000 http://localhost:8080/backend
is

    Server Software:        akka-http/2.4.8
    Server Hostname:        localhost
    Server Port:            8080
    
    Document Path:          /backend
    Document Length:        31 bytes
    
    Concurrency Level:      100
    Time taken for tests:   131.508 seconds
    Complete requests:      300000
    Failed requests:        0
    Total transferred:      57300000 bytes
    HTML transferred:       9300000 bytes
    Requests per second:    2281.23 [#/sec] (mean)
    Time per request:       43.836 [ms] (mean)
    Time per request:       0.438 [ms] (mean, across all concurrent requests)
    Transfer rate:          425.50 [Kbytes/sec] received
Keep alive 

    ab -k -c 100 -n 300000 http://localhost:8080/backend
is 

    Server Software:        akka-http/2.4.8
    Server Hostname:        localhost
    Server Port:            8080
    
    Document Path:          /backend
    Document Length:        31 bytes
    
    Concurrency Level:      100
    Time taken for tests:   12.657 seconds
    Complete requests:      300000
    Failed requests:        0
    Keep-Alive requests:    300000
    Total transferred:      58800000 bytes
    HTML transferred:       9300000 bytes
    Requests per second:    23702.03 [#/sec] (mean)
    Time per request:       4.219 [ms] (mean)
    Time per request:       0.042 [ms] (mean, across all concurrent requests)
    Transfer rate:          4536.72 [Kbytes/sec] received
#### results from gatling 
ramp up

    rampUsers(77000) over (1 minute)
is completed in 60 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                      77000 (OK=77000  KO=0     )
    > min response time                                      1 (OK=1      KO=-     )
    > max response time                                  43043 (OK=43043  KO=-     )
    > mean response time                                  1554 (OK=1554   KO=-     )
    > std deviation                                       4721 (OK=4721   KO=-     )
    > response time 50th percentile                         20 (OK=20     KO=-     )
    > response time 75th percentile                       1067 (OK=1067   KO=-     )
    > response time 95th percentile                       7975 (OK=7995   KO=-     )
    > response time 99th percentile                      29261 (OK=29261  KO=-     )
    > mean requests/sec                                1262.295 (OK=1262.295 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                         54620 ( 71%)
    > 800 ms < t < 1200 ms                                8126 ( 11%)
    > t > 1200 ms                                        14254 ( 19%)
    > failed                                                 0 (  0%)
    ================================================================================
at once

    atOnceUsers(4000)
is completed in 7 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                       4000 (OK=4000   KO=0     )
    > min response time                                      2 (OK=2      KO=-     )
    > max response time                                   3123 (OK=3123   KO=-     )
    > mean response time                                   666 (OK=666    KO=-     )
    > std deviation                                        937 (OK=937    KO=-     )
    > response time 50th percentile                        138 (OK=138    KO=-     )
    > response time 75th percentile                       1085 (OK=1085   KO=-     )
    > response time 95th percentile                       3033 (OK=3033   KO=-     )
    > response time 99th percentile                       3088 (OK=3088   KO=-     )
    > mean requests/sec                                    500 (OK=500    KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                          2590 ( 65%)
    > 800 ms < t < 1200 ms                                 761 ( 19%)
    > t > 1200 ms                                          649 ( 16%)
    > failed                                                 0 (  0%)
    ================================================================================
constant user per second

    constantUsersPerSec(1350) during(1 minute)
is completed in 60 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                      81000 (OK=81000  KO=0     )
    > min response time                                      1 (OK=1      KO=-     )
    > max response time                                  51058 (OK=51058  KO=-     )
    > mean response time                                  1852 (OK=1852   KO=-     )
    > std deviation                                       5252 (OK=5252   KO=-     )
    > response time 50th percentile                         28 (OK=28     KO=-     )
    > response time 75th percentile                       1079 (OK=1079   KO=-     )
    > response time 95th percentile                      14913 (OK=14916  KO=-     )
    > response time 99th percentile                      31067 (OK=31067  KO=-     )
    > mean requests/sec                                1327.869 (OK=1327.869 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                         56490 ( 70%)
    > 800 ms < t < 1200 ms                                8417 ( 10%)
    > t > 1200 ms                                        16093 ( 20%)
    > failed                                                 0 (  0%)
    ================================================================================
### Akka 2.4.9-RC2 on Core i5 machine
#### results from ab command

    ab -c 100 -n 300000 http://localhost:8080/backend
is
    Server Software:        akka-http/2.4.9-RC2
    Server Hostname:        localhost
    Server Port:            8080
    
    Document Path:          /backend
    Document Length:        31 bytes
    
    Concurrency Level:      100
    Time taken for tests:   114.333 seconds
    Complete requests:      300000
    Failed requests:        0
    Total transferred:      58500000 bytes
    HTML transferred:       9300000 bytes
    Requests per second:    2623.92 [#/sec] (mean)
    Time per request:       38.111 [ms] (mean)
    Time per request:       0.381 [ms] (mean, across all concurrent requests)
    Transfer rate:          499.67 [Kbytes/sec] received
Keep alive

    ab -k -c 100 -n 700000 http://localhost:8080/backend
is

    Server Software:        akka-http/2.4.9-RC2
    Server Hostname:        localhost
    Server Port:            8080
    
    Document Path:          /backend
    Document Length:        31 bytes
    
    Concurrency Level:      100
    Time taken for tests:   13.991 seconds
    Complete requests:      700000
    Failed requests:        0
    Keep-Alive requests:    700000
    Total transferred:      140000000 bytes
    HTML transferred:       21700000 bytes
    Requests per second:    50033.47 [#/sec] (mean)
    Time per request:       1.999 [ms] (mean)
    Time per request:       0.020 [ms] (mean, across all concurrent requests)
    Transfer rate:          9772.16 [Kbytes/sec] received
#### results from gatling 
ramp up

    rampUsers(82000) over (1 minute)
is completed in 61 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                      82000 (OK=82000  KO=0     )
    > min response time                                      0 (OK=0      KO=-     )
    > max response time                                  49961 (OK=49961  KO=-     )
    > mean response time                                  1513 (OK=1513   KO=-     )
    > std deviation                                       4611 (OK=4611   KO=-     )
    > response time 50th percentile                         21 (OK=21     KO=-     )
    > response time 75th percentile                       1043 (OK=1043   KO=-     )
    > response time 95th percentile                       8580 (OK=8573   KO=-     )
    > response time 99th percentile                      27299 (OK=27299  KO=-     )
    > mean requests/sec                                1322.581 (OK=1322.581 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                         59683 ( 73%)
    > 800 ms < t < 1200 ms                                7258 (  9%)
    > t > 1200 ms                                        15059 ( 18%)
    > failed                                                 0 (  0%)
    ================================================================================
at once 

    atOnceUsers(7000)
is completed in 11 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                       7000 (OK=7000   KO=0     )
    > min response time                                      2 (OK=2      KO=-     )
    > max response time                                   8416 (OK=8416   KO=-     )
    > mean response time                                  2623 (OK=2623   KO=-     )
    > std deviation                                       2213 (OK=2213   KO=-     )
    > response time 50th percentile                       2686 (OK=2686   KO=-     )
    > response time 75th percentile                       3371 (OK=3371   KO=-     )
    > response time 95th percentile                       7041 (OK=7041   KO=-     )
    > response time 99th percentile                       7091 (OK=7091   KO=-     )
    > mean requests/sec                                583.333 (OK=583.333 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                          1761 ( 25%)
    > 800 ms < t < 1200 ms                                 572 (  8%)
    > t > 1200 ms                                         4667 ( 67%)
    > failed                                                 0 (  0%)
    ================================================================================
constant user per second

    constantUsersPerSec(1350) during(1 minute)
is completed in 60 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                      81000 (OK=81000  KO=0     )
    > min response time                                      0 (OK=0      KO=-     )
    > max response time                                  38895 (OK=38895  KO=-     )
    > mean response time                                  1206 (OK=1206   KO=-     )
    > std deviation                                       3751 (OK=3751   KO=-     )
    > response time 50th percentile                         13 (OK=13     KO=-     )
    > response time 75th percentile                        858 (OK=857    KO=-     )
    > response time 95th percentile                       7087 (OK=7086   KO=-     )
    > response time 99th percentile                      20624 (OK=20624  KO=-     )
    > mean requests/sec                                1327.869 (OK=1327.869 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                         60519 ( 75%)
    > 800 ms < t < 1200 ms                                6666 (  8%)
    > t > 1200 ms                                        13815 ( 17%)
    > failed                                                 0 (  0%)
    ================================================================================
### Akka 2.4.9-RC2 on Core i5 machine auto fusing off
#### results from ab command

    ab -c 100 -n 600000 http://localhost:8080/backend
is

    Server Software:        akka-http/2.4.9-RC2
    Server Hostname:        localhost
    Server Port:            8080
    
    Document Path:          /backend
    Document Length:        31 bytes
    
    Concurrency Level:      100
    Time taken for tests:   143.823 seconds
    Complete requests:      600000
    Failed requests:        0
    Total transferred:      117000000 bytes
    HTML transferred:       18600000 bytes
    Requests per second:    4171.79 [#/sec] (mean)
    Time per request:       23.971 [ms] (mean)
    Time per request:       0.240 [ms] (mean, across all concurrent requests)
    Transfer rate:          794.43 [Kbytes/sec] received
Keep alive 

    ab -k -c 100 -n 900000 http://localhost:8080/backend
is 

    Server Software:        akka-http/2.4.9-RC2
    Server Hostname:        localhost
    Server Port:            8080
    
    Document Path:          /backend
    Document Length:        31 bytes
    
    Concurrency Level:      100
    Time taken for tests:   19.566 seconds
    Complete requests:      900000
    Failed requests:        0
    Keep-Alive requests:    900000
    Total transferred:      180000000 bytes
    HTML transferred:       27900000 bytes
    Requests per second:    45998.29 [#/sec] (mean)
    Time per request:       2.174 [ms] (mean)
    Time per request:       0.022 [ms] (mean, across all concurrent requests)
    Transfer rate:          8984.04 [Kbytes/sec] received
#### results from gatling 
ramp up

    rampUsers(110000) over (1 minute)
is completed in 60 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                     110000 (OK=110000 KO=0     )
    > min response time                                      0 (OK=0      KO=-     )
    > max response time                                  48634 (OK=48634  KO=-     )
    > mean response time                                  2219 (OK=2219   KO=-     )
    > std deviation                                       5992 (OK=5992   KO=-     )
    > response time 50th percentile                         21 (OK=21     KO=-     )
    > response time 75th percentile                       1091 (OK=1091   KO=-     )
    > response time 95th percentile                      15084 (OK=15084  KO=-     )
    > response time 99th percentile                      31206 (OK=31206  KO=-     )
    > mean requests/sec                                1803.279 (OK=1803.279 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                         74000 ( 67%)
    > 800 ms < t < 1200 ms                               10343 (  9%)
    > t > 1200 ms                                        25657 ( 23%)
    > failed                                                 0 (  0%)
    ================================================================================
at once 

    atOnceUsers(7000)
is completed in 11 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                       7000 (OK=7000   KO=0     )
    > min response time                                      1 (OK=1      KO=-     )
    > max response time                                   7967 (OK=7967   KO=-     )
    > mean response time                                  2213 (OK=2213   KO=-     )
    > std deviation                                       2096 (OK=2096   KO=-     )
    > response time 50th percentile                       1465 (OK=1466   KO=-     )
    > response time 75th percentile                       3120 (OK=3121   KO=-     )
    > response time 95th percentile                       7043 (OK=7043   KO=-     )
    > response time 99th percentile                       7255 (OK=7255   KO=-     )
    > mean requests/sec                                583.333 (OK=583.333 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                          2026 ( 29%)
    > 800 ms < t < 1200 ms                                 916 ( 13%)
    > t > 1200 ms                                         4058 ( 58%)
    > failed                                                 0 (  0%)
    ================================================================================
constant user per second

    constantUsersPerSec(1850) during(1 minute)
is completed in 62 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                     111000 (OK=111000 KO=0     )
    > min response time                                      0 (OK=0      KO=-     )
    > max response time                                  54407 (OK=54407  KO=-     )
    > mean response time                                  1679 (OK=1679   KO=-     )
    > std deviation                                       4915 (OK=4915   KO=-     )
    > response time 50th percentile                         11 (OK=11     KO=-     )
    > response time 75th percentile                       1041 (OK=1041   KO=-     )
    > response time 95th percentile                      11294 (OK=11312  KO=-     )
    > response time 99th percentile                      28358 (OK=28358  KO=-     )
    > mean requests/sec                                1790.323 (OK=1790.323 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                         79051 ( 71%)
    > 800 ms < t < 1200 ms                                9674 (  9%)
    > t > 1200 ms                                        22275 ( 20%)
    > failed                                                 0 (  0%)
    ================================================================================
