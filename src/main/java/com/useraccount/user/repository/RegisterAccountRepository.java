package com.useraccount.user.repository;

import com.useraccount.user.data.AccountRegister;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gevorg
 */


@Repository
public interface RegisterAccountRepository extends JpaRepository<AccountRegister, Long> {
//    Optional<AccountRegister> findById(String password);
    
    
}