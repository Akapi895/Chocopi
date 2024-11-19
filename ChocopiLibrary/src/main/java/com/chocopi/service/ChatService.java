package com.chocopi.service;

import com.chocopi.util.ApiConfig;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import com.chocopi.dao.ChatHistoryDAO;
import com.chocopi.model.ChatHistory;
import java.io.IOException;

public class ChatService {

    private static final String GEMINI_API_URL = ApiConfig.getGeminiApiUrl();
    private static final String GEMINI_API_KEY = ApiConfig.getGeminiApiKey();
    private static final String CONTEXT = "Bạn là một trợ lý thư viện. Hãy trả lời các câu hỏi liên quan đến sách, chính sách thư viện, hoặc lời khuyên về học tập. "
            + "Nếu câu hỏi không liên quan, hãy trả lời: 'Tôi không biết'.";

    public static String handleUserMessage(int userId, String userMessage) {
        System.out.println("Handling user message. UserID: " + userId + ", Message: " + userMessage);
        String response = getGeminiResponse(userMessage);

        if (response == null || response.isEmpty()) {
            response = "Hiện tại tôi không thể phản hồi. Vui lòng thử lại sau.";
        }

        ChatHistory chatHistory = new ChatHistory(userId, userMessage, response, new Timestamp(System.currentTimeMillis()));
        ChatHistoryDAO.saveChatHistory(chatHistory);

        return response;
    }

    private static String getGeminiResponse(String userMessage) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
//                System.out.println("Sending request to Gemini API. Attempt: " + attempt);
                RequestBody body = createGeminiRequestBody(userMessage);
                Request request = createGeminiRequest(body);

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
//                        System.out.println("Gemini API response: " + responseBody);
                        System.out.println(responseBody);
                        return parseGeminiResponse(responseBody);
                    } else {
                        System.err.println("Gemini API call failed. HTTP Code: " + response.code());
                    }
                }
            } catch (IOException e) {
                System.err.println("Gemini API call failed. Attempt: " + attempt + ". Error: " + e.getMessage());
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
        }

        return "Hệ thống đang quá tải. Vui lòng thử lại sau.";
    }

    private static RequestBody createGeminiRequestBody(String userMessage) {
        JSONObject json = new JSONObject();
        json.put("message", userMessage);
        json.put("context", CONTEXT);
        return RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
    }

    private static Request createGeminiRequest(RequestBody body) {
        return new Request.Builder()
                .url(GEMINI_API_URL + "?key=" + GEMINI_API_KEY)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
    }

    private static String parseGeminiResponse(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            if (json.has("response")) {
                return json.getString("response");
            }
        } catch (Exception e) {
            System.err.println("Error parsing Gemini API response: " + e.getMessage());
        }
        return "Phản hồi từ chatbot không hợp lệ.";
    }
}
