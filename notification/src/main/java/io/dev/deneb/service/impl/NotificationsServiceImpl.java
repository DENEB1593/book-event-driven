package io.dev.deneb.service.impl;

import io.dev.deneb.model.Notification;
import io.dev.deneb.service.NotificationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationsServiceImpl implements NotificationsService {

    @Override
    public Notification save(Notification book) {
        return null;
    }

    @Override
    public Page<Notification> listNotifications(Pageable pageable) {
        return null;
    }
}
