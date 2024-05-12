package com.feiwanghub.hellomicronaut;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.concurrent.CompletableFuture;

@Client("https://www.baidu.com") // (1)
public interface HelloHttpClient {

    @Get("/")
    CompletableFuture<String> baiduHome();
}

