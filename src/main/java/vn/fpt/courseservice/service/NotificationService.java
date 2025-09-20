package vn.fpt.courseservice.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface NotificationService {
    void sendNotification(String to, String subject, String content) throws MessagingException, UnsupportedEncodingException;
}
