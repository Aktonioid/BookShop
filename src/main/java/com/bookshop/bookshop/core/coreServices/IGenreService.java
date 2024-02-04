package com.bookshop.bookshop.core.coreServices;

import java.util.List;
import java.util.UUID;

import com.bookshop.bookshop.dtos.GenreModelDto;

public interface IGenreService 
{
    public List<GenreModelDto> GetAllGenres();
    public GenreModelDto GetGenreById(UUID id);
    public boolean CreateModel(GenreModelDto model);
    public boolean UpdateModel(GenreModelDto model);
    public boolean DeleteModelById(UUID id);
}
