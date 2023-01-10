package com.book.validation;

import com.book.domain.PersistentPositionEntity;
import com.book.model.PositionDTO;
import com.book.repository.PositionRepository;
import com.book.validation.error.PositionError;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PositionValidation {
    private final PositionRepository positionRepository;
    private final PositionError positionError = new PositionError();


    public PositionError validateOnSave(PositionDTO positionDTO){
        boolean isValid = validateTitle(positionDTO.getTitle());
        isValid = isValid & validateDetails(positionDTO.getDetail());
        isValid = isValid && validateUnique(positionDTO.getTitle());

        positionError.setValid(isValid);
        return positionError;
    }

    public PositionError validateOnUpdate(PositionDTO positionDTO){
        boolean isValid  = validateTitle(positionDTO.getTitle());
        isValid = isValid & validateDetails(positionDTO.getDetail());

        positionError.setValid(isValid);
        return positionError;
    }


    @Transactional
    boolean validateUnique(String title){
        PersistentPositionEntity positionEntity = positionRepository.findByTitle(title);
        if(positionEntity == null){
            return true;
        }
        if(positionEntity.getTitle().equals(title)){
            positionError.setTitle("Unique title is required");
        }
        return false;
    }

    private boolean validateTitle(String title){
        if(title == null || title.trim().isEmpty()){
            positionError.setTitle("Title is required");
            return false;
        }
        if(title.length() > 51){
            positionError.setTitle("Title must be less than 50 characters");
            return false;
        }
        return true;
    }

    private boolean validateDetails(String detail){
        if(detail == null || detail.trim().isEmpty()){
            positionError.setDetail("Detail is required");
            return false;
        }
        if(detail.length() > 500){
            positionError.setDetail("Detail must be less than 500 characters");
            return false;
        }

        return true;
    }

}
