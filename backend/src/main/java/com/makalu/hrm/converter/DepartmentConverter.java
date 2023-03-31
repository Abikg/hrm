package com.makalu.hrm.converter;

import com.makalu.hrm.domain.PersistentDepartmentEntity;
import com.makalu.hrm.model.DepartmentDTO;
import com.makalu.hrm.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DepartmentConverter extends Convertable<PersistentDepartmentEntity, DepartmentDTO> {

    private final EmployeeRepository employeeRepository;

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
        if(dto.getManagerId() != null) {
            entity.setManager(employeeRepository.findById(dto.getManagerId()).orElse(null));
        }

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
        if(entity.getManager() != null){
            dto.setManagerName(entity.getManager().getFullname());
            dto.setManagerId(entity.getManager().getId());
        }
        return dto;
    }

}
