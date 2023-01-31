package com.makalu.hrm.service.impl;

import com.makalu.hrm.controller.MvcBaseTest;
import com.makalu.hrm.service.MailTemplateParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

public class MailTemplateParserTest extends MvcBaseTest {

    @Autowired
    private MailTemplateParser mailTemplateParser;

    @Test
    public void sendPasswordTemplate(){
        String password = UUID.randomUUID().toString();
        String output = mailTemplateParser.sendPasswordTemplate(Map.of("user", "Test", "password", password));
        Assertions.assertTrue(output.contains("Test"));
        Assertions.assertTrue(output.contains(password));
    }
}
