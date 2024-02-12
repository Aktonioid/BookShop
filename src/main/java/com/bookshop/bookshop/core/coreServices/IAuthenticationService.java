package com.bookshop.bookshop.core.coreServices;

import java.util.concurrent.CompletableFuture;

import com.bookshop.bookshop.core.models.UserModel;
import com.bookshop.bookshop.dtos.LogInModel;
import com.bookshop.bookshop.dtos.UserModelDto;

public interface IAuthenticationService 
{
    public CompletableFuture<String> SignUp(UserModelDto model); // регистрация
    public CompletableFuture<Boolean> SignInByLogin(LogInModel login); // вход
    public CompletableFuture<Boolean> SingInByEmail(LogInModel login);
}
