package com.makalu.hrm.converter;

import com.makalu.hrm.domain.PersistentDepartmentEntity;
import com.makalu.hrm.domain.PersistentEmployeeEntity;
import com.makalu.hrm.domain.PersistentPositionEntity;
import com.makalu.hrm.enumconstant.UserType;
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
        dto.setResignationReason(entity.getResignationReason());
        dto.setResignationDate(entity.getResignationDate());
        dto.setExitDate(entity.getExitDate());
        if (entity.getApprovedBy() != null) {
            dto.setApprovedById(entity.getApprovedBy().getId());
        }
        if(entity.getUser()!= null && entity.getUser().getUserType().equals(UserType.MANAGER)){
           dto.setSubordinates(employeeRepository.findAllByManager(entity.getId()).stream().map(PersistentEmployeeEntity :: getFullname).collect(Collectors.toList()));
        }
        if(entity.getUser() != null){
            dto.setCreateUser(true);
            dto.setUserId(entity.getUser().getId());
        }
        if(entity.getManager() != null){
            dto.setReportingManagerName(entity.getManager().getFullname());
            dto.setManagerId(entity.getManager().getId());
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

        entity.setEmployeeId(dto.getEmployeeId());
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
        entity.setResignationReason(dto.getResignationReason());
        entity.setResignationDate(dto.getResignationDate());
        entity.setExitDate(dto.getExitDate());
        if (dto.getApprovedById() != null) {
            entity.setApprovedBy(userRepository.findById(dto.getApprovedById()).orElse(null));
        }
        if(dto.getManagerId() != null) {
            entity.setManager(employeeRepository.findById(dto.getManagerId()).orElse(null));
        }
        return entity;
    }
}
