package io.dev.deneb.service;


import io.dev.deneb.model.Notification;

public interface NotificationService {
    void publishNotification(Notification notification);
}
