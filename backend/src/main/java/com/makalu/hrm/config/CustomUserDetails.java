package com.makalu.hrm.config;

import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.enumconstant.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class CustomUserDetails extends User {
    private UserType userType = null;
    private UUID userId = null;

    public UserType getUserType() {
        return userType;
    }

    public UUID getUserId() {
        return userId;
    }

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUserDetails(String username, String password, UserType userType, UUID userId, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.userType = userType;
    }

    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public CustomUserDetails(String username, String password, UserType userType, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userType = userType;
    }

    public CustomUserDetails(PersistentUserEntity user, Collection<? extends GrantedAuthority> authorities) {
        this(user.getUsername(), user.getPassword(), user.getUserType(), user.isEnabled(), !user.isAccountExpired(), !user.isPasswordExpired(), !user.isAccountLocked(), authorities);
        this.userId = user.getId();
    }
}
