package com.databerries.slowsync;

import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.Vertx;
import io.vertx.ext.sync.SyncVerticle;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

import static io.vertx.ext.sync.Sync.awaitEvent;
import static io.vertx.ext.sync.Sync.awaitResult;

/**
 * This class's job is to test the Vertx-Synch syntax
 */
public class SyncVertx extends SyncVerticle {


    long startTime;
    long endTime;
    long diffTime;

    static int i = 0;
    /** Redis client object */
    private RedisClient redisClient;

    /**
     * Start the verticle
     */
    @Override
    @Suspendable
    public void start() {
        // Create a redis client
        // Setup redis with configuration by default
        RedisOptions opt = new RedisOptions().setHost("192.168.99.100").setPort(6379);
        // create the redis client
        redisClient = RedisClient.create(Vertx.vertx(), opt);

        // begin to measure the time at the first call
        startTime = System.currentTimeMillis();

        // Add 10000 keys in redis
        for (int i = 0; i < 10000 ; i++) {
            /**
             * Uncomment testCallBack to test the traditional callback style
             * Uncomment testSync to test the vert.x sync style
             */
            testCallBack();
            // testSync();
        }

    }

    /**
     * Insert a value in Redis using Vert.x Sync
     */
    @Suspendable
    public void testSync() {
        Long result = awaitResult(h -> redisClient.setnx("foo", "bar", h));

        ++i;

        if (result == null) {
            System.out.println("Error");
        } else {
            System.out.println("Success");
        }

        if (i == 10000) {
            System.out.println("We are done ! ");
            endTime = System.currentTimeMillis();
            diffTime = endTime - startTime;
            System.out.println("diffTime = " + diffTime);
        }
    }

    /**
     * Insert a value in vert.x sync using a traditional callback
     */
    public void testCallBack() {
        redisClient.setnx("foo", "bar", ar -> {
            ++i;

            if (!ar.succeeded()) {
                System.out.println("Error");
            } else {
                System.out.println("Success");
            }

            if (i == 10000) {
                System.out.println("We are done ! ");
                endTime = System.currentTimeMillis();
                diffTime = endTime - startTime;
                System.out.println("diffTime = " + diffTime);
            }

        });
    }


}
