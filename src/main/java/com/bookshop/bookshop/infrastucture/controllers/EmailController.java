package com.bookshop.bookshop.infrastucture.controllers;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.bookshop.core.coreServices.IEmailService;
import com.bookshop.bookshop.core.coreServices.IUserService;
import com.bookshop.bookshop.dtos.UserModelDto;

import jakarta.mail.MessagingException;

@RestController()
@RequestMapping("/email")
public class EmailController 
{
    @Autowired
    IEmailService emailService;
    @Autowired
    IUserService userService;

    // endpoint для отправки сообщения на почту
    @PostMapping("send/{id}")
    public ResponseEntity<String> SendVerificationEmail(@PathVariable(name = "id") UUID userId) throws InterruptedException, ExecutionException, MessagingException
    {   
        UserModelDto userModel = userService.GetUserById(userId).get();

        if(userModel == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        emailService.sendVerificationEmail(userModel);
        System.out.println("send");

        return ResponseEntity.ok("отправлено");
    }

    // подтверждение email по id пользователя
    @PostMapping("verify/{id}")
    public ResponseEntity<String> CheckEmailVerification(@PathVariable(name = "id") UUID userId, String verificationCode) throws InterruptedException, ExecutionException
    {
        UserModelDto userModel = userService.GetUserById(userId).get();

        if(!userModel.getVeridficationCode().equals(verificationCode))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userModel.setEmailVerificated(true);
        userService.UpdateUser(userModel);

        return ResponseEntity.ok("Подтверждено");
    }
}
