package com.book.service;

import com.book.domain.PersistentSystemTokenEntity;
import com.book.enumconstant.TokenType;

public interface SystemTokenService {
    PersistentSystemTokenEntity generateEmailVerification(String userId);

    PersistentSystemTokenEntity generatePasswordReset(String userId);

    boolean validate(String token, TokenType tokenType, String associateId, boolean delete);

    String validateAndGetAssociateId(String token, TokenType tokenType, boolean delete);
}
