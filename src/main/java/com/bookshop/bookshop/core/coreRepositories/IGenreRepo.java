package com.bookshop.bookshop.core.coreRepositories;

import java.util.List;
import java.util.UUID;

import com.bookshop.bookshop.core.models.GenreModel;


public interface IGenreRepo 
{
    public List<GenreModel> GetAllGenres();
    public GenreModel GetGenreById(UUID id);
    public boolean CreateModel(GenreModel model);
    public boolean UpdateModel(GenreModel model);
    public boolean DeleteModelById(UUID id);
    public boolean IsGenreExhistsByName(String genreName);
}
