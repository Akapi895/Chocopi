package com.chocopi.service;

import com.chocopi.model.Book;
import com.chocopi.util.ApiConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookService {
    private static String GoogleBookApi = ApiConfig.getGoogleBooksApiKey();
    private static String GoogleBookURL = ApiConfig.getGoogleBooksURL();
    private static String SerperApi = ApiConfig.getSerperApiKey();
    private static String SerperURL = ApiConfig.getSerperApiURL();
    public static final String basePath = System.getProperty("user.dir") + "/src/main/resources/com/chocopi/images/book";

    public static List<Book> fetchBooksInfo(String query) {
        List<Book> books = new ArrayList<>();
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String urlString = GoogleBookURL + encodedQuery + "&key=" + GoogleBookApi;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray items = jsonResponse.getAsJsonArray("items");

                if (items != null && items.size() > 0) {
                    for (int i = 0; i < items.size(); i++) {
                        JsonObject item = items.get(i).getAsJsonObject();
                        JsonObject volumeInfo = item.getAsJsonObject("volumeInfo");

                        String bookTitle = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "Unknown Title";
                        String author = volumeInfo.has("authors") && volumeInfo.getAsJsonArray("authors").size() > 0
                                ? volumeInfo.getAsJsonArray("authors").get(0).getAsString()
                                : "Unknown Author";
                        String genre = volumeInfo.has("categories") && volumeInfo.getAsJsonArray("categories").size() > 0
                                ? volumeInfo.getAsJsonArray("categories").get(0).getAsString()
                                : "Unknown Genre";
                        String yearString = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : "0";
                        int year = (yearString.matches("\\d{4}")) ? Integer.parseInt(yearString) : 2000 + new Random().nextInt(24);
                        int rating = 3 + new Random().nextInt(3);
                        String image = volumeInfo.has("imageLinks") && volumeInfo.getAsJsonObject("imageLinks").has("thumbnail")
                                ? volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString()
                                : "";
//                        String image = volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString();
                        String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No Description";

                        System.out.println(bookTitle + ": " + image);

                        Book newBook = new Book(bookTitle, description, image, genre, rating, 1000000, author,
                                year, volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString() : "Unknown Publisher");

                        books.add(newBook);
                    }
                }
            } else {
                System.err.println("GET request failed. Response Code: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public static String fetchBookImage(int bookId, String bookName) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String imagePath = null; // Biến lưu đường dẫn ảnh

        try {
            File directory = new File(basePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"q\":\"" + bookName + "\",\"gl\":\"vn\"}");
            Request request = new Request.Builder()
                    .url(SerperURL)
                    .method("POST", body)
                    .addHeader("X-API-KEY", SerperApi)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonResponse);

                JSONArray images = jsonObject.optJSONArray("images");
                if (images != null && images.length() > 0) {
                    String imageUrl = images.getJSONObject(0).getString("url");

                    imagePath = basePath + "/" + bookId + ".jpg";
                    downloadImage(imageUrl, imagePath);
                    System.out.println("Ảnh cho sách '" + bookName + "' đã được lưu vào " + imagePath);
                } else {
                    System.out.println("Không tìm thấy ảnh cho sách: " + bookName);
                }
            } else {
                System.out.println("Lỗi khi gọi API: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imagePath;
    }

    /**
     * Tải ảnh từ URL và lưu vào đường dẫn chỉ định.
     *
     * @param imageUrl URL của ảnh.
     * @param outputPath Đường dẫn lưu ảnh.
     */
    private static void downloadImage(String imageUrl, String destinationPath) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.copy(in, Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Lỗi khi tải ảnh từ URL: " + imageUrl);
            e.printStackTrace();
        }
    }
}