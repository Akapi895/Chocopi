package com.chocopi.service;

import com.chocopi.dao.UserDAO;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    private static final String smtpHost = "smtp.gmail.com";
    private static final int smtpPort = 587;
    private static final String username = "tuanpham08092005@gmail.com";
    private static final String password = "imbi ttyd vzzw hqui";

    public static boolean resetPassword(String email) {
        int userId = UserDAO.getUserIdByEmail(email);

        if (userId != 0) {
            String defaultPassword = "123"; // Mật khẩu mặc định
            boolean isUpdated = UserDAO.updateUserPassword(userId, defaultPassword);

            if (isUpdated) {
                String subject = "Reset Password - Chocopi Library";
                String message = "Your password has been reset to: " + defaultPassword
                        + "\n\nPlease login and change your password for better security.";

                // Gửi email thông báo cho người dùng
                sendEmail(email, subject, message);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public static void sendEmail(String recipientEmail, String subject, String messageText) {
        // Thiết lập cấu hình SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", String.valueOf(smtpPort));

        // Tạo Session với thông tin xác thực
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageText);

            // Gửi email
            Transport.send(message);
            System.out.println("Email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void sendHtmlEmail(String recipientEmail, String subject, String htmlContent) {
        // Thiết lập cấu hình SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", String.valueOf(smtpPort));

        // Tạo Session với thông tin xác thực
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo email với nội dung HTML
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // Gửi email
            Transport.send(message);
            System.out.println("HTML Email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send HTML email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
