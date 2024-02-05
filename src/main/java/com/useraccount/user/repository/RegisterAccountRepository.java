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
    boolean existsByEmail(final String email);
}