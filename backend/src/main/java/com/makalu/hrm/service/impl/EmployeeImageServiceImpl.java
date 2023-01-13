package com.makalu.hrm.service.impl;

import com.makalu.hrm.converter.EmployeeImageConverter;
import com.makalu.hrm.domain.PersistentEmployeeImageEntity;
import com.makalu.hrm.model.EmployeeImageDTO;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.repository.EmployeeImageRepository;
import com.makalu.hrm.service.EmployeeImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeImageServiceImpl implements EmployeeImageService {
    private final EmployeeImageRepository employeeImageRepository;

    private final EmployeeImageConverter employeeImageConverter;

    @Override
    public List<EmployeeImageDTO> list() {
        return employeeImageConverter.convertToDtoList(employeeImageRepository.findAll());
    }

    @Override
    @Transactional
    public RestResponseDto save(@NotNull MultipartFile file) {
        EmployeeImageDTO employeeImageDTO = new EmployeeImageDTO();
        try {
            String fileName = file.getOriginalFilename();
            String fileType = file.getContentType();
            byte[] data = file.getBytes();

            PersistentEmployeeImageEntity imageEntityEntity = new PersistentEmployeeImageEntity();
            imageEntityEntity.setName(fileName);
            imageEntityEntity.setType(fileType);
            imageEntityEntity.setImage(data);

            return RestResponseDto.INSTANCE()
                    .success().detail(employeeImageConverter.convertToDto(employeeImageRepository.saveAndFlush(imageEntityEntity)));

        }catch (Exception ex){
            return RestResponseDto
                    .INSTANCE()
                    .internalServerError()
                    .detail(employeeImageDTO);
        }
    }

    @Override
    public RestResponseDto getResponseById(UUID employeeImageId) {
        return null;
    }

    @Override
    public RestResponseDto update(EmployeeImageDTO employeeImageDTO) {
        return null;
    }

    @Override
    public RestResponseDto delete(UUID employeeImageId) {
        return null;
    }
}
