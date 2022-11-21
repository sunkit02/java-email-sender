package com.sunkit;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class EmailSender {
    // your email credentials
    private static String username = "your email";
    // your app password (if using gmail)
    private static String password = "your password";


    public static void main(String[] args) {
        String to = "sunkittsui+receive@gmail.com";
        String from = "sunkit.programming@gmail.com";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Java Mail API Test");
            // reading the email from html file in resources folder
            // can be replaced with any String input
            message.setContent(
                    getHTML("./src/main/resources/email.html"),
                    "text/html");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // util method for extracting html to String from a file
    public static String getHTML(String filePath) {
        Path path = Path.of(filePath);
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}