package com.bookshop.bookshop.core.mappers;

import java.util.stream.Collectors;

import com.bookshop.bookshop.core.models.CrateModel;
import com.bookshop.bookshop.core.models.UserModel;
import com.bookshop.bookshop.dtos.CrateModelDto;

public class CrateModelMapper
{
    public static CrateModel AsEntity(CrateModelDto dto)
    {
        return new CrateModel
        (
            dto.getId(),
            new UserModel(dto.getId()),
            dto.getBooks()
                .stream()
                .map(CratePartModelMapper::AsEntity)
                .collect(Collectors.toSet())
        );
    }    

    public static CrateModelDto AsDto(CrateModel model)
    {
        return new CrateModelDto
        (
            model.getId(),
            model.getUserId().getId(),
            model.getBooks()
                .stream()
                .map(CratePartModelMapper::AsDto)
                .collect(Collectors.toList())
        );
    }
}
