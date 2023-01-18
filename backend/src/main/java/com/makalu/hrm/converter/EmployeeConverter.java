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
        dto.setPositionId(entity.getPosition().getId());
        dto.setDepartmentId(entity.getDepartment().getId());
        dto.setEmployeeImage(employeeImageConverter.convertToDto(entity.getImage()));
        dto.setUser(userConverter.convertToDto(entity.getUser()));
        if(entity.getContactDetail() != null){
            dto.setContactDetailDTO(entity.getContactDetail());
        }
        if(entity.getPersonalDetail() != null){
            dto.setPersonalDetailDTO(entity.getPersonalDetail());
        }
        if(entity.getWorkExperience() != null){
            dto.setWorkExperienceDTO(entity.getWorkExperience());
        }
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
        entity.setPosition(positionRepository.findById(dto.getPositionId()).orElse(null));
        entity.setDepartment(departmentRepository.findById(dto.getDepartmentId()).orElse(null));
        if(dto.getEmployeeImageId() != null) {
            entity.setImage(employeeImageRepository.getReferenceById(dto.getEmployeeImageId()));
        }
        entity.setUser(userRepository.getReferenceById(dto.getUserId()));
        entity.setContactDetail(dto.getContactDetailDTO());
        entity.setPersonalDetail(dto.getPersonalDetailDTO());
        entity.setWorkExperience(dto.getWorkExperienceDTO());
        return entity;
    }
}
