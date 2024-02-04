package com.bookshop.bookshop.core.mappers;

import com.bookshop.bookshop.core.models.GenreModel;
import com.bookshop.bookshop.dtos.GenreModelDto;

public class GenreModelMapper 
{

    public static GenreModel AsEntity(GenreModelDto dto) 
    {
        return new GenreModel(
            dto.getId(),
            dto.getGenreName()
        );
    }

    public static GenreModelDto AsDto(GenreModel model) 
    {
        return new GenreModelDto(
            model.getId(),
            model.getName()
        );
    }
    
}
