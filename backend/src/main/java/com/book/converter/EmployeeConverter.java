package com.book.converter;

import com.book.domain.PersistentEmployeeEntity;
import com.book.model.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeConverter extends Convertable<PersistentEmployeeEntity, EmployeeDTO>{

    private final PositionConverter positionConverter;
    private final DepartmentConverter departmentConverter;
    private final UserConverter userConverter;

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
        entity.setPosition(positionConverter.convertToEntityList(dto.getPositionDTO()));
        entity.setDepartment(departmentConverter.convertToEntityList(dto.getDepartmentDTO()));
        entity.setUser(userConverter.convertToEntity(dto.getUserDTO()));
        return entity;
    }
}
