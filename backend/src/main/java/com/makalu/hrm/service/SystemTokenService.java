package com.makalu.hrm.service;

import com.makalu.hrm.domain.PersistentSystemTokenEntity;
import com.makalu.hrm.enumconstant.TokenType;

public interface SystemTokenService {
    PersistentSystemTokenEntity generateEmailVerification(String userId);

    PersistentSystemTokenEntity generatePasswordReset(String userId);

    boolean validate(String token, TokenType tokenType, String associateId, boolean delete);

    String validateAndGetAssociateId(String token, TokenType tokenType, boolean delete);
}
