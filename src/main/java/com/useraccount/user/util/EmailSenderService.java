package com.useraccount.user.util;

/**
 *
 * @author user
 */
import jakarta.mail.MessagingException;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import java.util.*;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;
    
    private Map<String, String> emailCodeMap = new HashMap<>();


    public void sendCode(String toEmail, String code) throws MessagingException {
        String body = "Your verification code is: " + code;
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
    
      public boolean verifyCode(String email, String code) {
        // Check if the email is present in the map and if the code matches
        return emailCodeMap.containsKey(email) && emailCodeMap.get(email).equals(code);
    }


    public void triggerMail(final String toEmail) throws MessagingException {
        // Generate a random code
        String randomCode = generateRandomCode();
        // Send email with the random code
        sendCode(toEmail, randomCode);
    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Random number between 100000 and 999999
        return String.valueOf(code);
    }
}
