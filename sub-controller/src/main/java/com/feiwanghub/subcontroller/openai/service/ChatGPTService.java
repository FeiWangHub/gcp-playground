package com.feiwanghub.subcontroller.openai.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.*;
import org.jetbrains.annotations.TestOnly;
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
                "{\"prompt\": \"" + prompt + "\", \"temperature\": 0.5, \"model\": \"text-davinci-003\", \"max_tokens\": 100}",
                JSON
        );
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @TestOnly
    public String generateTextByOpenAI(String prompt) {
        OpenAiService service = new OpenAiService(apiKey);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(prompt)
                .maxTokens(100)
                .temperature(0.5)
                .build();
        CompletionResult completion = service.createCompletion(completionRequest);
        completion.getChoices().forEach(choice -> System.out.println(choice.getText()));
        return completion.getChoices().get(0).getText();
    }
}

