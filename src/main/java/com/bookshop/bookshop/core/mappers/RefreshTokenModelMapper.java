package com.bookshop.bookshop.core.mappers;

import com.bookshop.bookshop.core.models.RefreshTokenModel;
import com.bookshop.bookshop.dtos.RefreshTokenModelDto;

public class RefreshTokenModelMapper 
{
    public static RefreshTokenModel AsEntity(RefreshTokenModelDto dto)
    {
        if(dto == null)
        {
            return null;
        }
        
        return new RefreshTokenModel(dto.getId(), dto.getExpiredDate(), dto.getToken());
    }  

    public static RefreshTokenModelDto AsDto(RefreshTokenModel model)
    {
        if(model == null)
        {
            return null;
        }

        return new RefreshTokenModelDto(model.getId(), model.getToken(), model.getExpiredDate());
    }
}
