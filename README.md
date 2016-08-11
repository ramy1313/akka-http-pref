# Test Akka-http performance 

run project using 

    sbt compile:run
    
run gatling using 

    sbt "project test" "gatling:test"
    
# Requests

    http://localhost:8080/backend

    http://localhost:8080/quacker?i=b9ogpt96K0NmkI

    http://localhost:8080/biteq?i=b9ogpt96K0NmkI

## All the results are from the second run 

### Tomcat7 on Core i5 machine
#### results from ab command

    ab -c 100 -n 300000 http://localhost:8080/backend
is 

    Server Software:        Apache-Coyote/1.1
    Server Hostname:        localhost
    Server Port:            8080
    
    Document Path:          /backend
    Document Length:        983 bytes
    
    Concurrency Level:      100
    Time taken for tests:   16.241 seconds
    Complete requests:      300000
    Failed requests:        0
    Non-2xx responses:      300000
    Total transferred:      352200000 bytes
    HTML transferred:       294900000 bytes
    Requests per second:    18471.84 [#/sec] (mean)
    Time per request:       5.414 [ms] (mean)
    Time per request:       0.054 [ms] (mean, across all concurrent requests)
    Transfer rate:          21177.68 [Kbytes/sec] received
Keep alive

    ab -k -c 100 -n 300000 http://localhost:8080/backend
is

    Server Software:        Apache-Coyote/1.1
    Server Hostname:        localhost
    Server Port:            8080
    
    Document Path:          /backend
    Document Length:        983 bytes
    
    Concurrency Level:      100
    Time taken for tests:   8.883 seconds
    Complete requests:      300000
    Failed requests:        0
    Non-2xx responses:      300000
    Keep-Alive requests:    297051
    Total transferred:      353685255 bytes
    HTML transferred:       294900000 bytes
    Requests per second:    33772.49 [#/sec] (mean)
    Time per request:       2.961 [ms] (mean)
    Time per request:       0.030 [ms] (mean, across all concurrent requests)
    Transfer rate:          38882.92 [Kbytes/sec] received
#### results from gatling 
ramp up

    rampUsers(280000) over (1 minute)
is completed in 60 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                     280000 (OK=280000 KO=0     )
    > min response time                                      0 (OK=0      KO=-     )
    > max response time                                  52004 (OK=52004  KO=-     )
    > mean response time                                  1048 (OK=1048   KO=-     )
    > std deviation                                       3362 (OK=3362   KO=-     )
    > response time 50th percentile                          6 (OK=6      KO=-     )
    > response time 75th percentile                        169 (OK=169    KO=-     )
    > response time 95th percentile                       7246 (OK=7247   KO=-     )
    > response time 99th percentile                      15478 (OK=15478  KO=-     )
    > mean requests/sec                                4590.164 (OK=4590.164 KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                        226096 ( 81%)
    > 800 ms < t < 1200 ms                               12097 (  4%)
    > t > 1200 ms                                        41807 ( 15%)
    > failed                                                 0 (  0%)
    ================================================================================
at once

    atOnceUsers(7000)
is completed in 4 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                       7000 (OK=7000   KO=0     )
    > min response time                                      0 (OK=0      KO=-     )
    > max response time                                   1021 (OK=1021   KO=-     )
    > mean response time                                    19 (OK=19     KO=-     )
    > std deviation                                         60 (OK=60     KO=-     )
    > response time 50th percentile                          2 (OK=2      KO=-     )
    > response time 75th percentile                         25 (OK=25     KO=-     )
    > response time 95th percentile                         70 (OK=70     KO=-     )
    > response time 99th percentile                        149 (OK=149    KO=-     )
    > mean requests/sec                                   1400 (OK=1400   KO=-     )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                          6980 (100%)
    > 800 ms < t < 1200 ms                                  20 (  0%)
    > t > 1200 ms                                            0 (  0%)
    > failed                                                 0 (  0%)
    ================================================================================
constant users per second

    constantUsersPerSec(5000) during(1 minute)
is completed in 69 seconds

    ================================================================================
    ---- Global Information --------------------------------------------------------
    > request count                                     300000 (OK=299959 KO=41    )
    > min response time                                      0 (OK=0      KO=60001 )
    > max response time                                  60011 (OK=52605  KO=60011 )
    > mean response time                                  1737 (OK=1729   KO=60005 )
    > std deviation                                       5180 (OK=5136   KO=3     )
    > response time 50th percentile                         20 (OK=20     KO=60006 )
    > response time 75th percentile                       1048 (OK=1048   KO=60009 )
    > response time 95th percentile                       8452 (OK=8414   KO=60011 )
    > response time 99th percentile                      31174 (OK=31172  KO=60011 )
    > mean requests/sec                                4285.714 (OK=4285.129 KO=0.586 )
    ---- Response Time Distribution ------------------------------------------------
    > t < 800 ms                                        220049 ( 73%)
    > 800 ms < t < 1200 ms                               17904 (  6%)
    > t > 1200 ms                                        62006 ( 21%)
    > failed                                                41 (  0%)
    ---- Errors --------------------------------------------------------------------
    > j.u.c.TimeoutException: Request timeout to not-connected after     40 (97.56%)
     60000ms
    > j.n.ConnectException: connection timed out: localhost/127.0.0.      1 ( 2.44%)
    1:8080
    ================================================================================


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

### Tomcat8 on Core i7 machine
#### results from wrk command

    wrk -t8 -c100 -d20s -R100000 http://127.0.0.1:8080/
is 

    Running 20s test @ http://127.0.0.1:8080/
      8 threads and 100 connections
      Thread calibration: mean lat.: 1794.994ms, rate sampling interval: 6279ms
      Thread calibration: mean lat.: 1917.311ms, rate sampling interval: 6762ms
      Thread calibration: mean lat.: 1951.991ms, rate sampling interval: 6922ms
      Thread calibration: mean lat.: 1850.369ms, rate sampling interval: 6533ms
      Thread calibration: mean lat.: 1959.935ms, rate sampling interval: 6733ms
      Thread calibration: mean lat.: 1893.944ms, rate sampling interval: 6680ms
      Thread calibration: mean lat.: 1900.646ms, rate sampling interval: 6606ms
      Thread calibration: mean lat.: 1911.499ms, rate sampling interval: 6692ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency     5.50s     1.05s    7.89s    59.01%
        Req/Sec     7.99k    55.01     8.08k    62.50%
      1275272 requests in 20.00s, 2.52GB read
    Requests/sec:  63768.29
    Transfer/sec:    129.18MB

#### results from ab command

    ab -c 100 -n 100000 http://127.0.0.1:8080/
is

    Concurrency Level:      100
    Time taken for tests:   3.326 seconds
    Complete requests:      100000
    Failed requests:        0
    Total transferred:      214300000 bytes
    HTML transferred:       189600000 bytes
    Requests per second:    30064.94 [#/sec] (mean)
    Time per request:       3.326 [ms] (mean)
    Time per request:       0.033 [ms] (mean, across all concurrent requests)
    Transfer rate:          62919.11 [Kbytes/sec] received

#### results from ab command (Keep-alive)

    ab -k -c 100 -n 100000 http://127.0.0.1:8080/    
is

    Concurrency Level:      100
    Time taken for tests:   1.626 seconds
    Complete requests:      100000
    Failed requests:        0
    Keep-Alive requests:    99048
    Total transferred:      214795240 bytes
    HTML transferred:       189600000 bytes
    Requests per second:    61485.60 [#/sec] (mean)
    Time per request:       1.626 [ms] (mean)
    Time per request:       0.016 [ms] (mean, across all concurrent requests)
    Transfer rate:          128972.80 [Kbytes/sec] received

### Akka 2.4.9-RC2 on Core i7 machine
#### results from ab command

    ab -c 100 -n 100000 http://127.0.0.1:3000/backend
is

    Concurrency Level:      100
    Time taken for tests:   31.265 seconds
    Complete requests:      100000
    Failed requests:        0
    Total transferred:      19500000 bytes
    HTML transferred:       3100000 bytes
    Requests per second:    3198.50 [#/sec] (mean)
    Time per request:       31.265 [ms] (mean)
    Time per request:       0.313 [ms] (mean, across all concurrent requests)
    Transfer rate:          609.09 [Kbytes/sec] received
And

    ab -c 100 -n 100000 http://127.0.0.1:3000/quacker?i=b9ogpt96K0NmkI
is

    Concurrency Level:      100
    Time taken for tests:   37.860 seconds
    Complete requests:      100000
    Failed requests:        0
    Total transferred:      39200000 bytes
    HTML transferred:       3000000 bytes
    Requests per second:    2641.32 [#/sec] (mean)
    Time per request:       37.860 [ms] (mean)
    Time per request:       0.379 [ms] (mean, across all concurrent requests)
    Transfer rate:          1011.13 [Kbytes/sec] received
And

    ab -c 100 -n 100000 http://127.0.0.1:3000/biteq?i=b9ogpt96K0NmkI
is

    Concurrency Level:      100
    Time taken for tests:   1345.012 seconds
    Complete requests:      100000
    Failed requests:        0
    Total transferred:      38900000 bytes
    HTML transferred:       2700000 bytes
    Requests per second:    74.35 [#/sec] (mean)
    Time per request:       1345.012 [ms] (mean)
    Time per request:       13.450 [ms] (mean, across all concurrent requests)
    Transfer rate:          28.24 [Kbytes/sec] received

#### results from ab command (Keep-alive)

    ab -k -c 100 -n 100000 http://127.0.0.1:3000/backend
is

    Concurrency Level:      100
    Time taken for tests:   1.202 seconds
    Complete requests:      100000
    Failed requests:        0
    Keep-Alive requests:    100000
    Total transferred:      20000000 bytes
    HTML transferred:       3100000 bytes
    Requests per second:    83224.31 [#/sec] (mean)
    Time per request:       1.202 [ms] (mean)
    Time per request:       0.012 [ms] (mean, across all concurrent requests)
    Transfer rate:          16254.75 [Kbytes/sec] received
And

    ab -k -c 100 -n 100000 http://127.0.0.1:3000/quacker?i=b9ogpt96K0NmkI
is

    Concurrency Level:      100
    Time taken for tests:   8.307 seconds
    Complete requests:      100000
    Failed requests:        0
    Keep-Alive requests:    100000
    Total transferred:      39700000 bytes
    HTML transferred:       3000000 bytes
    Requests per second:    12038.55 [#/sec] (mean)
    Time per request:       8.307 [ms] (mean)
    Time per request:       0.083 [ms] (mean, across all concurrent requests)
    Transfer rate:          4667.29 [Kbytes/sec] received
And

    ab -k -c 100 -n 100000 http://127.0.0.1:3000/biteq?i=b9ogpt96K0NmkI
is

    Concurrency Level:      100
    Time taken for tests:   1348.503 seconds
    Complete requests:      100000
    Failed requests:        0
    Keep-Alive requests:    100000
    Total transferred:      39400000 bytes
    HTML transferred:       2700000 bytes
    Requests per second:    74.16 [#/sec] (mean)
    Time per request:       1348.503 [ms] (mean)
    Time per request:       13.485 [ms] (mean, across all concurrent requests)
    Transfer rate:          28.53 [Kbytes/sec] received

#### results from wrk command

    wrk -t8 -c100 -d20s -R100000 http://127.0.0.1:3000/backend
is

    Running 20s test @ http://127.0.0.1:3000/backend
      8 threads and 100 connections
      Thread calibration: mean lat.: 333.716ms, rate sampling interval: 1451ms
      Thread calibration: mean lat.: 350.471ms, rate sampling interval: 1697ms
      Thread calibration: mean lat.: 331.322ms, rate sampling interval: 1723ms
      Thread calibration: mean lat.: 363.677ms, rate sampling interval: 1530ms
      Thread calibration: mean lat.: 404.263ms, rate sampling interval: 1744ms
      Thread calibration: mean lat.: 252.921ms, rate sampling interval: 1135ms
      Thread calibration: mean lat.: 340.583ms, rate sampling interval: 1531ms
      Thread calibration: mean lat.: 381.902ms, rate sampling interval: 1413ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency   780.02ms  593.14ms   2.42s    55.77%
        Req/Sec    12.01k   217.60    12.51k    66.67%
      1894518 requests in 20.00s, 317.99MB read
    Requests/sec:  94737.67
    Transfer/sec:     15.90MB
And

    wrk -t8 -c100 -d20s -R100000 http://127.0.0.1:3000/quacker?i=b9ogpt96K0NmkI
is

    Running 20s test @ http://127.0.0.1:3000/quacker?i=b9ogpt96K0NmkI
      8 threads and 100 connections
      Thread calibration: mean lat.: 4578.312ms, rate sampling interval: 16056ms
      Thread calibration: mean lat.: 4596.954ms, rate sampling interval: 16089ms
      Thread calibration: mean lat.: 4591.271ms, rate sampling interval: 16080ms
      Thread calibration: mean lat.: 4576.395ms, rate sampling interval: 16031ms
      Thread calibration: mean lat.: 4597.706ms, rate sampling interval: 16089ms
      Thread calibration: mean lat.: 4598.376ms, rate sampling interval: 16097ms
      Thread calibration: mean lat.: 4589.072ms, rate sampling interval: 16097ms
      Thread calibration: mean lat.: 4584.534ms, rate sampling interval: 16080ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency    13.32s     2.55s   17.78s    57.55%
        Req/Sec       -nan      -nan   0.00      0.00%
      227224 requests in 20.00s, 80.83MB read
    Requests/sec:  11360.83
    Transfer/sec:      4.04MB
And

    wrk -t8 -c100 -d20s -R100000 http://127.0.0.1:3000/biteq?i=b9ogpt96K0NmkI
is

    Running 20s test @ http://127.0.0.1:3000/biteq?i=b9ogpt96K0NmkI
      8 threads and 100 connections
      Thread calibration: mean lat.: 4914.247ms, rate sampling interval: 18235ms
      Thread calibration: mean lat.: 4941.177ms, rate sampling interval: 18268ms
      Thread calibration: mean lat.: 4846.192ms, rate sampling interval: 18038ms
      Thread calibration: mean lat.: 4914.518ms, rate sampling interval: 17842ms
      Thread calibration: mean lat.: 4922.981ms, rate sampling interval: 18169ms
      Thread calibration: mean lat.: 4870.149ms, rate sampling interval: 17989ms
      Thread calibration: mean lat.: 4855.966ms, rate sampling interval: 17694ms
      Thread calibration: mean lat.: 4917.327ms, rate sampling interval: 17858ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency    14.94s     2.88s   19.94s    56.89%
        Req/Sec       -nan      -nan   0.00      0.00%
      1464 requests in 20.07s, 528.98KB read
    Requests/sec:     72.95
    Transfer/sec:     26.36KB

### Akka 2.4.9-RC2 on Core i7 machine auto fusing off
#### results from ab command

    ab -c 100 -n 100000 http://127.0.0.1:3000/backend
is

    Concurrency Level:      100
    Time taken for tests:   14.685 seconds
    Complete requests:      100000
    Failed requests:        0
    Total transferred:      19500000 bytes
    HTML transferred:       3100000 bytes
    Requests per second:    6809.67 [#/sec] (mean)
    Time per request:       14.685 [ms] (mean)
    Time per request:       0.147 [ms] (mean, across all concurrent requests)
    Transfer rate:          1296.76 [Kbytes/sec] received
And

    ab -c 100 -n 100000 http://127.0.0.1:3000/quacker?i=b9ogpt96K0NmkI
is

    Concurrency Level:      100
    Time taken for tests:   22.388 seconds
    Complete requests:      100000
    Failed requests:        0
    Total transferred:      39200000 bytes
    HTML transferred:       3000000 bytes
    Requests per second:    4466.75 [#/sec] (mean)
    Time per request:       22.388 [ms] (mean)
    Time per request:       0.224 [ms] (mean, across all concurrent requests)
    Transfer rate:          1709.93 [Kbytes/sec] received
And

    ab -c 100 -n 100000 http://127.0.0.1:3000/biteq?i=b9ogpt96K0NmkI
is

    Concurrency Level:      100
    Time taken for tests:   25.985 seconds
    Complete requests:      100000
    Failed requests:        0
    Total transferred:      38900000 bytes
    HTML transferred:       2700000 bytes
    Requests per second:    3848.44 [#/sec] (mean)
    Time per request:       25.985 [ms] (mean)
    Time per request:       0.260 [ms] (mean, across all concurrent requests)
    Transfer rate:          1461.96 [Kbytes/sec] received

#### results from ab command (Keep-alive)

    ab -k -c 100 -n 100000 http://127.0.0.1:3000/backend
is

    Concurrency Level:      100
    Time taken for tests:   1.461 seconds
    Complete requests:      100000
    Failed requests:        0
    Keep-Alive requests:    100000
    Total transferred:      20000000 bytes
    HTML transferred:       3100000 bytes
    Requests per second:    68431.89 [#/sec] (mean)
    Time per request:       1.461 [ms] (mean)
    Time per request:       0.015 [ms] (mean, across all concurrent requests)
    Transfer rate:          13365.60 [Kbytes/sec] received
And

    ab -k -c 100 -n 100000 http://127.0.0.1:3000/quacker?i=b9ogpt96K0NmkI
is

    Concurrency Level:      100
    Time taken for tests:   7.211 seconds
    Complete requests:      100000
    Failed requests:        0
    Keep-Alive requests:    100000
    Total transferred:      39700000 bytes
    HTML transferred:       3000000 bytes
    Requests per second:    13868.39 [#/sec] (mean)
    Time per request:       7.211 [ms] (mean)
    Time per request:       0.072 [ms] (mean, across all concurrent requests)
    Transfer rate:          5376.71 [Kbytes/sec] received
And

    ab -k -c 100 -n 100000 http://127.0.0.1:3000/biteq?i=b9ogpt96K0NmkI
is

    Concurrency Level:      100
    Time taken for tests:   7.763 seconds
    Complete requests:      100000
    Failed requests:        0
    Keep-Alive requests:    100000
    Total transferred:      39400000 bytes
    HTML transferred:       2700000 bytes
    Requests per second:    12881.94 [#/sec] (mean)
    Time per request:       7.763 [ms] (mean)
    Time per request:       0.078 [ms] (mean, across all concurrent requests)
    Transfer rate:          4956.53 [Kbytes/sec] received

#### results from wrk command

    wrk -t8 -c100 -d20s -R100000 http://127.0.0.1:3000/backend
is

    Running 20s test @ http://127.0.0.1:3000/backend
      8 threads and 100 connections
      Thread calibration: mean lat.: 1371.258ms, rate sampling interval: 4771ms
      Thread calibration: mean lat.: 1367.483ms, rate sampling interval: 4751ms
      Thread calibration: mean lat.: 1431.113ms, rate sampling interval: 5033ms
      Thread calibration: mean lat.: 1404.824ms, rate sampling interval: 4939ms
      Thread calibration: mean lat.: 1361.832ms, rate sampling interval: 4759ms
      Thread calibration: mean lat.: 1395.961ms, rate sampling interval: 4874ms
      Thread calibration: mean lat.: 1373.594ms, rate sampling interval: 4788ms
      Thread calibration: mean lat.: 1388.590ms, rate sampling interval: 4911ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency     3.82s   783.47ms   5.87s    63.92%
        Req/Sec     9.56k    62.68     9.64k    66.67%
      1497683 requests in 20.00s, 251.38MB read
    Requests/sec:  74888.88
    Transfer/sec:     12.57MB
And

    wrk -t8 -c100 -d20s -R100000 http://127.0.0.1:3000/quacker?i=b9ogpt96K0NmkI
is

    Running 20s test @ http://127.0.0.1:3000/quacker?i=b9ogpt96K0NmkI
      8 threads and 100 connections
      Thread calibration: mean lat.: 4333.392ms, rate sampling interval: 15646ms
      Thread calibration: mean lat.: 4337.708ms, rate sampling interval: 15646ms
      Thread calibration: mean lat.: 4340.065ms, rate sampling interval: 15646ms
      Thread calibration: mean lat.: 4353.660ms, rate sampling interval: 15630ms
      Thread calibration: mean lat.: 4343.108ms, rate sampling interval: 15638ms
      Thread calibration: mean lat.: 4345.759ms, rate sampling interval: 15671ms
      Thread calibration: mean lat.: 4363.439ms, rate sampling interval: 15671ms
      Thread calibration: mean lat.: 4339.876ms, rate sampling interval: 15663ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency    12.94s     2.49s   17.27s    57.53%
        Req/Sec       -nan      -nan   0.00      0.00%
      275523 requests in 20.00s, 98.01MB read
    Requests/sec:  13776.83
    Transfer/sec:      4.90MB
And

    wrk -t8 -c100 -d20s -R100000 http://127.0.0.1:3000/biteq?i=b9ogpt96K0NmkI
is

    Running 20s test @ http://127.0.0.1:3000/biteq?i=b9ogpt96K0NmkI
      8 threads and 100 connections
      Thread calibration: mean lat.: 4409.314ms, rate sampling interval: 15826ms
      Thread calibration: mean lat.: 4409.014ms, rate sampling interval: 15826ms
      Thread calibration: mean lat.: 4395.976ms, rate sampling interval: 15818ms
      Thread calibration: mean lat.: 4406.151ms, rate sampling interval: 15826ms
      Thread calibration: mean lat.: 4400.117ms, rate sampling interval: 15818ms
      Thread calibration: mean lat.: 4419.768ms, rate sampling interval: 15859ms
      Thread calibration: mean lat.: 4396.140ms, rate sampling interval: 15810ms
      Thread calibration: mean lat.: 4396.089ms, rate sampling interval: 15818ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency    13.17s     2.49s   17.53s    57.60%
        Req/Sec       -nan      -nan   0.00      0.00%
      249563 requests in 20.00s, 88.06MB read
    Requests/sec:  12478.68
    Transfer/sec:      4.40MB
