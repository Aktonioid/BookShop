package com.bookshop.bookshop.infrastucture.services;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshop.bookshop.core.coreRepositories.IBookRepo;
import com.bookshop.bookshop.core.coreServices.IBookService;
import com.bookshop.bookshop.core.mappers.BookModelMapper;
import com.bookshop.bookshop.core.mappers.GenreModelMapper;
import com.bookshop.bookshop.dtos.BookModelDto;
import com.bookshop.bookshop.dtos.GenreModelDto;

@Service
public class BookService implements IBookService 
{

    @Autowired
    IBookRepo bookRepo;

    @Override
    public CompletableFuture<List<BookModelDto>> GetAllModelsByPage(int page) 
    {
        List<BookModelDto> books = Collections.synchronizedList(bookRepo.GetAllBooksByPage(page)
                                                    .stream()
                                                    .map(BookModelMapper::AsDto)
                                                    .collect(Collectors.toList())
                                                    );

        return CompletableFuture.completedFuture(books);
    }

    @Override
    public CompletableFuture<BookModelDto> GetBookModelById(UUID id) 
    {
        return CompletableFuture.completedFuture(BookModelMapper.AsDto(bookRepo.GetBookBookById(id)));
    }

    @Override
    public CompletableFuture<Boolean> CreateModel(BookModelDto model) 
    {
        return CompletableFuture.completedFuture(bookRepo.CreateBook(BookModelMapper.AsEntity(model)));
    }

    @Override
    public CompletableFuture<Boolean> DeleteModelById(UUID id) 
    {
        return CompletableFuture.completedFuture(bookRepo.DeleteBookById(id));
    }

    @Override
    public CompletableFuture<Boolean> UpdateModel(BookModelDto model) 
    {
        return CompletableFuture.completedFuture(bookRepo.UpdateBook(BookModelMapper.AsEntity(model)));
    }

    @Override
    public CompletableFuture<List<BookModelDto>> GetBooksByGenres(List<GenreModelDto> genres, int page) 
    {
        List<BookModelDto> models = Collections.synchronizedList(
            bookRepo.GetBooksByGenres(genres.stream().map(GenreModelMapper::AsEntity).collect(Collectors.toList()), page)
                .stream()
                .map(BookModelMapper::AsDto)
                .collect(Collectors.toList())
        );

        return CompletableFuture.completedFuture(models);
    }

}
