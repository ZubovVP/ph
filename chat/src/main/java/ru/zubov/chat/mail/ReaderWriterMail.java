package ru.zubov.chat.mail;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.zubov.chat.model.Email;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 23.10.2021.
 */
@Slf4j
@Component
public class ReaderWriterMail implements ReadWriteEmailAble {
    @Value("${spring.mail.pop3.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String email;
    @Value("${spring.mail.password}")
    private String pwd;
    private final JavaMailSender sender;

    public ReaderWriterMail(@Qualifier("createRearWriterEmailForGmail") JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public List<Email> readAllMessages() {
        return getMessages(0);
    }

    public List<Email> readLastMessage(int count) {
        return getMessages(count);
    }

    @Override
    public List<Email> readOnlyNewMessage() {
        return null;
    }


    private List<Email> getMessages(int size) {
        List<Email> msgs = new ArrayList<>();
        try {
            Properties properties = new Properties();
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.starttls.enable", "true");
            properties.put("mail.imap.ssl.trust", host);
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imaps");
            store.connect(this.host, this.email, this.pwd);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            int count = size == 0 ? inbox.getMessageCount() : size;
            for (int x = 0; x < count; x++) {
                if (inbox.getMessages().length < x) {
                    return msgs;
                }
                var msg = inbox.getMessages()[inbox.getMessages().length - 1 - x];
                msgs.add(new Email(0, null, msg.getFrom()[0].toString(), msg.getSubject(), msg.getContent().toString(), msg.getSentDate()));
            }
            log.info("Read emails success, email - {}.", this.email);
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            log.error("Read emails failed, email - {}.", this.email);
            e.printStackTrace();
        }
        return msgs;
    }

    @Override
    public boolean write(Email email, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        this.sender.send(message);
        return true;
    }
}

