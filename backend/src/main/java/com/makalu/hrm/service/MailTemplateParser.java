package com.makalu.hrm.service;

import java.util.Map;

public interface MailTemplateParser {
    String sendPasswordTemplate(Map<String, Object> context);

    String sendResetPasswordTemplate(Map<String, Object> context);
}
