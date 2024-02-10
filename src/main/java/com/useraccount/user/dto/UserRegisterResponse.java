/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.useraccount.user.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 *
 * @author gevorg
 */
@Data
public class UserRegisterResponse {
 
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private boolean isVerified;

    // Getters and setters
}