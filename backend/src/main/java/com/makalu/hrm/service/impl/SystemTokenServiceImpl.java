package com.makalu.hrm.service.impl;

import com.makalu.hrm.domain.PersistentSystemTokenEntity;
import com.makalu.hrm.enumconstant.TokenType;
import com.makalu.hrm.repository.SystemTokenRepository;
import com.makalu.hrm.service.SystemTokenService;
import com.makalu.hrm.utils.ParseUtils;
import com.makalu.hrm.utils.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemTokenServiceImpl implements SystemTokenService {

    private final SystemTokenRepository systemTokenRepository;

    private PersistentSystemTokenEntity generate(TokenType tokenType, String associateId) {
        if (tokenType == null) {
            return null;
        }
        synchronized (this.getClass()) {
            String token = UUIDGenerator.INSTANCE().newUUIDString();
            while (systemTokenRepository.findByToken(token) != null) {
                token = UUIDGenerator.INSTANCE().newUUIDString();
            }

            PersistentSystemTokenEntity systemToken = new PersistentSystemTokenEntity();

            systemToken.setToken(token);
            systemToken.setTokenType(tokenType);
            systemToken.setExpiration(ParseUtils.calculateExpiryDate(1));
            systemToken.setAssociateId(associateId);

            return systemTokenRepository.saveAndFlush(systemToken);
        }
    }

    @Override
    public PersistentSystemTokenEntity generateEmailVerification(String userId) {
        try {
            return generate(TokenType.EMAIL_VERIFICATION, userId);
        } catch (Exception ex) {
            log.error("error on generateEmailVerification", ex);
            throw ex;
        }
    }

    @Override
    public PersistentSystemTokenEntity generatePasswordReset(String userId) {
        try {
            return generate(TokenType.RESET_PASSWORD, userId);
        } catch (Exception ex) {
            log.error("error on generatePasswordReset", ex);
            throw ex;
        }
    }

    private boolean validate(PersistentSystemTokenEntity systemToken, TokenType tokenType, String associateId, boolean delete) {
        synchronized (this.getClass()) {
            if (systemToken == null) {
                return false;
            }

            if (!systemToken.getAssociateId().equals(associateId)) {
                return false;
            }

            Date now = new Date();
            if (now.getTime() > systemToken.getExpiration().getTime()) {
                systemTokenRepository.delete(systemToken);
                return false;
            }

            if (delete) {
                systemTokenRepository.delete(systemToken);
            }

            return true;
        }

    }

    @Override
    public boolean validate(String token, TokenType tokenType, String associateId, boolean delete) {
        try {
            PersistentSystemTokenEntity systemToken = systemTokenRepository.findByTokenAndTokenType(token, tokenType);
            return validate(systemToken, tokenType, associateId, delete);
        } catch (Exception ex) {
            log.error("error on validate", ex);
            throw ex;
        }
    }

    @Override
    public String validateAndGetAssociateId(String token, TokenType tokenType, boolean delete) {
        try {
            PersistentSystemTokenEntity systemToken = systemTokenRepository.findByTokenAndTokenType(token, tokenType);
            return validate(systemToken, tokenType, systemToken.getAssociateId(), delete) ? systemToken.getAssociateId() : null;
        } catch (Exception ex) {
            log.error("error on validateAndGetAssociateId", ex);
            throw ex;
        }
    }

}
