package com.bookshop.bookshop.core.coreRepositories;

import java.util.List;
import java.util.UUID;

import com.bookshop.bookshop.core.models.OrderModel;

public interface IOrderRepo 
{
    public List<OrderModel> GetAllOrdersByPage(int page); // для тестов
    public OrderModel GetOrderById(UUID id);
    public List<OrderModel> GetOrdersByUserId(UUID userId, int page);
    public boolean CreateOrder(OrderModel model);
    public boolean UpdateOrder(OrderModel model);
    public boolean DeleteOrderById(UUID id);
    
}
