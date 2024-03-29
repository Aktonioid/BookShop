package com.bookshop.bookshop.core.mappers;

import com.bookshop.bookshop.core.models.OrderModel;
import com.bookshop.bookshop.core.models.OrderPartModel;
import com.bookshop.bookshop.dtos.OrderPartModelDto;

public class OrderPartModelMapper 
{
    public static OrderPartModelDto AsDto(OrderPartModel model)
    {
        if(model == null)
        {
            return null;
        }

        return new OrderPartModelDto(
                    model.getId(), 
                    BookModelMapper.AsDto(model.getBook()), 
                    model.getBookCount(), 
                    model.getOrderId().getId());
    }    

    public static OrderPartModel AsEntity(OrderPartModelDto dto)
    {
        if(dto == null)
        {
            return null;
        }
        
        return new OrderPartModel
                    (
                        dto.getId(),
                        BookModelMapper.AsEntity(dto.getBook()),
                        dto.getBookCount(),
                        new OrderModel(dto.getOrderId())
                    );
    }
}
