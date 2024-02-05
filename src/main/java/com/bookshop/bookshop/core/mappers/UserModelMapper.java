package com.bookshop.bookshop.core.mappers;

import com.bookshop.bookshop.core.models.UserModel;
import com.bookshop.bookshop.dtos.UserModelDto;

public class UserModelMapper 
{
    public static UserModel AsEntity(UserModelDto dto)
    {
        return new UserModel(
            dto.getId(), 
            dto.getUsername(), 
            dto.getName(), 
            dto.getUserSurname(), 
            dto.getEmail(), 
            dto.getPassword(), 
            dto.getRole());
    }    

    public static UserModelDto AsDto(UserModel model)
    {
        return new UserModelDto(
            model.getId(),
            model.getUsername(),
            model.getName(),
            model.getUserSurname(),
            model.getEmail(),
            model.getPassword(),
            model.getRole()
        );
    }
}
