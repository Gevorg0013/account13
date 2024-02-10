
package com.useraccount.user.services;


import com.useraccount.user.domain.AccountRegister;
import com.useraccount.user.dto.UserRegisterRequest;
import com.useraccount.user.dto.UserRegisterResponse;
import com.useraccount.user.repository.RegisterAccountRepository;
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
    

    public List<UserRegisterResponse> getAllUsers() {
        List<AccountRegister> findAll = repo.findAll();
        List<UserRegisterResponse> response = new ArrayList<>();
        if (findAll != null) {
            for (AccountRegister user : findAll) {
                UserRegisterResponse map = modelMapper.map(user, UserRegisterResponse.class);
                response.add(map);
            }
            return response;
        }
        return response;
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

    public Optional<UserRegisterResponse> getUserById(final Long userId) {
        Optional<AccountRegister> findById = repo.findById(userId);
        if (findById == null || findById.isEmpty()) {
            return null;
        }
        UserRegisterResponse map = modelMapper.map(findById.get(), UserRegisterResponse.class);
        return Optional.of(map);

    }
    
    public AccountRegister getUserByEmail(final String email) {
        AccountRegister findByEmail = repo.findByEmail(email);
        if(findByEmail != null) {
            return findByEmail;
        }
        return null;
    }
}