package com.useraccount.user.util;

/**
 *
 * @author user
 */
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

   public void sendMailWithAttachment(String toEmail,
                                   String body,
                                   String subject,
                                   String attachment) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    mimeMessageHelper.setFrom("vardanyan.edgar.dev@gmail.com");
    mimeMessageHelper.setTo(toEmail);
    mimeMessageHelper.setText(body);
    mimeMessageHelper.setSubject(subject);

    FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
    mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource.getFile()); // Corrected this line

    javaMailSender.send(mimeMessage);
    System.out.println("Mail with attachment sent successfully.");
}

 
    @EventListener(ApplicationReadyEvent.class)
    public void triggerMail() throws MessagingException {
        this.sendMailWithAttachment("gevorg.ayvazyan.dev@gmail.com",
                "This is email body.",
                "This email subject", ""
                + "/home/user/Desktop/example.jpg");

    }
}