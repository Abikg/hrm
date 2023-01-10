package com.book.repository;

import com.book.domain.PersistentSystemTokenEntity;
import com.book.enumconstant.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemTokenRepository extends JpaRepository<PersistentSystemTokenEntity, String> {

    PersistentSystemTokenEntity findByToken(String token);

    PersistentSystemTokenEntity findByTokenAndTokenType(String token , TokenType tokenType);
}
