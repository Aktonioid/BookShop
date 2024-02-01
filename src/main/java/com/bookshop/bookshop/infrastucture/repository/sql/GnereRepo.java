package com.bookshop.bookshop.infrastucture.repository.sql;

import java.util.ArrayList;
import java.util.UUID;

import com.bookshop.bookshop.core.coreRepositories.IGenreRepo;
import com.bookshop.bookshop.core.models.GenreModel;

public class GnereRepo implements IGenreRepo
{

    @Override
    public ArrayList<GenreModel> GetAllGenres() 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetAllGenres'");
    }

    @Override
    public GenreModel GetGenreById(UUID id) 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetGenreById'");
    }

    @Override
    public boolean CreateModel(GenreModel model) 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'CreateModel'");
    }

    @Override
    public boolean UpdateModel(GenreModel model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UpdateModel'");
    }

    @Override
    public boolean DeleteModelById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DeleteModelById'");
    }
    
}
