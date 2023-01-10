package com.makalu.hrm.converter;

import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends Convertable<PersistentUserEntity, UserDTO>{
    @Override
    public PersistentUserEntity convertToEntity(UserDTO dto) {
        return this.copyConvertToEntity(dto,new PersistentUserEntity());
    }

    @Override
    public UserDTO convertToDto(PersistentUserEntity entity) {
        if(entity == null){
            return null;
        }

        UserDTO dto = new UserDTO();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setEnabled(entity.isEnabled());
        dto.setPasswordExpired(entity.isPasswordExpired());
        dto.setAccountExpired(entity.isAccountExpired());
        dto.setAccountLocked(entity.isAccountLocked());
        dto.setUserType(entity.getUserType());
        return dto;
    }

    @Override
    public PersistentUserEntity copyConvertToEntity(UserDTO dto, PersistentUserEntity entity) {
       if(dto == null || entity == null){
           return null;
       }

       entity.setUsername(dto.getUsername());
       entity.setPassword(dto.getPassword());
       entity.setEnabled(dto.isEnabled());
       entity.setPasswordExpired(dto.isPasswordExpired());
       entity.setAccountExpired(dto.isAccountExpired());
       entity.setAccountLocked(dto.isAccountLocked());
       entity.setUserType(dto.getUserType());
       return entity;
    }
}
