package com.bookshop.bookshop.infrastucture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshop.bookshop.core.coreServices.IAuthenticationService;
import com.bookshop.bookshop.core.models.UserModel;

@Service
public class AuthenticationService implements IAuthenticationService 
{
    @Autowired
    JwtService jwtService;

    @Override
    public String SignUp(UserModel reg) 
    {
        // Сначала проверки и сохранение данных в бд, а потом уже здесь

        return jwtService.GenerateToken(reg);
    }

    @Override
    public String SignIn(UserModel model)
    {
        // сначала проверки и получение из бд, потом уже суда

        return jwtService.GenerateToken(model);
    }
    
}
