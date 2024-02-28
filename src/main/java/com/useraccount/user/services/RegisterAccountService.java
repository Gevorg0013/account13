
package com.useraccount.user.services;


import com.useraccount.user.domain.AccountRegister;
import com.useraccount.user.dto.UserRegisterRequest;
import com.useraccount.user.dto.UserRegisterResponse;
import com.useraccount.user.repository.RegisterAccountRepository;
import com.useraccount.user.util.EmailSenderService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    EmailSenderService emaolService;

    public AccountRegister save(UserRegisterRequest registerDTO) {

        AccountRegister map = modelMapper.map(registerDTO, AccountRegister.class);
        map.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        Optional<String> hashedCode = emaolService.hashedCode(registerDTO.getEmail());
        map.setHashedVerificationCode(hashedCode.get());
        return repo.save(map);
    }

    public UserRegisterResponse getAllAccount(final String password, final String email) {

        UserRegisterResponse result = new UserRegisterResponse();
        AccountRegister user = repo.findByEmail(email);
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (matches) {
            result = modelMapper.map(user, UserRegisterResponse.class);
        }

        return result;

    }
    
    public boolean isEmailUnique(final String email) {
         if (email == null || email.isEmpty()) {
            return false;
        }
         boolean existsByEmail = repo.existsByEmail(email);
         if(existsByEmail == true) {
             return false;
         }
         return true;
    }

    public boolean emailVerification(final String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        boolean existsByEmail = repo.existsByEmail(email);
        return !existsByEmail;
    }
    
   public boolean deleteById(final Long userId) {
        // Delete the entity by its ID and return whether the deletion was successful
        try {
            repo.deleteById(userId);
            return true; // Deletion successful
        } catch (Exception e) {
            return false; // Deletion failed
        }
    }

    public Optional<List<UserRegisterResponse>> getAllUser() {
        List<AccountRegister> findAll = repo.findAll();
        if (findAll == null || findAll.isEmpty()) {
            return Optional.empty();
        }
        List<UserRegisterResponse> userList = modelMapper.map(findAll, ArrayList.class);
        return Optional.of(userList);
    }

    public Optional<UserRegisterResponse> getUserById(final Long userId) {
        Optional<AccountRegister> findById = repo.findById(userId);
        if (findById == null || findById.isEmpty()) {
            return null;
        }
        UserRegisterResponse map = modelMapper.map(findById.get(), UserRegisterResponse.class);
        return Optional.of(map);

    }
    
    public AccountRegister getUserByhashedEmailCode(final String hashedCode) {
        AccountRegister user = repo.findByHashedVerificationCode(hashedCode);
        if(user != null) {
            return user;
        }
        return null;
    }
    
}