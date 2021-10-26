package ru.zubov.chat.mail;


import ru.zubov.chat.model.Email;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 23.10.2021.
 */
public interface ReadWriteEmailAble {

    List<Email> readAllMessages();

    List<Email> readOnlyNewMessage();

    List<Email> readLastMessage(int count);

    boolean write(Email email, String password);
}
