package com.yinghuomars.microblog.consumer;

import com.alibaba.fastjson.JSON;
import com.yinghuomars.microblog.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static com.yinghuomars.microblog.constant.MqPrefixConst.EMAIL_QUEUE;

/**
 * 通知邮箱
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/06/13
 * @version 1.0.0
 * @since 1.0.0
 **/
@Component
@EnableConfigurationProperties(MailProperties.class)
@RequiredArgsConstructor
@RabbitListener(queues = EMAIL_QUEUE)
public class EmailConsumer {

    @Value("${spring.mail.nickname}")
    private String nickname;

    private final MailProperties properties;

    private final JavaMailSender javaMailSender;

    @RabbitHandler
    public void process(byte[] data) {
        EmailDTO emailDTO = JSON.parseObject(new String(data), EmailDTO.class);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(nickname + "<" + properties.getUsername() + ">");
        message.setTo(emailDTO.getEmail());
        message.setSubject(emailDTO.getSubject());
        message.setText(emailDTO.getContent());
        javaMailSender.send(message);
    }

}
