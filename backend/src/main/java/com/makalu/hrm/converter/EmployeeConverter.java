package com.makalu.hrm.converter;

import com.makalu.hrm.domain.PersistentEmployeeEntity;
import com.makalu.hrm.model.EmployeeDTO;
import com.makalu.hrm.repository.DepartmentRepository;
import com.makalu.hrm.repository.EmployeeImageRepository;
import com.makalu.hrm.repository.PositionRepository;
import com.makalu.hrm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeConverter extends Convertable<PersistentEmployeeEntity, EmployeeDTO>{

    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeImageRepository employeeImageRepository;
    private final PositionConverter positionConverter;
    private final DepartmentConverter departmentConverter;
    private final UserConverter userConverter;
    private final EmployeeImageConverter employeeImageConverter;
    private final UserRepository userRepository;
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
        dto.setEmpImageName(entity.getEmpImage());
        dto.setPositionDTO(positionConverter.convertToDtoList(entity.getPosition()));
        dto.setDepartmentDTO(departmentConverter.convertToDtoList(entity.getDepartment()));
        dto.setUserDTO(userConverter.convertToDto(entity.getUser()));
        dto.setEmployeeImageDTO(employeeImageConverter.convertToDto(entity.getImage()));
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
        entity.setEmpImage(dto.getEmpImageName());
        entity.setPosition(positionRepository.findAllById(dto.getDepartmentIdList()));
        entity.setDepartment(departmentRepository.findAllById(dto.getDepartmentIdList()));
        entity.setImage(employeeImageRepository.getById(dto.getEmployeeImageId()));
        entity.setUser(userRepository.getReferenceById(dto.getUserId().toString()));
        return entity;
    }
}
