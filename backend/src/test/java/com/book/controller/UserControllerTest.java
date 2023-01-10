/*
package com.book.controller;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends MvcBaseTest{

    @Test
    @Transactional
    @Sql(
            scripts = "/user.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName="testUserDetails")
    public void whomiamSystem() throws Exception {
        this.mvc.perform(get("/user/whomiam"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("system@gmail.com")))
                .andExpect(jsonPath("$.userType", is("SYSTEM")))
                .andExpect(jsonPath("$.authorities", hasSize(2)))
                .andExpect(jsonPath("$.authorities.[0].authority", is("ROLE_AUTHENTICATED")))
                .andExpect(jsonPath("$.authorities.[1].authority", is("ROLE_SYSTEM")))
                .andReturn();
    }

    @Test
    @Transactional
    @Sql(
            scripts = "/user.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "employer@gmail.com", userDetailsServiceBeanName="testUserDetails")
   public void whomiamEmployer() throws Exception {
        this.mvc.perform(get("/user/whomiam"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("employer@gmail.com")))
                .andExpect(jsonPath("$.userType", is("EMPLOYER")))
                .andExpect(jsonPath("$.authorities", hasSize(2)))
                .andExpect(jsonPath("$.authorities.[0].authority", is("ROLE_AUTHENTICATED")))
                .andExpect(jsonPath("$.authorities.[1].authority", is("ROLE_EMPLOYER")))
                .andReturn();
    }

    @Test
    @Transactional
    @Sql(
            scripts = "/user.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "worker@gmail.com", userDetailsServiceBeanName="testUserDetails")
    public void whomiamWORKER() throws Exception {
        this.mvc.perform(get("/user/whomiam"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("worker@gmail.com")))
                .andExpect(jsonPath("$.userType", is("WORKER")))
                .andExpect(jsonPath("$.authorities", hasSize(2)))
                .andExpect(jsonPath("$.authorities.[0].authority", is("ROLE_AUTHENTICATED")))
                .andExpect(jsonPath("$.authorities.[1].authority", is("ROLE_WORKER")))
                .andReturn();
    }
}
*/
