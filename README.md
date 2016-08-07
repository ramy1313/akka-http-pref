# Test Akka-http performance 

## All the results are from the second run 

### Akka 2.4.8 on Core i5 machine
results from ab command 

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

