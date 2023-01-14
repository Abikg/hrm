package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.model.EmployeeDTO;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.DepartmentService;
import com.makalu.hrm.service.EmployeeService;
import com.makalu.hrm.service.PositionService;
import com.makalu.hrm.utils.ImageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
@RequestMapping("/employee")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final PositionService positionService;

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

    @GetMapping("/create")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String createEmployee(ModelMap map){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        map.put(ParameterConstant.DEPARTMENT_LIST, departmentService.list());
        map.put(ParameterConstant.POSITION_LIST, positionService.list());
        map.put(ParameterConstant.EMPLOYEE,employeeDTO);
        return "employee/create";
    }
    @RequestMapping(path = "/save",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String save(EmployeeDTO employeeDTO,ModelMap map){
        map.put(ParameterConstant.DEPARTMENT_LIST,departmentService.list());
        map.put(ParameterConstant.POSITION_LIST,positionService.list());
        map.put(ParameterConstant.EMPLOYEE,employeeDTO);
        map.put(ParameterConstant.RESPONSE,employeeService.save(employeeDTO));
        return "employee/create";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String editEmployee(@PathVariable("id") UUID employeeId ,ModelMap map){
       RestResponseDto rdto = employeeService.getResponseById(employeeId);
       map.put(ParameterConstant.POSITION_LIST,positionService.list());
       map.put(ParameterConstant.DEPARTMENT_LIST,departmentService.list());
       map.put("imageUtil", new ImageUtil());
       map.put(ParameterConstant.EMPLOYEE,rdto.getDetail());
        return "employee/edit";
    }

    @RequestMapping(path = "/update",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String update(EmployeeDTO employeeDTO,ModelMap map){
        map.put(ParameterConstant.DEPARTMENT_LIST,departmentService.list());
        map.put(ParameterConstant.POSITION_LIST,positionService.list());
        map.put(ParameterConstant.EMPLOYEE,employeeDTO);
        map.put(ParameterConstant.RESPONSE,employeeService.update(employeeDTO));
        return "employee/edit";
    }
}
