Vert.x sync seems to be very slow compared to the traditional vert.x callback style.
This example makes 10,000 calls to Redis.

To launch this example you will need to have a Redis server. You can launch one using docker:
$ docker run -p 6379:6379 redis

Do not forget specify the java agent paramer while launching the jvm:
-javaagent:/path/to/quasar/core/quasar-core.jar

Uncomment testCallBack to test the traditional callback style  
Uncomment testSync to test the vert.x sync style

Vert.x sync seems to be 10 times slower.
