package com.bookshop.bookshop.core.coreServices;

import java.util.ArrayList;
import java.util.UUID;

import com.bookshop.bookshop.dtos.OrderModelDto;

public interface IOrderService 
{
    public ArrayList<OrderModelDto> GetAllOrders();
    public OrderModelDto GetOrderById(UUID id);
    public boolean CreateOrder(OrderModelDto model);
    public boolean UpdateOrder(OrderModelDto model);
    public boolean DeleteOrderById(UUID id);
}
