package com.makalu.hrm.converter;

import com.makalu.hrm.domain.PersistentEmployeeImageEntity;
import com.makalu.hrm.model.EmployeeImageDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeImageConverter extends Convertable<PersistentEmployeeImageEntity, EmployeeImageDTO> {

    @Override
    public PersistentEmployeeImageEntity convertToEntity(EmployeeImageDTO dto) {
        return this.copyConvertToEntity(dto, new PersistentEmployeeImageEntity());
    }

    @Override
    public EmployeeImageDTO convertToDto(PersistentEmployeeImageEntity entity) {
        if (entity == null) {
            return null;
        }
        EmployeeImageDTO dto = new EmployeeImageDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setImage(entity.getImage());
        return dto;
    }

    @Override
    public PersistentEmployeeImageEntity copyConvertToEntity(EmployeeImageDTO dto, PersistentEmployeeImageEntity entity) {
        if (dto == null || entity == null) {
            return null;
        }

        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setImage(dto.getImage());
        return entity;
    }
}
