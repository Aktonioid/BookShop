package com.bookshop.bookshop.infrastucture.services;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.bookshop.bookshop.core.coreRepositories.IGenreRepo;
import com.bookshop.bookshop.core.coreServices.IGenreService;
import com.bookshop.bookshop.core.mappers.GenreModelMapper;
import com.bookshop.bookshop.dtos.GenreModelDto;

@Service
@EnableAsync
public class GenreService implements IGenreService
{

    @Autowired
    IGenreRepo genreRepo;

    @Override
    @Async
    public CompletableFuture<List<GenreModelDto>> GetAllGenres() 
    {
        List<GenreModelDto> genres = Collections.synchronizedList
        (
            genreRepo.GetAllGenres()
                .stream()
                .map(GenreModelMapper::AsDto)
                .collect(Collectors.toList())
        );

        return CompletableFuture.completedFuture(genres);
    }

    @Override
    @Async
    public CompletableFuture<GenreModelDto> GetGenreById(UUID id) 
    {
        return CompletableFuture.completedFuture(GenreModelMapper.AsDto(genreRepo.GetGenreById(id)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> CreateModel(GenreModelDto model) 
    {
        return CompletableFuture.completedFuture(genreRepo.CreateModel(GenreModelMapper.AsEntity(model)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> UpdateModel(GenreModelDto model) 
    {
        return CompletableFuture.completedFuture(genreRepo.UpdateModel(GenreModelMapper.AsEntity(model)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> DeleteModelById(UUID id) 
    {
        return CompletableFuture.completedFuture(genreRepo.DeleteModelById(id));
    }

    @Override
    public CompletableFuture<Boolean> IsGenreExhistsByName(String genreName) 
    {
        return CompletableFuture.completedFuture(genreRepo.IsGenreExhistsByName(genreName));
    }
    
}
