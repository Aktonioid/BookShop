package com.bookshop.bookshop.infrastucture.services;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.bookshop.bookshop.core.coreRepositories.IOrderRepo;
import com.bookshop.bookshop.core.coreServices.IOrderService;
import com.bookshop.bookshop.core.mappers.OrderModelMapper;
import com.bookshop.bookshop.dtos.OrderModelDto;

@Service
@EnableAsync
public class OrderService implements IOrderService
{

    @Autowired
    IOrderRepo orderRepo;

    @Override
    @Async
    public CompletableFuture<List<OrderModelDto>> GetAllOrdersByPage(int page) 
    {
        return CompletableFuture.completedFuture(
            Collections.synchronizedList
                (
                    orderRepo.GetAllOrdersByPage(page)
                        .stream()
                        .map(OrderModelMapper::AsDto)
                        .collect(Collectors.toList())
                )
            );
    }

    @Override
    @Async
    public CompletableFuture<List<OrderModelDto>> GetOrdersByUserId(UUID userId, int page) 
    {
        return CompletableFuture.completedFuture
        (
            Collections.synchronizedList(
                orderRepo.GetOrdersByUserId(userId, page)
                .stream()
                .map(OrderModelMapper::AsDto)
                .collect(Collectors.toList()))
        );
    }

    @Override
    @Async
    public CompletableFuture<OrderModelDto> GetOrderById(UUID id) 
    {
        return CompletableFuture.completedFuture(OrderModelMapper.AsDto(orderRepo.GetOrderById(id)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> CreateOrder(OrderModelDto model) 
    {
        return CompletableFuture.completedFuture(orderRepo.CreateOrder(OrderModelMapper.AsEntity(model)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> UpdateOrder(OrderModelDto model) 
    {
        return CompletableFuture.completedFuture(orderRepo.UpdateOrder(OrderModelMapper.AsEntity(model)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> DeleteOrderById(UUID id) 
    {
        return CompletableFuture.completedFuture(orderRepo.DeleteOrderById(id));
    }

}
