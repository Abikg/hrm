/*

package com.book.service.impl;

import com.book.config.CustomUserDetails;
import com.book.converter.UserProfileConverter;
import com.book.domain.PersistentSystemTokenEntity;
import com.book.domain.PersistentUserEntity;
import com.book.domain.PersistentUserProfileEntity;
import com.book.enumconstant.TokenType;
import com.book.exceptions.DataNotFoundException;
import com.book.model.*;
import com.book.repository.UserProfileRepository;
import com.book.repository.UserRepository;
import com.book.service.SystemTokenService;
import com.book.service.UserService;
import com.book.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SystemTokenService systemTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileConverter userProfileConverter;

    private PersistentUserEntity register(CreateUserRequest createUserRequest, @NotNull UserType userType){
        try{
            PersistentUserEntity userEntity = new PersistentUserEntity();

            userEntity.setUsername(createUserRequest.getUsername().trim());
            userEntity.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
            userEntity.setAccountLocked(true);
            userEntity.setUserType(userType);

            return userRepository.saveAndFlush(userEntity);
        }catch (Exception ex){
            log.error("Error on register", ex);
            throw ex;
        }
    }

    @Override
    @Transactional
    public void registerEmployer(CreateUserRequest createUserRequest) throws Exception {
        try{
            PersistentUserEntity userEntity = register(createUserRequest, UserType.EMPLOYER);
            PersistentSystemTokenEntity systemTokenEntity = systemTokenService.generateEmailVerification(userEntity.getId());
            //TODO generate token and send mail for verification
        }catch (Exception ex){
            log.error("Error on registerEmployer", ex);
            throw ex;
        }
    }

    @Override
    @Transactional
    public boolean verifyEmail(UUID token){
        try{
            String userId = systemTokenService.validateAndGetAssociateId(token.toString(), TokenType.EMAIL_VERIFICATION, true);
            if (userId == null){
                return false;
            }
            Optional<PersistentUserEntity> userEntityOptional = userRepository.findById(userId);
            if (userEntityOptional.isEmpty()){
                return false;
            }
            PersistentUserEntity userEntity = userEntityOptional.get();
            userEntity.setAccountLocked(false);

            userRepository.saveAndFlush(userEntity);

            return true;
        }catch (Exception ex){
            log.error("Error on registerEmployer", ex);
            throw ex;
        }
    }

    @Override
    public CurrentUserData whomiam() {
        CustomUserDetails user = AuthenticationUtils.getCurrentUser();
        return new CurrentUserData()
                .userType(user.getUserType())
                .username(user.getUsername())
                .authorities(AuthenticationUtils.getCurrentUserAuthorities().parallelStream().map(role->new RoleDTO().authority(role)).collect(Collectors.toList()));
    }

    @Override
    public UserProfileDTO createUpdateCurrentUserProfile(UserProfileRequestDTO userProfileRequestDTO){
        try {
            CustomUserDetails currentUser = AuthenticationUtils.getCurrentUser();
            PersistentUserProfileEntity userProfile = userProfileRepository.findByUser_Id(currentUser.getUserId());
            if (userProfile == null){
                userProfile = new PersistentUserProfileEntity();
                userProfile.setUser(userRepository.findByUsername(currentUser.getUsername()).get());
            }
            userProfile = userProfileConverter.copyConvertToEntity(userProfileRequestDTO, userProfile);
            return userProfileConverter.convertToDTO(userProfileRepository.saveAndFlush(userProfile));
        }catch (Exception ex){
            log.error("Error on createCurrentUserProfile", ex);
            throw ex;
        }
    }

    @Override
    public UserProfileDTO getUserProfile() throws Exception{
        try {
            PersistentUserProfileEntity userProfile = userProfileRepository.findByUser_Id(AuthenticationUtils.getCurrentUser().getUserId());
            if (userProfile == null){
                throw new DataNotFoundException("Current user data not found");
            }
            return userProfileConverter.convertToDTO(userProfile);
        }catch (DataNotFoundException ex){
            throw ex;
        }catch (Exception ex){
            log.error("Error on createCurrentUserProfile", ex);
            throw ex;
        }
    }

    @Override
    public void changePassword(PasswordChangeRequestDTO passwordChangeRequestDTO) {
        try {
            PersistentUserEntity user = userRepository.findByUsername(AuthenticationUtils.getCurrentUser().getUsername()).get();

            user.setPassword(passwordEncoder.encode(passwordChangeRequestDTO.getNewPassword()));

            userRepository.saveAndFlush(user);
        }catch (Exception ex){
            log.error("error on changePassword", ex);
            throw ex;
        }
    }
}
*/
