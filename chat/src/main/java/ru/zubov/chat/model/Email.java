package ru.zubov.chat.model;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 23.10.2021.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Email {
    private int id;
    private String to;
    private String from;
    private String subject;
    private String body;
    private Date DateTime;
}
