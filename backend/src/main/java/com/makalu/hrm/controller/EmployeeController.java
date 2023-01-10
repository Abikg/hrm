package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.model.EmployeeDTO;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/employee")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String index(){
        return "redirect:/employee/list";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String list(ModelMap map){
        try {
            map.put(ParameterConstant.EMPLOYEE_LIST, employeeService.list());
        }catch (Exception ex){
            log.error("Error occurred in get employee", ex);
        }
        return "employee/list";
    }

    @GetMapping("/api/list")
    @PreAuthorize("hasRole('AUTHENTICATED')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> list(){
        return ResponseEntity.ok(RestResponseDto.INSTANCE().success().detail(employeeService.list()));
    }

    @GetMapping("/getSelectList")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<RestResponseDto> getSelectList(){
        return ResponseEntity.ok(employeeService.getPositionAndDepartmentList());
    }

    @RequestMapping(path = "/save",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> save(@RequestPart("file") MultipartFile file,@RequestPart("data") EmployeeDTO employeeDTO){
        return ResponseEntity.ok(employeeService.save(employeeDTO));
    }
}
