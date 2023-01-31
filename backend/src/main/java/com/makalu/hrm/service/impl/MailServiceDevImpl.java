package com.makalu.hrm.service.impl;

import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile({"dev", "test"})
public class MailServiceDevImpl implements MailService {

    @Override
    public RestResponseDto sendMail(String to, String subject, String password) {
        try {
            log.info("email has been sent to " + to);
            return RestResponseDto.INSTANCE().success();
        } catch (Exception ex) {
            log.error("Error sending mail", ex);
            return RestResponseDto.INSTANCE().internalServerError();
        }
    }
}
