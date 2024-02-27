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

    // получение книг по страницам без поисков и фильтров
    @GetMapping("/{page}")
    public ResponseEntity<List<BookModelDto>> GetAllModelsByPage(@PathVariable(name = "page") int page) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.GetAllModelsByPage(page).get());
    }

    // получение книги по id
    @GetMapping("/book/{bookId}")
    public ResponseEntity<BookModelDto> GetBookById(@PathVariable("bookId") UUID bookId) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.GetBookModelById(bookId).get());
    }

    // по страничный поиск по жанрам
    @GetMapping("/bygenres/{page}")
    public ResponseEntity<List<BookModelDto>> GetBooksByGenres(@RequestParam List<GenreModelDto> genres, 
                                                                @PathVariable("page") int page) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.GetBooksByGenres(genres, page).get());
    }

    // поиск по страницам по автору
    @GetMapping("/find/author/{page}")
    public ResponseEntity<List<BookModelDto>> FindBooksByAuthor(String authorName, @PathVariable("page")int page)
                                                throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.FindBooksByAuthor(authorName, page).get());
    }

    // Поиск по названию книги по страницам
    @GetMapping("/find/name/{page}")
    public ResponseEntity<List<BookModelDto>> FindBooksByName(String bookName, @PathVariable("page") int page) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.FindBooksByName(bookName, page).get());
    }

    // получение всех страниц когда нет поиска
    @GetMapping("/pages/all")
    public ResponseEntity<Long> GetMaxPageForAll() throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.GetMaxPageForAll().get());
    }

    // получение максимального колличества страниц при поиске по жанрам
    @GetMapping("/pages/genres")
    public ResponseEntity<Long> GetMaxPageForGenresSearch(@RequestParam List<GenreModelDto> genres) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.GetMaxPageForGenresSearch(genres).get());
    }

    // получение колличества страниц при поиске по автору
    @GetMapping("/pages/author")
    public ResponseEntity<Long> GetMaxPageForSearchByAuthor(String authorName) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.GetMaxPageForSearchByAuthor(authorName).get());
    }

    // получение максимального колличества страниц при поиске по названию книги
    @GetMapping("/pages/name")
    public ResponseEntity<Long> GetMaxPageForSearchByBookName(String bookName) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(bookService.GetMaxPageForSearchByBookName(bookName).get());
    }
}