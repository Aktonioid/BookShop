package com.bookshop.bookshop.infrastucture.services;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
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
    public List<GenreModelDto> GetAllGenres() 
    {
        List<GenreModelDto> genres = Collections.synchronizedList
        (
            genreRepo.GetAllGenres()
                .stream()
                .map(GenreModelMapper::AsDto)
                .collect(Collectors.toList())
        );

        return genres;
    }

    @Override
    @Async
    public GenreModelDto GetGenreById(UUID id) 
    {
        return GenreModelMapper.AsDto(genreRepo.GetGenreById(id));
    }

    @Override
    @Async
    public boolean CreateModel(GenreModelDto model) 
    {
        return genreRepo.CreateModel(GenreModelMapper.AsEntity(model));
    }

    @Override
    @Async
    public boolean UpdateModel(GenreModelDto model) 
    {
        return genreRepo.UpdateModel(GenreModelMapper.AsEntity(model));
    }

    @Override
    @Async
    public boolean DeleteModelById(UUID id) 
    {
        return genreRepo.DeleteModelById(id);
    }

    @Override
    public boolean IsGenreExhistsByName(String genreName) 
    {
        return genreRepo.IsGenreExhistsByName(genreName);
    }
    
}
