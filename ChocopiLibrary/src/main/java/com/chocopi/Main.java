package com.chocopi;

import com.chocopi.dao.*;
import com.chocopi.model.*;
import com.chocopi.util.*;
import com.chocopi.service.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();

        String query = "Tây du kí";
        bookDAO.addBooksByQuery(query);

//        try {
//            // Lấy danh sách sách chưa có ảnh
//            List<Book> booksWithoutImage = bookDAO.getBooksWithoutImage();
//
//            // Duyệt qua từng sách và tải ảnh
//            for (Book book : booksWithoutImage) {
//                String imageName = book.getTitle();
//                String imagePath = BookService.fetchBookImage(book.getBookId(), imageName);
//
//                // Cập nhật đường dẫn ảnh vào đối tượng Book
//                book.setImage(imagePath);
//
//                // Cập nhật thông tin ảnh vào database
//                bookDAO.updateBookImage(book);
//
//                System.out.println("Đã lưu ảnh và cập nhật cho sách: " + book.getTitle());
//            }
//
//            System.out.println("Hoàn thành quá trình tải và cập nhật ảnh.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
