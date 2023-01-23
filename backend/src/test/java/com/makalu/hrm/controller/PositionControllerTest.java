package com.makalu.hrm.controller;

import com.makalu.hrm.model.PositionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import java.util.UUID;
import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class PositionControllerTest extends MvcBaseTest {



    @Test
    @Transactional
    @Sql(
            scripts = "/position.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName = "testUserDetails")
    public void getPositions() throws Exception {
        String url = getURL("position/");
        this.mvc.perform(get(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/position/list"))
                .andDo(print());
    }

    @Test
    @Transactional
    @Sql(
            scripts = "/position.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName = "testUserDetails")
    public void List() throws Exception {
        String url = getURL("position/list");
        this.mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("position/list"))
                .andDo(print());
    }

    @Test
    @Transactional
    @Sql(
            scripts = "/position.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName = "testUserDetails")
    public void save() throws Exception {
        String url = getURL("position/save");
        PositionDTO positionDTO = rand.nextObject(PositionDTO.class);
        positionDTO.setTitle("Manager");
        this.objectMapper.findAndRegisterModules();
        this.mvc.perform(post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(positionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andDo(print())
                .andReturn();

        positionDTO.setTitle("Sr Accountant");
        positionDTO.setId(UUID.fromString("65473540-fd2e-43a7-b0c2-3e85157e114e"));
        this.objectMapper.findAndRegisterModules();
        this.mvc.perform(post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(positionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.detail.title", is("Sr Accountant")))
                .andDo(print())
                .andReturn();
    }

    @Test
    @Transactional
    @Sql(
            scripts = "/position.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName = "testUserDetails")
    public void update() throws Exception {
        String url = getURL("position/update");
        PositionDTO positionDTO = rand.nextObject(PositionDTO.class);
        positionDTO.setTitle("Sr Accountant");
        positionDTO.setId(UUID.fromString("67428b0e-8f2b-4291-b390-9bc4d9bc5ecd"));
        this.objectMapper.findAndRegisterModules();
        this.mvc.perform(post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(positionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.detail.id", is("67428b0e-8f2b-4291-b390-9bc4d9bc5ecd")))
                .andExpect(jsonPath("$.detail.title", is("Sr Accountant")))
                .andDo(print())
                .andReturn();
    }

    @Test
    @Transactional
    @Sql(
            scripts = "/position.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName = "testUserDetails")
    public void deletePosition() throws Exception {
        String url = getURL("position/delete/67428b0e-8f2b-4291-b390-9bc4d9bc5ecd");
        this.objectMapper.findAndRegisterModules();
        this.mvc.perform(delete(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andDo(print())
                .andReturn();
        url = getURL("position/delete/67428b0e-8f2b-4291-b390-9bc4d9bc5ec");
        this.mvc.perform(delete(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andDo(print())
                .andReturn();
    }

    @Test
    @Transactional
    @Sql(
            scripts = "/position.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName = "testUserDetails")
    public void getById() throws Exception {
        String url = getURL("position/get/67428b0e-8f2b-4291-b390-9bc4d9bc5ecd");
        this.objectMapper.findAndRegisterModules();
        this.mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.detail.id", is("67428b0e-8f2b-4291-b390-9bc4d9bc5ecd")))
                .andExpect(jsonPath("$.detail.title", is("Accountant")))
                .andDo(print())
                .andReturn();
        url = getURL("position/get/67428b0e-8f2b-4291-b390-9bc4d9bc5ec");
        this.mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.detail", is(nullValue())))
                .andDo(print())
                .andReturn();
    }

    @Test
    @Transactional
    @Sql(
            scripts = "/position.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName = "testUserDetails")
    public void getApiList() throws Exception {
        String url = getURL("position/api/list");
        this.objectMapper.findAndRegisterModules();
        this.mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.detail", hasSize(3)))
                .andDo(print())
                .andReturn();
    }

}
