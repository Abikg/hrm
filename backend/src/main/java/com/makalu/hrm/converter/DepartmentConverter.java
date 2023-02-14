package com.makalu.hrm.converter;

import com.makalu.hrm.domain.PersistentDepartmentEntity;
import com.makalu.hrm.model.DepartmentDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DepartmentConverter extends Convertable<PersistentDepartmentEntity, DepartmentDTO> {

    @Override
    public PersistentDepartmentEntity convertToEntity(DepartmentDTO dto) {
        return this.copyConvertToEntity(dto, new PersistentDepartmentEntity());
    }

    @Override
    public PersistentDepartmentEntity copyConvertToEntity(DepartmentDTO dto, PersistentDepartmentEntity entity) {

        if (entity == null || dto == null) {
            return null;
        }

        entity.setDetail(trimString(dto.getDetail()));
        entity.setTitle(trimString(dto.getTitle()));
        entity.setDepartmentCode(trimString(dto.getDepartmentCode()));

        return entity;
    }

    @Override
    public DepartmentDTO convertToDto(PersistentDepartmentEntity entity) {
        if (entity == null) {
            return null;
        }

        DepartmentDTO dto = new DepartmentDTO();

        dto.setId(entity.getId());
        dto.setDepartmentCode(entity.getDepartmentCode());
        dto.setTitle(entity.getTitle());
        dto.setDetail(entity.getDetail());

        return dto;
    }

}
