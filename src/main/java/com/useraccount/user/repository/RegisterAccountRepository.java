package com.useraccount.user.repository;

import com.useraccount.user.domain.AccountRegister;
import java.util.Optional;
import java.util.*; // import the ArrayList class
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gevorg
 */


@Repository
public interface RegisterAccountRepository extends JpaRepository<AccountRegister, Long> {
    List<AccountRegister> findAllByEmail(String email);
}