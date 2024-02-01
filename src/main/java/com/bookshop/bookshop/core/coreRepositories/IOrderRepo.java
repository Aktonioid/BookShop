package com.bookshop.bookshop.core.coreRepositories;

import java.util.ArrayList;
import java.util.UUID;

import com.bookshop.bookshop.core.models.OrderModel;

public interface IOrderRepo 
{
    public ArrayList<OrderModel> GetAllOrders();
    public OrderModel GetOrderById(UUID id);
    public boolean CreateOrder(OrderModel model);
    public boolean UpdateOrder(OrderModel model);
    public boolean DeleteOrderById(UUID id);
    
}
