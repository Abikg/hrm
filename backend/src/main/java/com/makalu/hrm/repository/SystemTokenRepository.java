package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentSystemTokenEntity;
import com.makalu.hrm.enumconstant.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemTokenRepository extends JpaRepository<PersistentSystemTokenEntity, String> {

    PersistentSystemTokenEntity findByToken(String token);

    PersistentSystemTokenEntity findByTokenAndTokenType(String token, TokenType tokenType);
}
