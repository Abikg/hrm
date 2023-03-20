package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.model.DepartmentDTO;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.DepartmentService;
import com.makalu.hrm.utils.FieldService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    private final FieldService fieldService;

    @GetMapping("/")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String index() {
        return "redirect:/department/list";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String list(ModelMap map) {
        try {
            map.put(ParameterConstant.DEPARTMENT_LIST, departmentService.list());
        } catch (Exception ex) {
            log.error("Error occurred in get department", ex);
        }
        return "department/list";
    }

    @GetMapping("/api/list")
    @PreAuthorize("hasRole('AUTHENTICATED')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> list() {
        return ResponseEntity.ok(RestResponseDto.INSTANCE().success().detail(departmentService.list()).column(fieldService.getDepartmentFields()));
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> save(@RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok().body(departmentService.save(departmentDTO));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> getById(@PathVariable("id") UUID departmentId) {
        return ResponseEntity.ok(departmentService.getResponseById(departmentId));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> update(@RequestBody DepartmentDTO departmentDTO, RedirectAttributes redirectAttributes) {
        return ResponseEntity.ok(departmentService.update(departmentDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> delete(@PathVariable("id") UUID departmentId) {
        return ResponseEntity.ok(departmentService.delete(departmentId));
    }

    @GetMapping("/getDepartmentEmployee")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> getEmployeeListByDepartment(@PathVariable("id") UUID departmentId){
        return ResponseEntity.ok(departmentService.getEmployeeListByDepartment(departmentId));
    }
}
