package com.bookshop.bookshop.core.mappers;

import java.util.stream.Collectors;

import com.bookshop.bookshop.core.models.OrderModel;
import com.bookshop.bookshop.core.models.UserModel;
import com.bookshop.bookshop.dtos.OrderModelDto;


public class OrderModelMapper
{
    public static OrderModel AsEntity(OrderModelDto dto)
    {
        if(dto == null)
        {
            return null;
        }

        return new OrderModel(
            dto.getId(),
            dto.isSend(),
            dto.getBooks().stream().map(OrderPartModelMapper::AsEntity).collect(Collectors.toSet()),
            dto.getSendDate(),
            dto.getUserFullName(),
            dto.getDeliveryAdress(),
            dto.isPaymentStatus(),
            new UserModel(dto.getUserId())
        );
    }

    public static OrderModelDto AsDto(OrderModel model)
    {
        if(model == null)
        {
            return null;
        }
        return new OrderModelDto
            (
                model.getId(),
                model.isSend(),
                model.getBooks().stream().map(OrderPartModelMapper::AsDto).collect(Collectors.toList()),
                model.getSendDate(),
                model.getUserFullName(),
                model.getDeliveryAdress(),
                model.isPaymentStatus(),
                model.getUserId().getId()
            );
    }
}
