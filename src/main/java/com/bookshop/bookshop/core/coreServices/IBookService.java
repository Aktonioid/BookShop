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
    public CompletableFuture<List<BookModelDto>> GetBooksByGenres(List<GenreModelDto> genres, int page);
    public CompletableFuture<String> SaveBookCover(MultipartFile model); // сохранение обложки файла
}
