package com.makalu.hrm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makalu.hrm.Application;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = Application.class)
@ContextConfiguration
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles({"test"})
public class MvcBaseTest {

    @LocalServerPort
    protected int port;

   protected ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected MockMvc mvc;

    protected EasyRandom rand = new EasyRandom();

    @MockBean
    protected JavaMailSender javaMailSender;

    @BeforeEach
    public void setup(){
    }

    protected String getURL(String action) {
        return "http://localhost:" + port + "/" + action;
    }

}
