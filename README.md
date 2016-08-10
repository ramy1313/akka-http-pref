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

    wrk -t8 -c100 -d20s -R100000 http://localhost:3000/backend
is 

    Running 20s test @ http://localhost:3000/backend
      8 threads and 100 connections
      Thread calibration: mean lat.: 1130.037ms, rate sampling interval: 3899ms
      Thread calibration: mean lat.: 1080.816ms, rate sampling interval: 3753ms
      Thread calibration: mean lat.: 1130.852ms, rate sampling interval: 3817ms
      Thread calibration: mean lat.: 1081.531ms, rate sampling interval: 3721ms
      Thread calibration: mean lat.: 1050.116ms, rate sampling interval: 3620ms
      Thread calibration: mean lat.: 1105.129ms, rate sampling interval: 3950ms
      Thread calibration: mean lat.: 1097.380ms, rate sampling interval: 3751ms
      Thread calibration: mean lat.: 1103.611ms, rate sampling interval: 3827ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency     3.01s   615.59ms   4.56s    63.64%
        Req/Sec    10.14k    97.03    10.26k    50.00%
      1606419 requests in 20.00s, 269.63MB read
    Requests/sec:  80317.82
    Transfer/sec:     13.48MB

And

    wrk -t8 -c100 -d20s -R100000 http://localhost:3000/quacker?i=b9ogpt96K0NmkI
is 

    Running 20s test @ http://localhost:3000/quacker?i=b9ogpt96K0NmkI
      8 threads and 100 connections
      Thread calibration: mean lat.: 4487.087ms, rate sampling interval: 16048ms
      Thread calibration: mean lat.: 4480.704ms, rate sampling interval: 16023ms
      Thread calibration: mean lat.: 4492.586ms, rate sampling interval: 16048ms
      Thread calibration: mean lat.: 4477.791ms, rate sampling interval: 16039ms
      Thread calibration: mean lat.: 4486.473ms, rate sampling interval: 16015ms
      Thread calibration: mean lat.: 4492.172ms, rate sampling interval: 16048ms
      Thread calibration: mean lat.: 4479.053ms, rate sampling interval: 16015ms
      Thread calibration: mean lat.: 4487.284ms, rate sampling interval: 16031ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency    13.20s     2.51s   17.63s    57.71%
        Req/Sec       -nan      -nan   0.00      0.00%
      240525 requests in 20.00s, 85.56MB read
    Requests/sec:  12026.31
    Transfer/sec:      4.28MB

And

    wrk -t8 -c100 -d20s -R100000 http://localhost:3000/biteq?i=b9ogpt96K0NmkI

is

    Running 20s test @ http://localhost:3000/biteq?i=b9ogpt96K0NmkI
      8 threads and 100 connections
      Thread calibration: mean lat.: 4851.538ms, rate sampling interval: 17924ms
      Thread calibration: mean lat.: 4866.918ms, rate sampling interval: 17760ms
      Thread calibration: mean lat.: 4924.487ms, rate sampling interval: 18137ms
      Thread calibration: mean lat.: 4923.326ms, rate sampling interval: 17825ms
      Thread calibration: mean lat.: 5032.779ms, rate sampling interval: 18137ms
      Thread calibration: mean lat.: 5074.230ms, rate sampling interval: 18186ms
      Thread calibration: mean lat.: 5017.971ms, rate sampling interval: 18055ms
      Thread calibration: mean lat.: 4995.010ms, rate sampling interval: 17973ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency    15.04s     2.85s   19.92s    57.68%
        Req/Sec       -nan      -nan   0.00      0.00%
      1577 requests in 20.07s, 569.81KB read
    Requests/sec:     78.58
    Transfer/sec:     28.39KB

### Akka 2.4.9-RC2 on Core i7 machine
#### results from wrk command

    wrk -t8 -c100 -d20s -R100000 http://127.0.0.1:3000/backend
