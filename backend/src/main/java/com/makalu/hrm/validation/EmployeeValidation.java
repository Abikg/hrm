package com.makalu.hrm.validation;

import com.makalu.hrm.domain.PersistentEmployeeEntity;
import com.makalu.hrm.model.DepartmentDTO;
import com.makalu.hrm.model.EmployeeDTO;
import com.makalu.hrm.model.PositionDTO;
import com.makalu.hrm.model.WorkExperienceDTO;
import com.makalu.hrm.repository.EmployeeRepository;
import com.makalu.hrm.validation.error.EmployeeError;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeValidation {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private static final int MAX_FILE_SIZE = 3145728;
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("png", "jpg", "jpeg");


    private final EmployeeRepository employeeRepository;
    private final EmployeeError error = new EmployeeError();

    public EmployeeError validateOnSave(EmployeeDTO dto) {
        boolean isValid = validateFullName(dto.getFullname());
        isValid = isValid & validateEmail(dto.getEmail());
        isValid = isValid & validatePhone(dto.getPhone());
        isValid = isValid & validateAddress(dto.getAddress());
        isValid = isValid & validateImage(dto.getEmpImage());
        isValid = isValid & validatePosition(dto.getPositionId());
        isValid = isValid & validateDepartment(dto.getDepartmentId());
        isValid = isValid & validateContactPhone(dto.getContactDetailDTO().getContactPhone());
        isValid = isValid & validateContactEmail(dto.getContactDetailDTO().getContactEmail());
        isValid = isValid & validateContactAddress(dto.getContactDetailDTO().getContactAddress());
        try {
            isValid = isValid & validateDob(dto.getPersonalDetailDTO().getDob());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        isValid = isValid & validateGender(dto.getPersonalDetailDTO().getGender());
        isValid = isValid & validateMaritalStatus(dto.getPersonalDetailDTO().getMaritalStatus());
//        if(dto.getWorkExperienceDTO() != null) {
//            isValid = isValid & validatePreviousCompany(dto.getWorkExperienceDTO().stream().map(WorkExperienceDTO::getPreviousCompany).collect(Collectors.toList()));
//            isValid = isValid & validateJoinDate(dto.getWorkExperienceDTO().stream().map(WorkExperienceDTO::getJoinDate).collect(Collectors.toList()));
//            isValid = isValid & validateLeftDate(dto.getWorkExperienceDTO().stream().map(WorkExperienceDTO::getLeftDate).collect(Collectors.toList()));
//            isValid = isValid & validateJobTitle(dto.getWorkExperienceDTO().stream().map(WorkExperienceDTO::getJobTitle).collect(Collectors.toList()));
//            isValid = isValid & validateJobDesc(dto.getWorkExperienceDTO().stream().map(WorkExperienceDTO::getJobDesc).collect(Collectors.toList()));
//
//        }
        isValid = isValid && validateUnique(dto.getPhone(),dto.getEmail());
        error.setValid(isValid);
        return error;
    }


    public EmployeeError validateOnUpdate(EmployeeDTO dto) {
        boolean isValid = validateFullName(dto.getFullname());
        isValid = isValid & validateEmail(dto.getEmail());
        isValid = isValid & validatePhone(dto.getPhone());
        isValid = isValid & validateAddress(dto.getAddress());
        isValid = isValid & validatePosition(dto.getPositionId());
        isValid = isValid & validateDepartment(dto.getDepartmentId());
        isValid = isValid & validateImage(dto.getEmpImage());
        isValid = isValid & validateContactPhone(dto.getContactDetailDTO().getContactPhone());
        isValid = isValid & validateContactEmail(dto.getContactDetailDTO().getContactEmail());
        isValid = isValid & validateContactAddress(dto.getContactDetailDTO().getContactAddress());
        try {
            isValid = isValid & validateDob(dto.getPersonalDetailDTO().getDob());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        isValid = isValid & validateGender(dto.getPersonalDetailDTO().getGender());
        isValid = isValid & validateMaritalStatus(dto.getPersonalDetailDTO().getMaritalStatus());
        isValid = isValid && validateUniqueOnUpdate(dto);
        error.setValid(isValid);
        return error;
    }

    @Transactional
    boolean validateUnique(String phone, String email) {
        PersistentEmployeeEntity employeeEntity = employeeRepository.findByPhoneOrEmail(phone,email);

        if (employeeEntity == null) {
            return true;
        }

        if (employeeEntity.getPhone().equals(phone)) {
            error.setPhone("Please provide a unique phone number");
        }

        if (employeeEntity.getEmail().equals(email)) {
            error.setEmail("Please provide a unique email address");
        }

        return false;
    }

    @Transactional
    boolean validateUniqueOnUpdate(EmployeeDTO employeeDTO){
        PersistentEmployeeEntity employeeEntity1 = employeeRepository.findByPhone(employeeDTO.getPhone());
        PersistentEmployeeEntity employeeEntity2 = employeeRepository.findByEmail(employeeDTO.getEmail());
        if(employeeEntity1 != null && !employeeEntity1.getId().equals(employeeDTO.getId())){
                error.setPhone("Phone number already used");
                return false;
        }
        if( employeeEntity2 != null && !employeeEntity2.getId().equals(employeeDTO.getId())){
                error.setEmail("Email already used");
                return false;
        }

        return true;
    }
    private boolean validateFullName(String name) {
        if (name == null || name.trim().isEmpty()) {
            error.setFullname("Name is required");
            return false;
        }

        if (name.length() > 50) {
            error.setFullname("Name must be less than 50 characters");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            error.setEmail("Email is required");
            return false;
        }else {
            Matcher matcher = EMAIL_PATTERN.matcher(email);
            if (!matcher.matches()) {
                error.setEmail("Incorrect email format");
                return false;
            }
        }
        return true;
    }

    private boolean validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            error.setPhone("Phone number is required");
            return false;
        }
        if(!phone.chars().allMatch(Character::isDigit)){
            error.setPhone("Phone number can only have numerical value");
            return false;
        }
        if (phone.length() != 10) {
            error.setPhone("Phone number must have 10 digit");
            return false;
        }
        return true;
    }

    private boolean validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            error.setAddress("Address is required");
            return false;
        }
        if (address.length() > 50) {
            error.setAddress("Address can't have more than 50 characters");
            return false;
        }
        return true;
    }

    private boolean validateImage(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String ext = FilenameUtils.getExtension(fileName);
            if (file.getSize() > MAX_FILE_SIZE) {
                error.setEmpImage("Image must be less than 3MB");
                return false;
            }

            if (!ALLOWED_EXTENSIONS.contains(ext)) {
                error.setEmpImage("Invalid file. Supports: jpg,jpeg,png only");
                return false;
            }
        }
        return true;
    }

    private boolean validatePosition(UUID positionId){
        if(positionId == null){
            error.setPosition("Position is required");
            return false;
        }
        return true;
    }

    private boolean validateDepartment(UUID departmentId){
        if(departmentId == null){
            error.setDepartment("Department is required");
            return false;
        }
        return true;
    }

    private boolean validateContactEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            error.setContactEmail("Email is required");
            return false;
        }else {
            Matcher matcher = EMAIL_PATTERN.matcher(email);
            if (!matcher.matches()) {
                error.setContactEmail("Incorrect email format");
                return false;
            }
        }
        return true;
    }

    private boolean validateContactPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            error.setContactPhone("Phone number is required");
            return false;
        }
        if(!phone.chars().allMatch(Character::isDigit)){
            error.setContactPhone("Phone number can only have numerical value");
            return false;
        }
        if (phone.length() != 10) {
            error.setContactPhone("Phone number must have 10 digit");
            return false;
        }
        return true;
    }

    private boolean validateContactAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            error.setContactAddress("Address is required");
            return false;
        }
        if (address.length() > 50) {
            error.setContactAddress("Address can't have more than 50 characters");
            return false;
        }
        return true;
    }
    private boolean validateDob(String dob) throws ParseException {
        if(dob == null || dob.trim().isEmpty()){
            error.setDob("Date of birth is required");
            return false;
        }
        else{
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date birthDate = dateFormat.parse(dob);
            Calendar cal = Calendar.getInstance();
            Date currentDate = cal.getTime();
            int compare = birthDate.compareTo(currentDate);

            if(compare > 0){
                error.setDob("Date of birth must be less than current date");
                return false;
            }
        }
        return true;
    }


    private boolean validateGender(String gender){
        if(gender == null || gender.trim().isEmpty()){
            error.setGender("Gender is required");
            return false;
        }
        return true;
    }
    private boolean validateMaritalStatus(String maritalStatus){
        if(maritalStatus == null || maritalStatus.trim().isEmpty()){
            error.setMaritalStatus("Marital status is required");
            return false;
        }
        return true;
    }

