package com.bookshop.bookshop.infrastucture.controllers;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.bookshop.core.coreRepositories.IBookRepo;
import com.bookshop.bookshop.core.coreServices.IBookService;
import com.bookshop.bookshop.core.models.BookModel;
import com.bookshop.bookshop.infrastucture.repository.sql.GnereRepo;
import com.bookshop.bookshop.infrastucture.repository.sql.UserRepo;
import com.bookshop.bookshop.infrastucture.services.JwtService;
import com.bookshop.bookshop.infrastucture.services.UserService;

@RestController
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
    @Autowired
    IBookRepo bookRepo;
    @Autowired
    IBookService bookService;

    @GetMapping("/count")
    public ResponseEntity<Long> CountBooks()
    {
        return ResponseEntity.ok(bookRepo.GetMaxPageForAll());
    }

    @GetMapping("/author")
    public ResponseEntity<List<BookModel>> GetBooksByAuthor(String authorName)
    {
        return ResponseEntity.ok(bookRepo.FindBooksByAuthor(authorName, 1));
    }

    @GetMapping("/author/pages")
    public ResponseEntity<Long> GetMaxPageForSearchByAuthor(String authorName) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.GetMaxPageForSearchByAuthor(authorName));
    }
}
