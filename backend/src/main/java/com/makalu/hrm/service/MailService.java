package com.makalu.hrm.service;

import com.makalu.hrm.model.RestResponseDto;

public interface MailService {

    RestResponseDto sendMail(String to, String subject, String password);

    RestResponseDto sendHtmMail(String to, String subject, String body);
}
