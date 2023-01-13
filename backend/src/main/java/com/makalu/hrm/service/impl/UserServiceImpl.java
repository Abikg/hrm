package com.makalu.hrm.service.impl;

import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.UserService;
import com.makalu.hrm.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public PersistentUserEntity getCurrentUserEntity() {
        return userRepository.findById(AuthenticationUtils.getCurrentUser().getUserId()).orElse(null);
    }
}
