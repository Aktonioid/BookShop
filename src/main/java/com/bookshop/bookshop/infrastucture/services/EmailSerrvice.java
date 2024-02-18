package com.bookshop.bookshop.infrastucture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.bookshop.bookshop.core.coreServices.IEmailService;
import com.bookshop.bookshop.dtos.UserModelDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
@PropertySource("email.properties")
@EnableAsync
public class EmailSerrvice implements IEmailService
{
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    Environment env;

    @Override
    @Async
    public void sendVerificationEmail(UserModelDto userModel) throws MessagingException
    {
        String toAddres = userModel.getEmail();
        String fromAdress = env.getProperty("spring.mail.username"); // кто присылает сообщение
        String subject = "Please verify your registration"; // Тема сообщения
        // текст сообщения
        String content = "Dear "+ userModel.getName() + " thanks for registration\n"
                        +"Your verification code is: "+ userModel.getVeridficationCode();


        EmailSend(toAddres, fromAdress, subject, content);
    }

    @Override
    @Async
    public void sendEmailWithPassword(UserModelDto userModel, String password) throws MessagingException 
    {
        String toAddres = userModel.getEmail();
        String fromAdress = env.getProperty("spring.mail.username"); // кто присылает сообщение
        String subject = "Your new password"; // Тема сообщения
        // текст сообщения
        String content = "Dear "+ userModel.getName() + " you forget your password\n"
                        +"Your new password: "+ password;
        //
        
        EmailSend(toAddres, fromAdress, subject, content);
    }
    
    @SuppressWarnings("null")
    private void EmailSend(String toAddres,
                        String fromAdress,
                        String subject,
                        String content) throws MessagingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject(subject);
        helper.setTo(toAddres);
        helper.setFrom(fromAdress);
        
        helper.setText(content);

        mailSender.send(message);
    }
}
