package com.makalu.hrm;

import com.makalu.hrm.config.CustomUserDetails;
import com.makalu.hrm.enumconstant.UserType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component(value = "testUserDetails")
@Primary
@Profile("test")
public class TestUserDetailsImpl implements UserDetailsService {
    public static final String API_SYSTEM = "system@gmail.com";
    public static final String API_EMPLOYER = "employer@gmail.com";
    public static final String API_WORKER = "worker@gmail.com";


    private CustomUserDetails getUser(String username) {
        List<SimpleGrantedAuthority> roleList  = new ArrayList<>();
        roleList.add(new SimpleGrantedAuthority("ROLE_AUTHENTICATED"));
        UserType userType = UserType.SUPER_ADMIN;
        UUID userId = UUID.fromString("1badc1b8-0f17-11ed-9480-394a830e32as");
        if (Objects.equals(username, API_SYSTEM))
            roleList.add(new SimpleGrantedAuthority("ROLE_SYSTEM"));
        if (Objects.equals(username, API_WORKER)) {
            roleList.add(new SimpleGrantedAuthority("ROLE_WORKER"));
            userId = UUID.fromString("1badc1b8-0f17-11ed-9480-394a830e32aw");
            userType = UserType.ADMIN;
        }
        if (Objects.equals(username, API_EMPLOYER)) {
            roleList.add(new SimpleGrantedAuthority("ROLE_EMPLOYER"));
            userId =UUID.fromString("1badc1b8-0f17-11ed-9480-394a830e32ae");
            userType = UserType.EMPLOYEE;
        }

        return new CustomUserDetails(username, "{noop}test", userType, userId, roleList);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        if (Objects.equals(username, API_SYSTEM))
            return getUser(API_SYSTEM);
        if (Objects.equals(username, API_WORKER))
            return getUser(API_WORKER);
        if (Objects.equals(username, API_EMPLOYER))
            return getUser(API_EMPLOYER);
        throw new UsernameNotFoundException(username);
    }
}
