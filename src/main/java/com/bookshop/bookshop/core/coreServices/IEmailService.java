package com.bookshop.bookshop.core.coreServices;

import java.util.concurrent.CompletableFuture;

import com.bookshop.bookshop.dtos.UserModelDto;

import jakarta.mail.MessagingException;

public interface IEmailService 
{
    public CompletableFuture<Boolean> sendVerificationEmail(UserModelDto userModel) throws MessagingException;
    public CompletableFuture<Boolean> sendEmailWithPassword(UserModelDto userModel, String password) throws MessagingException;
}
