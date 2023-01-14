package com.makalu.hrm.service.impl;

import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Override

    public RestResponseDto sendMail(String to, String subject, String password) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText("You have been registered to " +
                    "MakaluHRM with username: " + to + " password: " + password);
            javaMailSender.send(mail);
            return RestResponseDto.INSTANCE().success();
        } catch (Exception ex) {
            log.error("Error sending mail", ex);
            return RestResponseDto.INSTANCE().internalServerError();
        }
    }
}
