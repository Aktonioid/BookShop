package com.bookshop.bookshop.core.mappers;

import java.util.stream.Collectors;

import com.bookshop.bookshop.core.models.BookModel;
import com.bookshop.bookshop.dtos.BookModelDto;

public class BookModelMapper 
{

    public static BookModel AsEntity(BookModelDto dto) 
    {
        return new BookModel(
            dto.getId(),
            dto.getBookName(),
            dto.getAuthorName(),
            dto.getPublisher(),
            dto.getPrice(),
            dto.getIsbn(),
            dto.getGenres().stream()
                            .map(GenreModelMapper::AsEntity)
                            .collect(Collectors.toSet()),
            dto.getDescription(),
            dto.getPictureUrl(),
            dto.getLeftovers()
        );
    }

    public static BookModelDto AsDto(BookModel model) 
    {
        return new BookModelDto(
                    model.getId(),
                    model.getBookName(),
                    model.getAuthorName(),
                    model.getPublisher(),
                    model.getPrice(),
                    model.getIsbn(),
                    model.getGenres()
                        .stream()
                        .map(GenreModelMapper::AsDto)
                        .collect(Collectors.toList()),
                    model.getDescription(),
                    model.getPictureUrl(),
                    model.getLeftovers());
    }
    
}
