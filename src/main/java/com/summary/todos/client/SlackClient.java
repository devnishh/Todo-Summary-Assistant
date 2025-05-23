package com.summary.todos.client;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SlackClient {

    @Value("${slack.webhook.url}")
    private String webhookUrl;

    private final OkHttpClient client = new OkHttpClient();

    public void sendSummary(String summary) throws IOException {
        String json = String.format("{\"text\": \"To-Do Summary:\\n%s\"}", summary);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(webhookUrl)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        }
    }
}