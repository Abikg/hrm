package com.makalu.hrm.service.impl;

import com.makalu.hrm.controller.MvcBaseTest;

import com.makalu.hrm.model.PositionDTO;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.PositionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PositionServiceTest extends MvcBaseTest {

    private final PositionService positionService;

    @BeforeEach
    public void setup(){
        //We can setup required things here.
//        PositionDTO positionDTO = rand.nextObject(PositionDTO.class);
//        positionDTO.setId(UUID.fromString("67428b0e-8f2b-4291-b390-9bc4d9bc5ecb"));
//        positionService.save(positionDTO);
//        positionDTO = rand.nextObject(PositionDTO.class);
//        positionDTO.setId(UUID.fromString("67428b0e-8f2b-4291-b390-9bc4d9bc5ecc"));
//        positionService.save(positionDTO);
//        positionDTO = rand.nextObject(PositionDTO.class);
//        positionDTO.setId(UUID.fromString("67428b0e-8f2b-4291-b390-9bc4d9bc5ecd"));
//        positionService.save(positionDTO);
        System.out.println("Testing stared = ");
    }

    @Autowired
    public PositionServiceTest(PositionService positionService) {
        this.positionService = positionService;
    }

    @Test
    @Transactional
    @Sql(
            scripts = "/position.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName="testUserDetails")
    public void ListPosition() throws Exception {
        List<PositionDTO> positions= positionService.list();
        assert positions.size() == 3;
        assert UUID.fromString("67428b0e-8f2b-4291-b390-9bc4d9bc5ecb").equals(positions.get(0).getId());
    }

    @Test
    @Transactional
    @Sql(
            scripts = "/position.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName="testUserDetails")
    public void save() throws Exception {
          RestResponseDto persistentPositionEntity = positionService.save(rand.nextObject(PositionDTO.class));
        List<PositionDTO> positions= positionService.list();
        assert positions.size() == 4;
    }

    @Test
    @Transactional
    @Sql(
            scripts = "/position.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName="testUserDetails")
    public void update() throws Exception {
        PositionDTO positionDTO= rand.nextObject(PositionDTO.class);
        positionDTO.setId(UUID.fromString("67428b0e-8f2b-4291-b390-9bc4d9bc5ecd"));
        RestResponseDto persistentPositionEntity = positionService.update(positionDTO);
        List<PositionDTO> positions= positionService.list();
        assert positions.size() == 3;
        assert persistentPositionEntity.getStatus() == 200;
        assert ((PositionDTO) persistentPositionEntity.getDetail()).getId() == positionDTO.getId();

        positionDTO.setId(UUID.fromString("67428b0e-8f2b-4291-b390-9bc4d9bc5ece"));
        persistentPositionEntity = positionService.update(positionDTO);
        Assertions.assertEquals(400, persistentPositionEntity.getStatus());

        positionDTO.setTitle("This is rock");
        persistentPositionEntity = positionService.update(positionDTO);
        Assertions.assertEquals(404, persistentPositionEntity.getStatus());

    }

    @Test
    @Transactional
    @Sql(
            scripts = "/position.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName="testUserDetails")
    public void getResponseById() throws Exception {
        RestResponseDto persistentPositionEntity = positionService.getResponseById(UUID.fromString("67428b0e-8f2b-4291-b390-9bc4d9bc5ec"));
        assert persistentPositionEntity.getStatus() == 404;
        persistentPositionEntity = positionService.getResponseById(UUID.fromString("67428b0e-8f2b-4291-b390-9bc4d9bc5ecb"));
        assert (persistentPositionEntity.getStatus() == 200);
    }

    @Test
    @Transactional
    @Sql(
            scripts = "/position.sql",
            config = @SqlConfig(separator = ";")
    )
    @WithUserDetails(value = "system@gmail.com", userDetailsServiceBeanName="testUserDetails")
    public void delete() throws Exception {
        RestResponseDto persistentPositionEntity = positionService.delete(UUID.fromString("67428b0e-8f2b-4291-b390-9bc4d9bc5ec"));
        assert persistentPositionEntity.getStatus() == 404;
        persistentPositionEntity = positionService.delete(UUID.fromString("67428b0e-8f2b-4291-b390-9bc4d9bc5ecb"));
        assert (persistentPositionEntity.getStatus() == 200);
        List<PositionDTO> positions= positionService.list();
        assert positions.size() == 2;
    }




}