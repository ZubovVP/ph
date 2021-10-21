package ru.zubov.chat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.zubov.chat.model.Msg;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 21.10.2021.
 */
@Service
@Slf4j
public class ScheduledService {
    private final SimpMessagingTemplate simpMessagingTemplate;


    public ScheduledService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(fixedDelayString = "${interval}")
    public void computePrice() {
        log.info("Add message");
        simpMessagingTemplate.convertAndSend("/chat/1/save",
                new Msg(1, "DUKE", "TEST MESSAGE"));
        log.info("Finish Adding message");
    }
}
