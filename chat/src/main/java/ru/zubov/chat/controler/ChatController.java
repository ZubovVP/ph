package ru.zubov.chat.controler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.zubov.chat.model.Msg;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 20.10.2021.
 */
@PropertySource("classpath:application.properties")
@Controller
@Slf4j
public class ChatController {
    private final List<Msg> messages = new CopyOnWriteArrayList<>();

    @MessageMapping("/chat/{id}/save")
    @SendTo("/chat/{id}/save")
    public Msg save(@DestinationVariable int id, Msg message) {
        messages.add(message);
        return message;
    }
}
