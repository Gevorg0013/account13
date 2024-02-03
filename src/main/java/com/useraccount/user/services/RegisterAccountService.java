
package com.useraccount.user.services;


import com.useraccount.user.domain.AccountRegister;
import com.useraccount.user.dto.UserRegisterRequest;
import java.util.*; // import the ArrayList class

import com.useraccount.user.repository.RegisterAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterAccountService {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RegisterAccountRepository repo;

    public AccountRegister save(UserRegisterRequest registerDTO) {

        AccountRegister map = modelMapper.map(registerDTO, AccountRegister.class);
        map.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        return repo.save(map);
    }

    public UserRegisterRequest getAllAccount(final String password, final String email) {

        UserRegisterRequest result = new UserRegisterRequest();
        List<AccountRegister> findAllByEmail = repo.findAllByEmail(email);
        for (AccountRegister user : findAllByEmail) {
            boolean matches = passwordEncoder.matches(password, user.getPassword());
            if (matches) {
                result = modelMapper.map(user, UserRegisterRequest.class);
                break;
            }
        }
        return result;
//        return new ArrayList<AccountRegister>();

    }
}
