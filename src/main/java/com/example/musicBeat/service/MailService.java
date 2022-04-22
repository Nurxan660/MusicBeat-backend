package com.example.musicBeat.service;

import com.example.musicBeat.exception.EmailSenderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    public void send(String to,String emailBody,String subject) {
        try {
            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(emailBody);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            mailSender.send(mimeMessage);
        }
        catch (MessagingException e){
            throw new EmailSenderException("failed to send email");
        }



    }






}
