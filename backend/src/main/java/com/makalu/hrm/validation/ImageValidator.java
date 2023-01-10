//package com.book.validation;
//
//import org.apache.commons.io.FilenameUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class ImageValidator implements Validator {
//
//    private static final int MAX_FILE_SIZE = 3145728;
//    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("png","jpg","jpeg");
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return MultipartFile.class.isAssignableFrom(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        MultipartFile file = (MultipartFile) target;
//        String fileName = file.getOriginalFilename();
//        String ext = FilenameUtils.getExtension(fileName);
//        if(file.getSize() > MAX_FILE_SIZE){
//            errors.rejectValue("file","file.tooLarge","Image must be less than 3MB");
//        }
//
//        if(!ALLOWED_EXTENSIONS.contains(ext)){
//            errors.rejectValue("file","file.invalid","Invalid file. Supports: jpg,jpeg,png only");
//        }
//
//
//    }
//}
