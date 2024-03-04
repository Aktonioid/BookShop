package com.bookshop.bookshop.infrastucture.services;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
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
    public List<OrderModelDto> GetAllOrdersByPage(int page) 
    {
        return Collections.synchronizedList
                (
                    orderRepo.GetAllOrdersByPage(page)
                        .stream()
                        .map(OrderModelMapper::AsDto)
                        .collect(Collectors.toList())
                );
    }

    @Override
    @Async
    public List<OrderModelDto> GetOrdersByUserId(UUID userId, int page) 
    {
        return Collections.synchronizedList(
                orderRepo.GetOrdersByUserId(userId, page)
                .stream()
                .map(OrderModelMapper::AsDto)
                .collect(Collectors.toList()))
        ;
    }

    @Override
    @Async
    public OrderModelDto GetOrderById(UUID id) 
    {
        return OrderModelMapper.AsDto(orderRepo.GetOrderById(id));
    }

    @Override
    @Async
    public boolean CreateOrder(OrderModelDto model) 
    {
        return orderRepo.CreateOrder(OrderModelMapper.AsEntity(model));
    }

    @Override
    @Async
    public boolean UpdateOrder(OrderModelDto model) 
    {
        return orderRepo.UpdateOrder(OrderModelMapper.AsEntity(model));
    }

    @Override
    @Async
    public boolean DeleteOrderById(UUID id) 
    {
        return orderRepo.DeleteOrderById(id);
    }

}
