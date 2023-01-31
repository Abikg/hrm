package com.makalu.hrm.service.impl;

import com.makalu.hrm.service.MailTemplateParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailTemplateParserImpl implements MailTemplateParser {

    private final SpringTemplateEngine springTemplateEngine;

    @Override
    public String sendPasswordTemplate(Map<String, Object> context){
        Context ctx = new Context();
        context.forEach(ctx::setVariable);
        return springTemplateEngine.process("/mail/password.html",ctx);
    }
}
