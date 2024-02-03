
package com.useraccount.user.services;


import com.useraccount.user.domain.AccountRegister;
import com.useraccount.user.dto.UserRegisterRequest;
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
        AccountRegister user = repo.findByEmail(email);
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (matches) {
            result = modelMapper.map(user, UserRegisterRequest.class);
        }

        return result;

    }

    public boolean emailVerification(final String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return repo.existsByEmail(email);
    }
}
