package com.feiwanghub;

import io.micronaut.http.annotation.*;

@Controller("/")
public class MicronautController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Micronaut Example Response";
    }
}