package com.chocopi.service;

import com.chocopi.dao.BookDAO;
import com.chocopi.model.Book;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private HttpClient httpClientMock;
    private HttpResponse<String> httpResponseMock;
    private BookService bookService;
    private BookDAO bookDAOMock;

    @BeforeEach
    void setUp() {
        httpClientMock = mock(HttpClient.class);
        httpResponseMock = mock(HttpResponse.class);

        bookDAOMock = mock(BookDAO.class);

        bookService = new BookService(httpClientMock);
    }

    @Test
    void fetchBooksInfo_validResponse_returnsBooks() throws Exception {
        String mockResponse = """
        {
          "items": [
            {
              "volumeInfo": {
                "title": "Sample Book",
                "authors": ["John Doe"],
                "categories": ["Fiction"],
                "publishedDate": "2020",
                "description": "A sample book for testing.",
                "publisher": "Sample Publisher",
                "imageLinks": {
                  "thumbnail": "http://example.com/sample.jpg"
                }
              }
            }
          ]
        }
        """;

        when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponseMock);
        when(httpResponseMock.statusCode()).thenReturn(200);
        when(httpResponseMock.body()).thenReturn(mockResponse);

        List<Book> books = bookService.fetchBooksInfo("sample");

        assertNotNull(books);
        assertEquals(1, books.size());

        Book book = books.get(0);
        assertEquals("Sample Book", book.getTitle());
        assertEquals("John Doe", book.getAuthor());
        assertEquals("Fiction", book.getGenre());
        assertEquals("Sample Publisher", book.getPublisher());
        assertEquals("A sample book for testing.", book.getDescription());
        assertEquals("http://example.com/sample.jpg", book.getImage());
    }

    @Test
    void fetchBooksInfo_invalidResponse_returnsEmptyList() throws Exception {
        when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponseMock);
        when(httpResponseMock.statusCode()).thenReturn(400);
        when(httpResponseMock.body()).thenReturn("");

        List<Book> books = bookService.fetchBooksInfo("invalid");

        assertNotNull(books);
        assertTrue(books.isEmpty());
    }
}
