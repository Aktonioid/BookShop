package com.bookshop.bookshop.infrastucture.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookshop.bookshop.core.coreRepositories.IUserRepo;
import com.bookshop.bookshop.core.coreServices.IAuthenticationService;
import com.bookshop.bookshop.core.models.UserModel;
import com.bookshop.bookshop.dtos.LogInModel;
import com.bookshop.bookshop.dtos.UserModelDto;

@Service
@EnableAsync
public class AuthenticationService implements IAuthenticationService 
{

    @Autowired
    IUserRepo userRepo;
    @Autowired
    PasswordEncoder encoder;

    // вернулось null, значит все норм, регаем дальше
    // елси вернулся не null значит username или email уже используются(Что именно используется указано в строке, которая выводиться)
    @Async
    @Override
    public CompletableFuture<String> SignUp(UserModelDto model) 
    {
        StringBuffer sb = new StringBuffer();

        if(userRepo.IsUserExhistsByEmail(model.getEmail()))
        {
            sb.append("email"); // пользователь с таким email уже существует
        }

        if(userRepo.IsUserExhistsByUsername(model.getUsername()))
        {
            sb.append("username");// пользователь с таким username уже существует
        }

        if(!sb.toString().isEmpty())
        {
            return CompletableFuture.completedFuture(sb.toString());
        }

        return null; // то что тут null возвращается - нормально
    }
    
    @Async
    @Override
    public CompletableFuture<Boolean> SignInByLogin(LogInModel login) 
    {
        UserModel userModel = userRepo.UserByUsername(login.getLogin());

        if(userModel == null)
        {
            return CompletableFuture.completedFuture(false); // пользователя с таким username нет в бд
        }

        // Проверка пароля
        if(encoder.matches(login.getPassword(), userModel.getPassword()))
        {
            return CompletableFuture.completedFuture(false);    
        }

        return CompletableFuture.completedFuture(true); // Все ок
    }

    @Async
    @Override
    public CompletableFuture<Boolean> SingInByEmail(LogInModel login) 
    {
        UserModel userModel = userRepo.UserByEmail(login.getLogin());

        if(userModel == null) // Есть ли user в бд
        {
            return CompletableFuture.completedFuture(false);
        }

        // Проверка пароля
        if(encoder.matches(login.getPassword(), userModel.getPassword()))
        {
            return CompletableFuture.completedFuture(false);
        }

        return CompletableFuture.completedFuture(true); // Все ок
    }

    
}
