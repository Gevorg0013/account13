package com.useraccount.user.repository;

import com.useraccount.user.domain.AccountRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gevorg
 */


@Repository
public interface RegisterAccountRepository extends JpaRepository<AccountRegister, Long> {
    AccountRegister findByEmail(final String email);
    AccountRegister findByHashedVerificationCode(final String verificationCode);
    boolean existsByEmail(final String email);
}