is

    Running 20s test @ http://localhost:3000/backend
      8 threads and 100 connections
      Thread calibration: mean lat.: 947.474ms, rate sampling interval: 3817ms
      Thread calibration: mean lat.: 917.376ms, rate sampling interval: 3725ms
      Thread calibration: mean lat.: 854.788ms, rate sampling interval: 3553ms
      Thread calibration: mean lat.: 913.139ms, rate sampling interval: 3768ms
      Thread calibration: mean lat.: 969.530ms, rate sampling interval: 3829ms
      Thread calibration: mean lat.: 930.114ms, rate sampling interval: 3727ms
      Thread calibration: mean lat.: 921.990ms, rate sampling interval: 3696ms
      Thread calibration: mean lat.: 904.085ms, rate sampling interval: 3583ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency     2.66s   725.80ms   4.53s    63.71%
        Req/Sec    10.59k   517.25    11.30k    62.50%
      1661684 requests in 20.00s, 278.91MB read
    Requests/sec:  83092.87
    Transfer/sec:     13.95MB

And

    wrk -t8 -c100 -d20s -R100000 http://127.0.0.1:3000/quacker?i=b9ogpt96K0NmkI
is

    Running 20s test @ http://127.0.0.1:3000/quacker?i=b9ogpt96K0NmkI
      8 threads and 100 connections
      Thread calibration: mean lat.: 4362.562ms, rate sampling interval: 15638ms
      Thread calibration: mean lat.: 4374.573ms, rate sampling interval: 15687ms
      Thread calibration: mean lat.: 4370.280ms, rate sampling interval: 15687ms
      Thread calibration: mean lat.: 4368.448ms, rate sampling interval: 15679ms
      Thread calibration: mean lat.: 4386.135ms, rate sampling interval: 15728ms
      Thread calibration: mean lat.: 4385.946ms, rate sampling interval: 15712ms
      Thread calibration: mean lat.: 4376.925ms, rate sampling interval: 15695ms
      Thread calibration: mean lat.: 4375.103ms, rate sampling interval: 15712ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency    13.07s     2.48s   17.45s    57.65%
        Req/Sec       -nan      -nan   0.00      0.00%
      261960 requests in 20.00s, 93.18MB read
    Requests/sec:  13097.65
    Transfer/sec:      4.66MB

And

    wrk -t8 -c100 -d20s -R100000 http://localhost:3000/biteq?i=b9ogpt96K0NmkI
is

    Running 20s test @ http://localhost:3000/biteq?i=b9ogpt96K0NmkI
      8 threads and 100 connections
      Thread calibration: mean lat.: 4834.694ms, rate sampling interval: 17940ms
      Thread calibration: mean lat.: 4977.547ms, rate sampling interval: 18087ms
      Thread calibration: mean lat.: 5047.363ms, rate sampling interval: 18006ms
      Thread calibration: mean lat.: 5093.558ms, rate sampling interval: 18153ms
      Thread calibration: mean lat.: 5054.244ms, rate sampling interval: 18071ms
      Thread calibration: mean lat.: 4981.165ms, rate sampling interval: 17989ms
      Thread calibration: mean lat.: 5027.600ms, rate sampling interval: 17956ms
      Thread calibration: mean lat.: 5086.062ms, rate sampling interval: 18202ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency    15.06s     2.87s   19.96s    57.57%
        Req/Sec       -nan      -nan   0.00      0.00%
      1577 requests in 20.07s, 569.81KB read
    Requests/sec:     78.57
    Transfer/sec:     28.39KB


### Akka 2.4.9-RC2 on Core i7 machine auto fusing off
#### results from wrk command

    wrk -t8 -c100 -d20s -R100000 http://127.0.0.1:3000/
is

    Running 20s test @ http://127.0.0.1:3000/
      8 threads and 100 connections
      Thread calibration: mean lat.: 448.144ms, rate sampling interval: 1679ms
      Thread calibration: mean lat.: 404.384ms, rate sampling interval: 1682ms
      Thread calibration: mean lat.: 374.180ms, rate sampling interval: 1490ms
      Thread calibration: mean lat.: 378.515ms, rate sampling interval: 1643ms
      Thread calibration: mean lat.: 465.665ms, rate sampling interval: 1795ms
      Thread calibration: mean lat.: 432.270ms, rate sampling interval: 1614ms
      Thread calibration: mean lat.: 362.621ms, rate sampling interval: 1735ms
      Thread calibration: mean lat.: 433.344ms, rate sampling interval: 1659ms
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency   638.59ms  555.83ms   2.39s    53.44%
        Req/Sec    12.18k   392.87    12.70k    69.77%
      1914356 requests in 20.00s, 356.01MB read
      Non-2xx or 3xx responses: 1914356
    Requests/sec:  95724.09
    Transfer/sec:     17.80MB
