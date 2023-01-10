package com.book.config;

import com.book.constant.StringConstant;
import com.book.domain.PersistentRoleEntity;
import com.book.domain.PersistentUserEntity;
import com.book.domain.PersistentUserProfileEntity;
import com.book.repository.UserProfileRepository;
import com.book.repository.UserRepository;
import com.book.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.trim().isEmpty()){
            throw new UsernameNotFoundException(username + " is null");
        }

        Optional<PersistentUserEntity> userEntityOptional = userRepository.findByUsername(username);

        if(userEntityOptional.isEmpty()){
            throw new UsernameNotFoundException(username + " not found");
        }

        PersistentUserEntity user = userEntityOptional.get();
        List<SimpleGrantedAuthority> ROLES = new ArrayList<>();
        ROLES.add(new SimpleGrantedAuthority(StringConstant.DEFAULT_ROLE));
        ROLES.add(new SimpleGrantedAuthority("ROLE_"+user.getUserType().name().toUpperCase()));

        return new CustomUserDetails(user, ROLES);
    }
}
