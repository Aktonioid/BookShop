package com.bookshop.bookshop.infrastucture.controllers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.bookshop.core.mappers.UserModelMapper;
import com.bookshop.bookshop.core.models.GenreModel;
import com.bookshop.bookshop.core.models.UserModel;
import com.bookshop.bookshop.infrastucture.repository.sql.GnereRepo;
import com.bookshop.bookshop.infrastucture.repository.sql.UserRepo;
import com.bookshop.bookshop.infrastucture.services.JwtService;
import com.bookshop.bookshop.infrastucture.services.UserService;

@Controller
@RequestMapping("test")
public class TestController 
{
    @Autowired
    GnereRepo repo;
    @Autowired
    JwtService service;
    @Autowired
    UserService userService;
    @Autowired
    UserRepo userRepo;

    @GetMapping("/")
    public ResponseEntity<List<GenreModel>> GetAll()
    {
        return ResponseEntity.ok(repo.GetAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreModel> GetById(@PathVariable(name = "id") UUID id)
    {
        return ResponseEntity.ok(repo.GetGenreById(id));
    }

    @PostMapping("/")
    public ResponseEntity<String> Create(@RequestBody GenreModel model)
    {
        repo.CreateModel(model);
        return ResponseEntity.ok("");
    }

    @GetMapping("/secured")
    public ResponseEntity<String> Secured()
    {
        return ResponseEntity.ok("Secured");
    }

    @GetMapping("/jwt")
    public ResponseEntity<Boolean> jwt(String jwt, String username) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(service.IsTokenValid(jwt, UserModelMapper.AsEntity(userService.GetUserByUserName(username).get())));
    }
    @GetMapping("/user")
    public ResponseEntity<UserModel> GetUserByUserName(String username) throws InterruptedException, ExecutionException
    {
        UserModel model  = UserModelMapper.AsEntity(userService.GetUserByUserName(username).get());
        return ResponseEntity.ok(model);
    }


    @GetMapping("/jwtest")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token)
    {
        System.out.println(token);

        return ResponseEntity.ok(false);
    }
}
