package com.feiwanghub.openai.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatGPTService {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey;

    public ChatGPTService(@Value("${openai.apiKey}") String apiKey) {
        this.apiKey = apiKey;
    }

    public String generateText(String prompt) throws IOException {
        RequestBody requestBody = RequestBody.create(
                "{\"prompt\": \"" + prompt + "\", \"temperature\": 0.5}",
                JSON
        );
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/engines/davinci-codex/completions")
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}

