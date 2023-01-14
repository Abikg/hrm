package com.makalu.hrm.converter;

import com.makalu.hrm.domain.PersistentDepartmentEntity;
import com.makalu.hrm.domain.PersistentEmployeeEntity;
import com.makalu.hrm.domain.PersistentPositionEntity;
import com.makalu.hrm.model.EmployeeDTO;
import com.makalu.hrm.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Entity;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeConverter extends Convertable<PersistentEmployeeEntity, EmployeeDTO>{

    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeImageRepository employeeImageRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final EmployeeImageConverter employeeImageConverter;
    @Override
    public PersistentEmployeeEntity convertToEntity(EmployeeDTO dto) {
        return this.copyConvertToEntity(dto,new PersistentEmployeeEntity());
    }

    @Override
    public EmployeeDTO convertToDto(PersistentEmployeeEntity entity) {
        if(entity == null){
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(entity.getId());
        dto.setFullname(entity.getFullname());
        dto.setAddress(entity.getAddress());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setPositionIdList(entity.getPosition().stream().map(PersistentPositionEntity::getId).collect(Collectors.toList()));
        dto.setDepartmentIdList(entity.getDepartment().stream().map(PersistentDepartmentEntity::getId).collect(Collectors.toList()));
        dto.setEmployeeImage(employeeImageConverter.convertToDto(entity.getImage()));
        dto.setUser(userConverter.convertToDto(entity.getUser()));
        return dto;
    }

    @Override
    public PersistentEmployeeEntity copyConvertToEntity(EmployeeDTO dto, PersistentEmployeeEntity entity) {
        if(dto == null || entity == null){
            return entity;
        }
        entity.setFullname(dto.getFullname());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPosition(positionRepository.findAllById(dto.getPositionIdList()));
        entity.setDepartment(departmentRepository.findAllById(dto.getDepartmentIdList()));
        if(dto.getEmployeeImageId() != null) {
            entity.setImage(employeeImageRepository.getReferenceById(dto.getEmployeeImageId()));
        }
        entity.setUser(userRepository.getReferenceById(dto.getUserId()));
        return entity;
    }
}
