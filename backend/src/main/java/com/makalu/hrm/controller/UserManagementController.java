package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.model.UserDTO;
import com.makalu.hrm.service.UserManagementService;
import com.makalu.hrm.utils.FieldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final FieldService fieldService;

    @GetMapping("/")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String index() {
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String list(ModelMap map) {
        return "userManagement/list";
    }

    @GetMapping("/api/list")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> list() {
        return ResponseEntity.ok(RestResponseDto.INSTANCE().success().detail(userManagementService.list()).column(fieldService.getUserManagementFields()));
    }

    @GetMapping("/view/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String viewUser(@PathVariable("id") UUID userId, ModelMap map) {
        RestResponseDto rdto = userManagementService.getResponseById(userId);
        map.put(ParameterConstant.User, rdto.getDetail());
        return "userManagement/view";
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> changePassword(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userManagementService.changePassword(userDTO));
    }

    @GetMapping("/getUserType")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> getUserTypeList(){
        return ResponseEntity.ok(RestResponseDto.INSTANCE().success().detail(UserType.getAllUserType()));
    }

    @PostMapping("/changeUserType")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> changeUserType(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userManagementService.changeUserType(userDTO));
    }

    @GetMapping("disableUser/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> disableUser(@PathVariable("id") UUID userId){
        return ResponseEntity.ok(userManagementService.disableUser(userId));
    }
}
