package com.feiwanghub.openai.model;

import java.util.Date;
import java.util.List;

public record GPTResponse(
        String id,
        String object,
        Date created,
        String model,
        List<String> choices,
        Object usage) {
}
