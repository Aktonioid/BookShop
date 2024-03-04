package com.bookshop.bookshop.core.coreServices;

import java.util.List;
import java.util.UUID;

import com.bookshop.bookshop.dtos.OrderModelDto;

public interface IOrderService 
{
    public List<OrderModelDto> GetAllOrdersByPage(int page);
    public List<OrderModelDto> GetOrdersByUserId(UUID userId, int page);
    public OrderModelDto GetOrderById(UUID id);
    public boolean CreateOrder(OrderModelDto model);
    public boolean UpdateOrder(OrderModelDto model);
    public boolean DeleteOrderById(UUID id);
}
