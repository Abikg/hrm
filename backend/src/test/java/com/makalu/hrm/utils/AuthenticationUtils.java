package com.makalu.hrm.utils;

import com.makalu.hrm.config.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Profile("test")
public class AuthenticationUtils {

    public static CustomUserDetails getCurrentUser(){
        if (SecurityContextHolder.getContext().getAuthentication() != null){
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof CustomUserDetails)
                return (CustomUserDetails) principal;
        }

        return null;
    }

    public static List<String> getCurrentUserAuthorities() {
        if (SecurityContextHolder.getContext().getAuthentication() == null){
            return null;
        }

        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().parallelStream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
       }
}
