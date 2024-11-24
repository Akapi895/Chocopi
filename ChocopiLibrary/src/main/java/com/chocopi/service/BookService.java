package com.chocopi.service;

import com.chocopi.dao.BookDAO;
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
import java.io.BufferedReader;
import java.io.InputStreamReader;
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

                BookDAO bookDAO = new BookDAO();
                int lastBookId = bookDAO.lastBookId();

                if (items != null && items.size() > 0) {
                    for (int i = 0; i < items.size(); i++) {
                        lastBookId++;
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

                        String image = null;
                        if (volumeInfo.has("imageLinks")) {
                            JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                            if (imageLinks != null && imageLinks.has("thumbnail")) {
                                image = imageLinks.get("thumbnail").getAsString();
                            } else if (imageLinks != null && imageLinks.has("smallThumbnail")) {
                                image = imageLinks.get("smallThumbnail").getAsString();
                            }
                        } else {
                            image = "http://books.google.com/books/content?id=uIO2DgAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api";
                        }

                        String destinationPath = "src/main/resources/com/chocopi/images/book/" + lastBookId + ".jpg";

                        BookService.downloadImage(image, destinationPath);

                        String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No Description";

//                        System.out.println(bookTitle + ": " + image);

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

//    private static void downloadImage(String imageUrl, String destinationPath) {
//        try (InputStream in = new URL(imageUrl).openStream()) {
//            Files.copy(in, Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            System.out.println("Lỗi khi tải ảnh từ URL: " + imageUrl);
//            e.printStackTrace();
//        }
//    }

    public static String searchImageUrl(String keyword) {
        String apiKey = "la2IM83kwlxbo4wh06CgZGuinVBm3JwqLCtYvjyHztk";
        String apiUrl = "https://api.unsplash.com/search/photos?query=" + keyword + "&client_id=" + apiKey;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON để lấy URL của ảnh
            String jsonResponse = response.toString();
            // Dùng thư viện như Gson hoặc Jackson để parse JSON.
            return parseImageUrlFromJson(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }
    }

    public static void downloadImage(String imageUrl, String destinationPath) {
        ensureDirectoryExists(new File(destinationPath).getParent());
        try (BufferedInputStream in = new BufferedInputStream(new URL(imageUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(destinationPath)) {

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("Image downloaded to " + destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String parseImageUrlFromJson(String jsonResponse) {
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray resultsArray = jsonObject.getAsJsonArray("results");

            if (resultsArray.size() > 0) {
                JsonObject firstResult = resultsArray.get(0).getAsJsonObject();
                JsonObject urlsObject = firstResult.getAsJsonObject("urls");
                return urlsObject.get("regular").getAsString(); // Lấy URL kích thước thường
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}