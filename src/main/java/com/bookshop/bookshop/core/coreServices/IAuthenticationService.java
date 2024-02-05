package com.bookshop.bookshop.core.coreServices;

import com.bookshop.bookshop.core.models.UserModel;

public interface IAuthenticationService 
{
    public String SignUp(UserModel model); // регистрация
    public String SignIn(UserModel model); // вход
}
