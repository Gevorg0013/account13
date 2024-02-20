package com.useraccount.user.util;

/**
 *
 * @author user
 */
import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;


@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;
    
    public void sendCode(String toEmail, String hashedCode) throws MessagingException {

        String verificationLink = "http://localhost:8080/api/verify?hashedCode=" + hashedCode;
        String body = "Please click the following link to access the API:"
                + verificationLink;

        String subject = "Verification Code";

        sendMail(toEmail, body, subject);

    }

      
    private void sendMail(String toEmail, String body, String subject) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vardanyan.edgar.dev@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        System.out.println("Mail sent successfully.");
    }
    
   
   


    public void triggerMail(final String toEmail,final String hashedCode) throws MessagingException {
        // Generate a random code
        sendCode(toEmail,hashedCode);
    }
    
    public Optional<String> hashedCode(final String email) {
            try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add email bytes to digest
            md.update(email.getBytes());

            // Get the hashed bytes
            byte[] hashedBytes = md.digest();

            // Convert hashed bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            // Print the hashed value
            System.out.println("Hashed email: " + sb.toString());
            String hashedValue = sb.toString();
            return Optional.of(hashedValue);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: SHA-256 algorithm not found.");
            e.printStackTrace();
            return Optional.empty();

        }
    }
}
