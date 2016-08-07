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
    