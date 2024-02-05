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

import com.bookshop.bookshop.core.coreServices.IJwtService;
import com.bookshop.bookshop.core.coreServices.IUserService;
import com.bookshop.bookshop.core.mappers.UserModelMapper;
import com.bookshop.bookshop.core.models.Role;
import com.bookshop.bookshop.core.models.UserModel;
import com.bookshop.bookshop.dtos.LogInModel;
import com.bookshop.bookshop.dtos.UserModelDto;
import com.bookshop.bookshop.infrastucture.services.UserService;

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

    @PostMapping("/login")
    public ResponseEntity<String> LogIn(@RequestBody LogInModel model) throws InterruptedException, ExecutionException
    {
        if(userService.GetUserByUserName(model.getUsername()) == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        model.setPassword(encoder.encode(model.getPassword()));
       
        // if(!userService.LogIn(model).get())
        // {
        //     return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // }

        String token = jwtService.GenerateToken(UserModelMapper
                    .AsEntity(
                        userService.GetUserByUserName(model.getUsername()).get()
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
