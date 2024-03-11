package com.bookshop.bookshop.infrastucture.controllers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.bookshop.core.coreServices.IOrderService;
import com.bookshop.bookshop.dtos.OrderModelDto;
import com.bookshop.bookshop.infrastucture.services.PaymentService;

@RestController
@RequestMapping("/orders")
public class OrderController 
{
    @Autowired
    IOrderService orderService;
    @Autowired
    PaymentService paymentService;

    // постраничное получение всех заказов по пользователю 
    @GetMapping("/{page}")
    public ResponseEntity<List<OrderModelDto>> GetUserOrders(UUID userId, @PathVariable("page") int page) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(orderService.GetOrdersByUserId(userId, page).get());
    }

    // получение заказа по id заказа
    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderModelDto> GetOrderById(@PathVariable("orderId") UUID orderId) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(orderService.GetOrderById(orderId).get());
    }

    // создание нового заказа с подтверждением оплаты(Тут симуляция)
    @PostMapping("/")
    public ResponseEntity<String> CreateNewOrder(String paymentToken, UUID userId,
                                        @RequestBody OrderModelDto order) throws InterruptedException, ExecutionException
    {
        String tokenToCompare = paymentService.CreateToken(userId).get();   
        boolean isPaymentTrue = paymentService.CheckPayment(paymentToken, tokenToCompare).get(); //проверка на то что токены одинаковы
        
        if(!isPaymentTrue)
        {
            return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
        }

        if(!orderService.CreateOrder(order).get())
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return ResponseEntity.ok("Order created");
    }

    
}
