package ru.zubov.chat.service;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.zubov.chat.mail.ReadWriteEmailAble;
import ru.zubov.chat.mail.ReaderWriterMail;
import ru.zubov.chat.model.Email;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 26.10.2021.
 */
@Service
@Slf4j
@PropertySource("classpath:propForEmail.properties")
public class EmailService {
    private final ReadWriteEmailAble readWriteEmailAble;

    public EmailService(ReadWriteEmailAble readWriteEmailAble) {
        this.readWriteEmailAble = readWriteEmailAble;
    }


    public List<Email> readAllMessages() {
        return this.readWriteEmailAble.readAllMessages();
    }

    public List<Email> readLastMessage(int count) {
        return this.readWriteEmailAble.readLastMessage(count);
    }

    public List<Email> readOnlyNewMessage() {
        return this.readWriteEmailAble.readOnlyNewMessage();
    }


}
