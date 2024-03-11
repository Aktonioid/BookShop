package com.bookshop.bookshop.core.coreServices;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

import com.bookshop.bookshop.dtos.BookModelDto;
import com.bookshop.bookshop.dtos.GenreModelDto;

public interface IBookService 
{
    public CompletableFuture<List<BookModelDto>> GetAllModelsByPage(int page);   
    public CompletableFuture<BookModelDto> GetBookModelById(UUID id);
    public CompletableFuture<Boolean> CreateModel(BookModelDto model);
    public CompletableFuture<Boolean> DeleteModelById(UUID id);
    public CompletableFuture<Boolean> UpdateModel(BookModelDto model);
    public CompletableFuture<List<BookModelDto>> GetBooksByGenres(List<GenreModelDto> genres, int page);// нахождение книг по жанрам 
    public CompletableFuture<List<BookModelDto>> FindBooksByAuthor(String authorName, int page);// нахождение книг по авторам
    public CompletableFuture<List<BookModelDto>> FindBooksByName(String bookName, int page);// нахождение книг по именам
    public CompletableFuture<String> SaveBookCover(MultipartFile model); // сохранение обложки файла
    public CompletableFuture<Long> GetMaxPageForAll();// получениие максимальной страницы для всех книг
    public CompletableFuture<Long> GetMaxPageForGenresSearch(List<GenreModelDto> genres);// получениие максимальной 
                                                                                        //страницы при поиске по жанрам
    public CompletableFuture<Long> GetMaxPageForSearchByAuthor(String authorName);// получениие максимальной 
                                                                                //страницы пр и поиске по автору
    public CompletableFuture<Long> GetMaxPageForSearchByBookName(String bookName);// получениие максимальной 
                                                                                //страницы пр и поиске по названию

}
