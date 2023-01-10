package com.makalu.hrm.controller;

import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.utils.AuthenticationUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublicController {

    @RequestMapping(value = "/")
    @PreAuthorize("permitAll()")
    public String index() {
        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())){
            return "adminDashboard";
        }else if (AuthenticationUtils.hasRole(UserType.ADMIN.name().toUpperCase())){
            return "adminDashboard";

        }
        return "auth/login";
    }

    @RequestMapping(value = "/auth/login")
    public String login(@RequestParam(value = "error", required = false)String error, ModelMap map) {
        if (AuthenticationUtils.getCurrentUser() != null){
            return "redirect:/";
        }
        map.put("error", error);
        return "auth/login";
    }
}
