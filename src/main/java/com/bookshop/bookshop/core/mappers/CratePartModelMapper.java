package com.bookshop.bookshop.core.mappers;

import com.bookshop.bookshop.core.models.CrateModel;
import com.bookshop.bookshop.core.models.CratePartModel;
import com.bookshop.bookshop.dtos.CratePartModelDto;

public class CratePartModelMapper 
{
    public static CratePartModel AsEntity(CratePartModelDto dto)
    {
        if(dto == null)
        {
            return null;
        }

        return new CratePartModel
        (
            dto.getId(),
            BookModelMapper.AsEntity(dto.getBook()),
            dto.getBookCount(),
            new CrateModel(dto.getCrateId())
        );
    }    

    public static CratePartModelDto AsDto(CratePartModel model)
    {
        if(model == null)
        {
            return null;
        }

        return new CratePartModelDto
        (
            model.getId(),
            BookModelMapper.AsDto(model.getBook()),
            model.getBookCount(),
            model.getCrate().getId()
        );
    }
}
