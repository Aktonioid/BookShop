package com.bookshop.bookshop.infrastucture.controllers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.bookshop.core.coreServices.IBookService;
import com.bookshop.bookshop.dtos.BookModelDto;
import com.bookshop.bookshop.dtos.GenreModelDto;

@RestController
@RequestMapping("/books")
//здесь только 
public class BooksController 
{
    @Autowired
    IBookService bookService;


    @GetMapping("/{page}")
    public ResponseEntity<List<BookModelDto>> GetAllModelsByPage(@PathVariable(name = "page") int page) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.GetAllModelsByPage(page).get());
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<BookModelDto> GetBookById(@PathVariable("bookId") UUID bookId) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.GetBookModelById(bookId).get());
    }


    @GetMapping("/bygenres/{page}")
    public ResponseEntity<List<BookModelDto>> GetBooksByGenres(@RequestParam List<GenreModelDto> genres, 
                                                                @PathVariable("page") int page) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.GetBooksByGenres(genres, page).get());
    }
}
