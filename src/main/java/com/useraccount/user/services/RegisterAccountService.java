
package com.useraccount.user.services;


import com.useraccount.user.data.AccountRegister;
import com.useraccount.user.repository.RegisterAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterAccountService {

    @Autowired
    private RegisterAccountRepository repo;

     public AccountRegister save(AccountRegister registerPerson) {
        return repo.save(registerPerson);
    }
    // Additional methods if needed
}
