package com.bookshop.bookshop.core.coreServices;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.bookshop.bookshop.dtos.OrderModelDto;

public interface IOrderService 
{
    public CompletableFuture<List<OrderModelDto>> GetAllOrdersByPage(int page);
    public CompletableFuture<List<OrderModelDto>> GetOrdersByUserId(UUID userId, int page);
    public CompletableFuture<OrderModelDto> GetOrderById(UUID id);
    public CompletableFuture<Boolean> CreateOrder(OrderModelDto model);
    public CompletableFuture<Boolean> UpdateOrder(OrderModelDto model);
    public CompletableFuture<Boolean> DeleteOrderById(UUID id);
}
