package ru.zubov.chat.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ru.zubov.chat.mail.ReadWriteEmailAble;
import ru.zubov.chat.mail.ReaderWriterMail;

import java.util.Properties;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 26.10.2021.
 */
@Configuration
@PropertySource("classpath:propForEmail.properties")
public class EmailConfig {
    @Bean
    public JavaMailSender createRearWriterEmailForGmail(@Value("${spring.mail.host}") String host,
                                                        @Value("${spring.mail.username}") String username,
                                                        @Value("${spring.mail.password}") String pwd,
                                                        @Value("${spring.mail.port}") int port) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        //"my.gmail@gmail.com"
        mailSender.setUsername(username);
        mailSender.setPassword(pwd);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }


    @Bean
    public ReadWriteEmailAble getReadWriteEmailAble(@Value("${spring.mail.pop3.host}") String host,
                                                    @Value("${spring.mail.username}") String email,
                                                    @Value("${spring.mail.password}") String pwd,
                                                    @Qualifier("createRearWriterEmailForGmail") JavaMailSender sender) {
        return new ReaderWriterMail(host, email, pwd, sender);
    }

}
