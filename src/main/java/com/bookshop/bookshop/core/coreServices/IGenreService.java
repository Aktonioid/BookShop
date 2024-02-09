package com.bookshop.bookshop.core.coreServices;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.bookshop.bookshop.dtos.GenreModelDto;

public interface IGenreService 
{
    public CompletableFuture<List<GenreModelDto>> GetAllGenres();
    public CompletableFuture<GenreModelDto> GetGenreById(UUID id);
    public CompletableFuture<Boolean> CreateModel(GenreModelDto model);
    public CompletableFuture<Boolean> UpdateModel(GenreModelDto model);
    public CompletableFuture<Boolean> DeleteModelById(UUID id);
}
