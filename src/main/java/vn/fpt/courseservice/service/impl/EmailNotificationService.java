package vn.fpt.courseservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.fpt.courseservice.service.NotificationService;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService implements NotificationService {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendNotification(String to, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(from, "E-Learning");
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setText(content);

        mailSender.send(mimeMessage);

        log.info("Send email successfully for user: {}", to);
    }

}
