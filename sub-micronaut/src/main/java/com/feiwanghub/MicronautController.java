package com.feiwanghub;

import com.feiwanghub.hellomicronaut.HelloHttpClient;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

import java.util.concurrent.CompletableFuture;

@Controller("/")
public class MicronautController {

    @Inject
    HelloHttpClient helloHttpClient;

    @Get(uri = "/", produces = "text/plain")
    public String index() {
        return "Micronaut Example Response";
    }

    @Get("/httpClientBaidu")
    public CompletableFuture<String> testHttpClientBaidu() {
        return helloHttpClient.baiduHome().thenApplyAsync(resp -> resp);
    }
}