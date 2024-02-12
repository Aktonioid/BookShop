package com.bookshop.bookshop.infrastucture.controllers;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.bookshop.core.coreServices.IAuthenticationService;
import com.bookshop.bookshop.core.coreServices.IJwtService;
import com.bookshop.bookshop.core.coreServices.IUserService;
import com.bookshop.bookshop.core.mappers.UserModelMapper;
import com.bookshop.bookshop.core.models.Role;
import com.bookshop.bookshop.dtos.LogInModel;
import com.bookshop.bookshop.dtos.UserModelDto;

@RequestMapping("/user")
@Controller
public class UserController 
{
    @Autowired
    IUserService userService;
    @Autowired
    IJwtService jwtService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    IAuthenticationService authenticationService;


    // для тестирования, что оно работает
    @PostMapping("create")
    public ResponseEntity<String> CreateUser(@RequestBody UserModelDto model) throws InterruptedException, ExecutionException
    {
        model.setId(UUID.randomUUID());
        model.setPassword(encoder.encode(model.getPassword()));
        model.setRole(Role.ROLE_USER);
        if(!userService.CreateUser(model).get())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("");
    }

    @PostMapping("/register")
    // надо подумать как создать проверку на то что определенные поля уже заняты и как это отправлять на фронт
    // Написать объект отдельный для регистрации с boolean полями что есть,чего нет? Просто проверка на заполненность полей делается на фронте
    // хотя проверку на то что все обязательные поля заполнены тож надо сделать
    public ResponseEntity<String> UserRegistration(@RequestBody UserModelDto userModel) // мб надо прописать отдельный класс для ответов, так как токен одни хрен в куках пищется
    {
        if(userModel == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // указывет на то указаны ли при регистрации уже существующие в бд email и username
        String mistakes = "";
        
        try 
        {
            mistakes = authenticationService.SignUp(userModel).get();
        } 
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(!mistakes.isEmpty())
        {
            return new ResponseEntity<String>(mistakes, HttpStatus.BAD_REQUEST);
        }

        userModel.setRole(Role.ROLE_USER);
        userModel.setId(UUID.randomUUID());

        userService.CreateUser(userModel);


        //потом просто пустоту в ответ давать будет, а токен он возвращает чтоб мне удобней было проверять
        return ResponseEntity.ok(jwtService.GenerateToken(UserModelMapper.AsEntity(userModel)));
    }

    @PostMapping("/email")
    public ResponseEntity<String> LogInByEmail(@RequestBody LogInModel model) throws InterruptedException, ExecutionException
    {

        if(userService.GetUserByUserName(model.getLogin()) == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        model.setPassword(encoder.encode(model.getPassword()));
       

        if(!authenticationService.SingInByEmail(model).get())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String token = jwtService.GenerateToken(UserModelMapper
                    .AsEntity(
                        userService.GetUserByUserName(model.getLogin()).get()
                        )
                    );


        return ResponseEntity.ok(token);
    }

    @PostMapping("/username")
    public ResponseEntity<String> LogInByUsername(@RequestBody LogInModel model) throws InterruptedException, ExecutionException
    {

        if(userService.GetUserByUserName(model.getLogin()) == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        model.setPassword(encoder.encode(model.getPassword()));
       
        if(!authenticationService.SignInByLogin(model).get())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String token = jwtService.GenerateToken(UserModelMapper
                    .AsEntity(
                        userService.GetUserByUserName(model.getLogin()).get()
                        )
                    );


        return ResponseEntity.ok(token);
    }

    @GetMapping("/user")
    public ResponseEntity<UserModelDto> GetUserByUsername(String username) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(userService.GetUserByUserName(username).get());
    }
}
