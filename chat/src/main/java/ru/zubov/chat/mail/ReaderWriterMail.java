package ru.zubov.chat.mail;


import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ru.zubov.chat.model.Email;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 23.10.2021.
 */
@Slf4j
public class ReaderWriterMail implements ReadWriteEmailAble {
    private final String host;
    private final String email;
    private final String pwd;
    private final JavaMailSender sender;

    public ReaderWriterMail(String host, String email, String pwd, JavaMailSender sender) {
        this.host = host;
        this.email = email;
        this.pwd = pwd;
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
        List<Email> msgs = new ArrayList<>();
        try {
            Properties properties = new Properties();
            properties.put("mail.imap.host", this.host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.starttls.enable", "true");
            properties.put("mail.imap.ssl.trust", this.host);
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imaps");
            store.connect(this.host, this.email, this.pwd);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            Message[] messages = inbox.search(
                    new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            for (var msg : messages) {
                msgs.add(new Email(0, null, msg.getFrom()[0].toString(), msg.getSubject(), msg.getContent().toString(), msg.getSentDate()));
            }
            log.info("Read emails success, email - {}.", this.email);
            inbox.setFlags(messages, new Flags(Flags.Flag.SEEN), true);
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            log.error("Read emails failed, email - {}.", this.email);
            e.printStackTrace();
        }

        return msgs;
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

