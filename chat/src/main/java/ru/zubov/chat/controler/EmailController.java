package ru.zubov.chat.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.zubov.chat.model.Email;
import ru.zubov.chat.service.EmailService;

import java.util.List;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 25.10.2021.
 */
@RestController
public class EmailController {
    private final EmailService readerWriterMail;

    public EmailController(EmailService readerWriterMail) {
        this.readerWriterMail = readerWriterMail;
    }

    @GetMapping("/read")
    public List<Email> checkEmail() {
        return this.readerWriterMail.readAllMessages();
    }

    @GetMapping("/last/{count}")
    public List<Email> readLastMessage(@PathVariable int count) {
        return this.readerWriterMail.readLastMessage(count);
    }


    @GetMapping("/readNew")
    public List<Email> readOnlyNewMessage(){
        return this.readerWriterMail.readOnlyNewMessage();
    }
}
