package com.bookshop.bookshop.core.coreServices;

import com.bookshop.bookshop.dtos.UserModelDto;

import jakarta.mail.MessagingException;

public interface IEmailService 
{
    public void sendVerificationEmail(UserModelDto userModel) throws MessagingException;
    public void sendEmailWithPassword(UserModelDto userModel, String password) throws MessagingException;
}
