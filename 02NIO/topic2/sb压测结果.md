java -Xmx1g -Xms1g -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar gateway-server-0.0.1-SNAPSHOT.jar

C:\Users\User>sb -u 'http://127.0.0.1:8088/' -c 10 -N 40
Starting at 2021/7/4 13:06:49
[Press C to stop the test]
290873  (RPS: 4889.9)
---------------Finished!----------------
Finished at 2021/7/4 13:07:49 (took 00:00:59.8066529)
Status 303:    290880

RPS: 7049.2 (requests/second)
Max: 939ms
Min: 0ms
Avg: 0ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 0ms
  98%   below 0ms
  99%   below 0ms
99.9%   below 0ms

java -Xmx2g -Xms2g -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar gateway-server-0.0.1-SNAPSHOT.jar

C:\Users\User>sb -u 'http://127.0.0.1:8088/' -c 10 -N 40
Starting at 2021/7/4 13:11:01
[Press C to stop the test]
314888  (RPS: 6119.9)
---------------Finished!----------------
Finished at 2021/7/4 13:11:53 (took 00:00:51.6726358)
Status 303:    314888

RPS: 7649.1 (requests/second)
Max: 859ms
Min: 0ms
Avg: 0ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 0ms
  98%   below 0ms
  99%   below 0ms
99.9%   below 0ms

调整成2G内存之后性能有一定的提升，但不明显