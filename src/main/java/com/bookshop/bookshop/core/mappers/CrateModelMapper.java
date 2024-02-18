package com.bookshop.bookshop.core.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bookshop.bookshop.core.models.CrateModel;
import com.bookshop.bookshop.core.models.CratePartModel;
import com.bookshop.bookshop.dtos.CrateModelDto;
import com.bookshop.bookshop.dtos.CratePartModelDto;

public class CrateModelMapper
{
    public static CrateModel AsEntity(CrateModelDto dto)
    {
        if(dto == null)
        {
            return null;
        }

        Set<CratePartModel> cratePart = null; 

        if(dto.getBooks() != null)
        {
            cratePart = dto.getBooks()
                .stream()
                .map(CratePartModelMapper::AsEntity)
                .collect(Collectors.toSet());
        }

        return new CrateModel
        (
            dto.getId(),
            cratePart
        );
    }    

    public static CrateModelDto AsDto(CrateModel model)
    {
        if(model == null)
        {
            return null;
        }

        List<CratePartModelDto> crateParts = null;

        if(model.getBooks() != null)
        {
            crateParts = model.getBooks()
                .stream()
                .map(CratePartModelMapper::AsDto)
                .collect(Collectors.toList());
        }

        return new CrateModelDto
        (
            model.getId(),
            crateParts
        );
    }
}
