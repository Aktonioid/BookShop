package com.bookshop.bookshop.infrastucture.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookshop.bookshop.core.coreRepositories.IUserRepo;
import com.bookshop.bookshop.core.coreServices.IPaymentService;
import com.bookshop.bookshop.core.models.UserModel;

@Service
//Это !!!симуляция сервиса оплаты не более!!!
public class PaymentService implements IPaymentService 
{

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    IUserRepo userRepo;

    @Override
    @Async
    public boolean CheckPayment(String paymentToken, String tokenToCompare) 
    {
        boolean isTrue = false;
        if(encoder.matches(paymentToken, tokenToCompare))
        {
            isTrue = true;
        }

        return isTrue;
    }

    @Async
    //для создания симуляции токена просто из инфы пользователя я беру инфу для создания(id, username, email)
    public String CreateToken(UUID userId)
    {
        UserModel userModel = userRepo.UserById(userId);
        StringBuffer sb = new StringBuffer();
        sb.append(userModel.getId().toString());
        sb.append(userModel.getUsername());
        sb.append(userModel.getEmail());
        sb.append(true);
        return encoder.encode(sb.toString());
    }
    
}
