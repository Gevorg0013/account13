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

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;
    
    public void sendCode(String toEmail) throws MessagingException {

        String verificationLink = "http://localhost:8080/api/verify?email=" + toEmail;
        String body = "<p>Hello,</p><p>Please click the following link to access the API:</p>"
                + "<p><a href=\"" + verificationLink + "\">" + verificationLink + "</a></p>";

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
    
   
   


    public void triggerMail(final String toEmail) throws MessagingException {
        // Generate a random code
        sendCode(toEmail);
    }
}
