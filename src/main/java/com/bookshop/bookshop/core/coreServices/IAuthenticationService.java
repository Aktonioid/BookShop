package com.bookshop.bookshop.core.coreServices;


import com.bookshop.bookshop.dtos.LogInModel;
import com.bookshop.bookshop.dtos.UserModelDto;

public interface IAuthenticationService 
{
    public String SignUp(UserModelDto model); // регистрация
    public boolean SignInByLogin(LogInModel login); // вход
    public boolean SingInByEmail(LogInModel login);
}
