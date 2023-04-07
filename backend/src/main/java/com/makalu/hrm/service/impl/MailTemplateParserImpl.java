package com.makalu.hrm.service.impl;

import com.makalu.hrm.service.MailTemplateParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailTemplateParserImpl implements MailTemplateParser {

    private final TemplateEngine templateEngine;

    @Override
    public String sendPasswordTemplate(Map<String, Object> context){
        Context ctx = new Context();
        context.forEach(ctx::setVariable);
        return templateEngine.process("/mail/password.html",ctx);
    }

    @Override
    public String sendResetPasswordTemplate(Map<String, Object> context){
        Context ctx = new Context();
        context.forEach(ctx::setVariable);
        return templateEngine.process("/mail/resetPassword.html",ctx);
    }
}
