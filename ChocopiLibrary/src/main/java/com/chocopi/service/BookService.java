package com.chocopi.service;

import com.chocopi.dao.BookDAO;
import com.chocopi.model.Book;
import com.chocopi.util.ApiConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class BookService {
    private static String GoogleBookApi = ApiConfig.getInstance().getGoogleBooksApiKey();
    public static String GoogleBookURL = ApiConfig.getInstance().getGoogleBooksURL();
    private static HttpClient httpClient = null;

    public BookService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public static List<Book> fetchBooksInfo(String query) {
            List<Book> books = new ArrayList<>();

            try {
                // Tạo HTTP request
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(GoogleBookURL + query + "&key=" + GoogleBookApi))
                        .GET()
                        .build();

                // Gửi request và nhận phản hồi
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    // Parse JSON từ phản hồi
                    JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                    JsonArray items = jsonResponse.has("items") ? jsonResponse.getAsJsonArray("items") : null;

                    if (items != null) {
                        for (int i = 0; i < items.size(); i++) {
                            JsonObject item = items.get(i).getAsJsonObject();
                            JsonObject volumeInfo = item.getAsJsonObject("volumeInfo");

                            // Lấy thông tin sách
                            String title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "Unknown Title";
                            String author = volumeInfo.has("authors") && volumeInfo.getAsJsonArray("authors").size() > 0
                                    ? volumeInfo.getAsJsonArray("authors").get(0).getAsString()
                                    : "Unknown Author";
                            String genre = volumeInfo.has("categories") && volumeInfo.getAsJsonArray("categories").size() > 0
                                    ? volumeInfo.getAsJsonArray("categories").get(0).getAsString()
                                    : "Unknown Genre";
                            String yearString = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : "2000";
                            int year = yearString.matches("\\d{4}") ? Integer.parseInt(yearString) : 2000;
                            int rating = 3 + new Random().nextInt(3);

                            String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No Description";
                            String publisher = volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString() : "Unknown Publisher";

                            String image = null;
                            if (volumeInfo.has("imageLinks")) {
                                JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                                image = imageLinks.has("thumbnail") ? imageLinks.get("thumbnail").getAsString() : null;
                            }

                            if (image == null) {
                                image = "http://books.google.com/books/content?id=uIO2DgAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api";
                            }

                            // Tạo đối tượng Book và thêm vào danh sách
                            Book book = new Book(title, description, image, genre, rating, 1000000, author, year, publisher);
                            books.add(book);
                        }
                    }
                } else {
//                    System.err.println("Failed to fetch books. HTTP Status Code: " + response.statusCode());
                    System.out.println();
                }
            } catch (Exception e) {
                System.err.println("Error fetching books: " + e.getMessage());
                e.printStackTrace();
            }

            return books;
        }

    public static List<Book> fetchBooksAdmin(String query) {
        Path imagesDirectory = Paths.get("src/main/resources/com/chocopi/images/book/genBook");
        try (Stream<Path> paths = Files.walk(imagesDirectory)) {
            paths.filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            Files.delete(file);
                        } catch (IOException e) {
                            System.err.println("Failed to delete file: " + file);
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            System.err.println("Failed to clean up images directory.");
            e.printStackTrace();
        }

        List<Book> books = new ArrayList<>();
        int totalBooksToFetch = 18;
        int maxResultsPerRequest = 10;
        int startIndex = 0;

        try {
            BookDAO bookDAO = new BookDAO();
            int lastBookId = bookDAO.lastBookId();

            while (books.size() < totalBooksToFetch) {
                int booksRemaining = totalBooksToFetch - books.size();
                int currentMaxResults = Math.min(booksRemaining, maxResultsPerRequest);

                if (query == null || query.trim().isEmpty()) {
                    throw new IllegalArgumentException("Query string cannot be null or empty.");
                }
                String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
                String urlString = GoogleBookURL + encodedQuery
                        + "&key=" + GoogleBookApi
                        + "&startIndex=" + startIndex
                        + "&maxResults=" + currentMaxResults;

                HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                    StringBuilder response = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                    JsonArray items = jsonResponse.has("items") ? jsonResponse.getAsJsonArray("items") : null;

                    if (items == null || items.size() == 0) {
                        System.out.println("No more books available for the query.");
                        break;
                    }

                    for (JsonElement itemElement : items) {
                        lastBookId++;
                        JsonObject item = itemElement.getAsJsonObject();
                        JsonObject volumeInfo = item.has("volumeInfo") ? item.getAsJsonObject("volumeInfo") : new JsonObject();

                        // Lấy thông tin sách
                        String bookTitle = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "Unknown Title";
                        String author = getJsonArrayFirstItem(volumeInfo, "authors", "Unknown Author");
                        String genre = getJsonArrayFirstItem(volumeInfo, "categories", "Unknown Genre");
                        String yearString = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : "2000";
                        int year = yearString.matches("\\d{4}") ? Integer.parseInt(yearString) : 2000;
                        int rating = 3 + new Random().nextInt(3);

                        // Xử lý ảnh sách
                        String image = extractImageUrl(volumeInfo);
                        String destinationPath = "src/main/resources/com/chocopi/images/book/genBook/" + lastBookId + ".jpg";
                        BookService.downloadImage(image, destinationPath);
                        image = "/com/chocopi/images/book/genBook/" + lastBookId + ".jpg";


                        // Thêm sách mới vào danh sách
                        String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No Description";
                        String publisher = volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString() : "Unknown Publisher";

                        Book newBook = new Book(bookTitle, description, image, genre, rating, 1000000, author, year, publisher);
                        books.add(newBook);

                    }
                }
                startIndex += maxResultsPerRequest;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public static String extractImageUrl(JsonObject volumeInfo) {
        String image = null;
        if (volumeInfo.has("imageLinks")) {
            JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
            if (imageLinks != null && imageLinks.has("thumbnail")) {
                image = imageLinks.get("thumbnail").getAsString();
            }
        }
        return image == null ? "http://books.google.com/books/content?id=uIO2DgAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api" : image;
    }

    public static String getJsonArrayFirstItem(JsonObject volumeInfo, String key, String defaultValue) {
        if (volumeInfo.has(key) && volumeInfo.getAsJsonArray(key).size() > 0) {
            return volumeInfo.getAsJsonArray(key).get(0).getAsString();
        }
        return defaultValue;
    }

    public static void downloadImage(String imageUrl, String destinationPath) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            Files.copy(inputStream, Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
