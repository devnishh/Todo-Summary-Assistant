package com.summary.todos.client;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CohereClient {

    @Value("${cohere.api.key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();

    public String summarize(String text) throws IOException {
        String json = String.format("{\"model\": \"command\", \"prompt\": \"Summarize the following to-do list in a concise paragraph:\\n%s\", \"max_tokens\": 100}", text);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("https://api.cohere.ai/v1/generate")
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();
            // Simplified parsing for brevity
            return responseBody.split("\"text\":\"")[1].split("\"")[0].trim();
        }
    }
}