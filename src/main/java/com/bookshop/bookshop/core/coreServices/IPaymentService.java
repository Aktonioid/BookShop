package com.bookshop.bookshop.core.coreServices;

import java.util.concurrent.CompletableFuture;

public interface IPaymentService 
{                                                   // приходящий токен   // токен для симуляции проверки
    public CompletableFuture<Boolean> CheckPayment(String paymentToken, String tokenToCompare); // Если честно, то я не совсем уверен как это происходит
 }
