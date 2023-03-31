package com.makalu.hrm.controller;
import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.exceptions.EmployeeException;
import com.makalu.hrm.model.EmployeeDTO;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.DepartmentService;
import com.makalu.hrm.service.EmployeeService;
import com.makalu.hrm.service.ManagerService;
import com.makalu.hrm.service.PositionService;
import com.makalu.hrm.utils.FieldService;
import com.makalu.hrm.utils.ImageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Controller
@RequestMapping("/employee")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final PositionService positionService;
    private final FieldService fieldService;
    private final ManagerService managerService;

    @GetMapping("/")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String index() {
        return "redirect:/employee/list";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String list(ModelMap map) {
        return "employee/list";
    }

    @GetMapping("/api/list")
    @PreAuthorize("hasRole('AUTHENTICATED')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> list() {
        return ResponseEntity.ok(RestResponseDto.INSTANCE().success().detail(employeeService.list()).column(fieldService.getEmployeeFields()));
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String createEmployee(ModelMap map) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        map.put(ParameterConstant.DEPARTMENT_LIST, departmentService.list());
        map.put(ParameterConstant.POSITION_LIST, positionService.list());
        map.put(ParameterConstant.EMPLOYEE, employeeDTO);
        return "employee/create";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String save(EmployeeDTO employeeDTO, ModelMap map) {
        map.put(ParameterConstant.DEPARTMENT_LIST, departmentService.list());
        map.put(ParameterConstant.POSITION_LIST, positionService.list());
        map.put(ParameterConstant.EMPLOYEE, employeeDTO);
        map.put(ParameterConstant.RESPONSE, employeeService.save(employeeDTO));
        return "employee/create";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String editEmployee(@PathVariable("id") UUID employeeId, ModelMap map) {
        try {
            RestResponseDto rdto = employeeService.getResponseByIdForUpdate(employeeId);
            map.put(ParameterConstant.POSITION_LIST, positionService.list());
            map.put(ParameterConstant.DEPARTMENT_LIST, departmentService.list());
            map.put("imageUtil", new ImageUtil());
            map.put(ParameterConstant.EMPLOYEE, rdto.getDetail());

        }catch (EmployeeException e){
            return "redirect:/employee/list";
        }
        return "employee/edit";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String update(EmployeeDTO employeeDTO, ModelMap map) {
        map.put(ParameterConstant.DEPARTMENT_LIST, departmentService.list());
        map.put(ParameterConstant.POSITION_LIST, positionService.list());
        map.put(ParameterConstant.EMPLOYEE, employeeDTO);
        map.put(ParameterConstant.RESPONSE, employeeService.update(employeeDTO));
        return "employee/edit";
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> getById(@PathVariable("id") UUID employeeId) {
        return ResponseEntity.ok(employeeService.getResponseById(employeeId));
    }

    @GetMapping("/view/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String viewEmployee(@PathVariable("id") UUID employeeId, ModelMap map) {
        RestResponseDto rdto = employeeService.getResponseById(employeeId);
        map.put("imageUtil", new ImageUtil());
        map.put(ParameterConstant.EMPLOYEE, rdto.getDetail());
        return "employee/view";
    }

    @PostMapping(path = "/createResignation")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> employeeResignationCreate(EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.employeeResignationCreate(employeeDTO));
    }

    @PostMapping(path = "/updateResignation")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> employeeResignationUpdate(EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.employeeResignationUpdate(employeeDTO));
    }

    @PostMapping(path = "/approveResignation/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> approveResignation(@PathVariable("id") UUID employeId) {
        return ResponseEntity.ok(employeeService.employeeApproveResignation(employeId));
    }

    @PostMapping(path = "/exitResignation/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> exitResignation(@PathVariable("id") UUID employeId) {
        return ResponseEntity.ok(employeeService.employeeExitResignation(employeId));
    }

    @GetMapping(path = "/getActiveEmployees")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> getAllEmployee(){
        return ResponseEntity.ok(managerService.getEmployeeList());
    }
}
