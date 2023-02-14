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
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class EmployeeConverter extends Convertable<PersistentEmployeeEntity, EmployeeDTO> {

    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeImageRepository employeeImageRepository;
    private final UserRepository userRepository;
    private final EmployeeImageConverter employeeImageConverter;
    private final EmployeeRepository employeeRepository;

    @Override
    public PersistentEmployeeEntity convertToEntity(EmployeeDTO dto) {
        return this.copyConvertToEntity(dto, new PersistentEmployeeEntity());
    }

    @Override
    public EmployeeDTO convertToDto(PersistentEmployeeEntity entity) {
        if (entity == null) {
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setEntityEmployeeId("Test");
        dto.setEmployeeStatus(entity.getEmployeeStatus());
        dto.setFullname(entity.getFullname());
        dto.setEmail(entity.getEmail());
        dto.setJoinDate(entity.getJoinDate());
        dto.setPositionId(entity.getPosition().getId());
        dto.setDepartmentId(entity.getDepartment().getId());
        dto.setPositionName(entity.getPosition().getTitle());
        dto.setDepartmentName(entity.getDepartment().getTitle());
        dto.setEmployeeImage(employeeImageConverter.convertToDto(entity.getImage()));
        dto.setContactDetailDTO(entity.getContactDetail());
        dto.setPersonalDetailDTO(entity.getPersonalDetail());
        dto.setWorkExperienceDTO(entity.getWorkExperience());
        if (entity.getResignationReason() != null) {
            dto.setResignationReason(entity.getResignationReason());
        }
        if (entity.getResignationDate() != null) {
            dto.setResignationDate(entity.getResignationDate());
        }
        if (entity.getExitDate() != null) {
            dto.setExitDate(entity.getExitDate());
        }
        if (entity.getApprovedBy() != null) {
            dto.setApprovedById(entity.getApprovedBy().getId());
        }
        if(entity.getUser() != null){
            boolean isManager= (entity.getUser().getUserType().toString().equals("Manager"))? true: false;
            dto.setManager(isManager);
            dto.setUserId(entity.getUser().getId());
        }
        return dto;
    }

    @Override
    public PersistentEmployeeEntity copyConvertToEntity(EmployeeDTO dto, PersistentEmployeeEntity entity) {
        if (dto == null || entity == null) {
            return entity;
        }

        entity.setEmployeeId(dto.getEntityEmployeeId());
        entity.setEmployeeStatus(dto.getEmployeeStatus());
        entity.setFullname(dto.getFullname());
        entity.setEmail(dto.getEmail());
        entity.setJoinDate(dto.getJoinDate());
        entity.setPosition(positionRepository.findById(dto.getPositionId()).orElse(null));
        entity.setDepartment(departmentRepository.findById(dto.getDepartmentId()).orElse(null));
        if (dto.getEmployeeImageId() != null) {
            entity.setImage(employeeImageRepository.findById(dto.getEmployeeImageId()).orElse(null));
        }
        if (dto.getUserId() != null) {
            entity.setUser(userRepository.findById(dto.getUserId()).orElse(null));
        }
        entity.setContactDetail(dto.getContactDetailDTO());
        entity.setPersonalDetail(dto.getPersonalDetailDTO());
        entity.setWorkExperience(dto.getWorkExperienceDTO());
        if (dto.getResignationReason() != null) {
            entity.setResignationReason(dto.getResignationReason());
        }
        if (dto.getResignationDate() != null) {
            entity.setResignationDate(dto.getResignationDate());
        }
        if (dto.getExitDate() != null) {
            entity.setExitDate(dto.getExitDate());
        }
        if (dto.getApprovedById() != null) {
            entity.setApprovedBy(userRepository.findById(dto.getApprovedById()).orElse(null));
        }
        return entity;
    }
}
