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
        //        try {
//            Properties properties = new Properties();
//            properties.put("mail.imap.host", host);
//            properties.put("mail.imap.port", "993");
//            properties.put("mail.imap.starttls.enable", "true");
//            properties.put("mail.imap.ssl.trust", host);
//            Session emailSession = Session.getDefaultInstance(properties);
//            Store store = emailSession.getStore("imaps");
//            store.connect(this.host, this.email, this.pwd);
//            Folder inbox = store.getFolder("INBOX");
//            inbox.open(Folder.READ_ONLY);
//            for (Message msg : inbox.getMessages()) {
//                msgs.add(new Email(0, null, msg.getFrom()[0].toString(), msg.getSubject(), msg.getContent().toString(),  msg.getSentDate()));
//                if (msgs.size() > 10) {
//                    return msgs;
//                }
//            }
//            log.info("Read emails success, email - {}.", this.email);
//            inbox.close(false);
//            store.close();
//        } catch (Exception e) {
//            log.error("Read emails failed, email - {}.", this.email);
//            e.printStackTrace();
//        }
        return this.readWriteEmailAble.readAllMessages();
    }

    public List<Email> readLastMessage(int count) {
        return this.readWriteEmailAble.readLastMessage(count);
    }


}
