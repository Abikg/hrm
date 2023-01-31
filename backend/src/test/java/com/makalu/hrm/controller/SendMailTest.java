package com.makalu.hrm.controller;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


public class SendMailTest extends  MvcBaseTest {

    @Autowired
    private   SpringTemplateEngine springTemplateEngine;

    @Test
    public void springTemplateEngine(){
        Context context = new Context();
        context.setVariable("email","testemail@gmail.com");
        String emailBody = springTemplateEngine.process("/mail/password.html",context);
        System.out.printf("Emailbody=>"+emailBody);
        Assertions.assertFalse(false);
    }
}