//    private boolean validatePreviousCompany(List<String> previousCompany){
//        if(previousCompany == null || previousCompany.stream().anyMatch(s->s.trim().isEmpty())){
//            error.setPreviousCompany("Previous company is required");
//            return false;
//        }
//        if(previousCompany.stream().anyMatch(s->s.length()>30)){
//            error.setPreviousCompany("Previous company is name must be less than 30 characters");
//            return false;
//        }
//        return true;
//    }
//
//    private boolean validateJobTitle(List<String> jobTitle){
//        if(jobTitle == null || jobTitle.stream().anyMatch(s->s.trim().isEmpty())){
//            error.setJobTitle("Job title is required");
//            return false;
//        }
//        if(jobTitle.stream().anyMatch(s->s.length() >20)){
//            error.setJobTitle("Job title must be less than 20 characters");
//            return false;
//        }
//        return true;
//    }
//
//
//    private boolean validateJobDesc(List<String> jobDesc){
//        if(jobDesc == null || jobDesc.stream().anyMatch(s->s.trim().isEmpty())){
//            error.setJobDesc("Job description is required");
//            return false;
//        }
//        if(jobDesc.stream().anyMatch(s->s.length()>100)){
//            error.setJobDesc("Job description must be less than 100 characters");
//            return false;
//        }
//        return true;
//    }
//
//    private boolean validateJoinDate(List<String> joinDate){
//        if(joinDate == null  || joinDate.stream().anyMatch(s->s.trim().isEmpty())){
//            error.setJoinDate("Joined date is required");
//            return false;
//        }
//        return true;
//    }
//
//
//    private boolean validateLeftDate(List<String> leftDate){
//        if(leftDate == null  || leftDate.stream().anyMatch(s->s.trim().isEmpty())){
//            error.setLeftDate("Left date is required");
//            return false;
//        }
//        return true;
//    }
}