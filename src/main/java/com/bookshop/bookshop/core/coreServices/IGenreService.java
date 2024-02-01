package com.bookshop.bookshop.core.coreServices;

import java.util.ArrayList;
import java.util.UUID;

import com.bookshop.bookshop.dtos.GenreModelDto;

public interface IGenreService 
{
    public ArrayList<GenreModelDto> GetAllGenres();
    public GenreModelDto GetGenreById(UUID id);
    public boolean CreateModel(GenreModelDto model);
    public boolean UpdateModel(GenreModelDto model);
    public boolean DeleteModelById(UUID id);
}
