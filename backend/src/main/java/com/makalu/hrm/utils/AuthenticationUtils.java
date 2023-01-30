package com.makalu.hrm.utils;

import com.makalu.hrm.config.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationUtils {

    public static CustomUserDetails getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principle == null) {
            return null;
        }
        if (principle instanceof CustomUserDetails) {
            return (CustomUserDetails) principle;
        }

        return null;
    }

    public static String getUserName() {
        CustomUserDetails user = getCurrentUser();
        if (user != null) {
            return user.getUsername();
        }

        return "";
    }

    public static List<String> getCurrentUserAuthorities() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }

        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().parallelStream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public static boolean hasRole(String role) {
        List<String> roleList = getCurrentUserAuthorities();
        if (roleList == null || role == null) {
            return false;
        }

        role = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return roleList.contains(role);
    }
}
