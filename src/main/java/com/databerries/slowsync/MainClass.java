package com.databerries.slowsync;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

/**
 * Created by nexux on 09/12/15.
 */
public class MainClass {

    public static void main(String[] args) {
        // access to the core api
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle("com.databerries.slowsync.SyncVertx");

    }

}
