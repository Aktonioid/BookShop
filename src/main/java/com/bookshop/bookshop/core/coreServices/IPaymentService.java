package com.bookshop.bookshop.core.coreServices;


public interface IPaymentService 
{                                                   // приходящий токен   // токен для симуляции проверки
    public boolean CheckPayment(String paymentToken, String tokenToCompare); // Если честно, то я не совсем уверен как это происходит
 }
