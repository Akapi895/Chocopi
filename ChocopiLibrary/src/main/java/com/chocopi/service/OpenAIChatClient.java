package com.chocopi.service;

import com.chocopi.dao.BookDAO;
import com.chocopi.dao.UserDAO;
import com.chocopi.model.User;
import com.chocopi.util.ApiConfig;
import com.chocopi.util.SessionManager;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OpenAIChatClient {
    private static final String API_URL = ApiConfig.getOpenaiApiUrl();
    private final String apiKey;

    public OpenAIChatClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public String sendMessage(String query) throws IOException {
        String allBooksInLib = BookDAO.getAllBookIdandTitle();
        User user = UserDAO.getUserById(SessionManager.getUserId());
        String userInfo = user.userInfo();

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4");

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", "You are a helpful online library assistant and you will answer question using user's language. Your library has: " + allBooksInLib));
        messages.put(new JSONObject().put("role", "user").put("content", "This is my personal information: " + userInfo + "I have a question: " + query));
        requestBody.put("messages", messages);

        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.get("application/json; charset=utf-8")
        );

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                JSONObject jsonResponse = new JSONObject(response.body().string());
                JSONArray choices = jsonResponse.getJSONArray("choices");
                String content = choices.getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
                return content;
            } else {
                throw new IOException("Request failed with code: " + response.code() + " - " + response.message());
            }
        }
    }

    public static String handleUserQuestion(String query) {
        OpenAIChatClient chatClient = new OpenAIChatClient(ApiConfig.getOpenaiApiKey());

        String responseContent = null;
        try {
            responseContent = chatClient.sendMessage(query);
//            System.out.println("Response Content: " + responseContent);
        } catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
        if (responseContent == null) responseContent = "Vui lòng đợi trong giây lát...";
        return responseContent;
    }
}