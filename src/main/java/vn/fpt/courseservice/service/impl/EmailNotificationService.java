package vn.fpt.courseservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.fpt.courseservice.service.NotificationService;

@Service
@Slf4j
public class EmailNotificationService implements NotificationService {

    @Override
    public void sendNotification(String message) {
        log.info("Email Notification Service");
    }

}
