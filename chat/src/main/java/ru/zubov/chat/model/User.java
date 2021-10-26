package ru.zubov.chat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 23.10.2021.
 */
@NoArgsConstructor
@Data
public class User {
    private int id;
    private String name;
    private String surname;
    private String pwd;

}
