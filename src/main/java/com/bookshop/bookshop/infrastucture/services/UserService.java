package com.bookshop.bookshop.infrastucture.services;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.bookshop.bookshop.core.coreRepositories.IUserRepo;
import com.bookshop.bookshop.core.coreServices.IUserService;
import com.bookshop.bookshop.core.mappers.UserModelMapper;
import com.bookshop.bookshop.core.models.UserModel;
import com.bookshop.bookshop.dtos.UserModelDto;



@EnableAsync(proxyTargetClass = true)
@Service
@EnableCaching
public class UserService implements IUserService 
{
    @Autowired
    IUserRepo userRepo;

    @Override
    @Async
    public UserModelDto GetUserById(UUID id) 
    {
        return UserModelMapper.AsDto(userRepo.UserById(id));
    }

    @Override
    @Async
    public boolean CreateUser(UserModelDto model) 
    {
        return userRepo.CreateUser(UserModelMapper.AsEntity(model));
    }

    @Override
    @Async
    public boolean UpdateUser(UserModelDto model) // хз как правильно сделать это
    {
        return userRepo.UpdateUser(UserModelMapper.AsEntity(model));
    }

    @Override
    @Async
    public boolean DeleteUserById(UUID id) 
    {
        return userRepo.DeleteUserById(id);
    }

    @Override
    @Async
    public UserModelDto GetUserByEmail(String email) 
    {
        UserModel model = userRepo.UserByEmail(email);

        if(model == null)
        {
            return null;
        }

        return UserModelMapper.AsDto(model);
    }

    @Override
    @Async
    public UserModelDto GetUserByUserName(String username) 
    {   
        UserModel model = userRepo.UserByUsername(username);

        if(model == null)
        {
            return null;
        }

        return UserModelMapper.AsDto(model);
    }

    @Override
    public UserDetailsService UserDetailsService() 
    {
        return this::ForUserDetailService;
    }

    private UserModel ForUserDetailService(String username)
    {
        return userRepo.UserByUsername(username);
    }


    @Override
    @Async
    // возвращает string только для тестов, чтоб я пока не прикрутил отправку пароля на почту чтоб я просто пароль знал
    public UserModelDto GenerateNewPassword(String username) 
    {
        UserModel userModel = userRepo.UserByUsername(username);

        if(userModel == null)
        {
            return null;
        }

        String newPassword = RandomStringUtils.randomAlphanumeric(20);

        userModel.setPassword(newPassword);

        return UserModelMapper.AsDto(userModel);
    }

    @Override
    public List<UserModelDto> GetUsersByPage(int page) 
    {
        List<UserModelDto> users = Collections.synchronizedList
                                (userRepo.GetAllUsersByPage(page)
                                .stream()
                                .map(UserModelMapper::AsDto)
                                .collect(Collectors.toList()));

        return users;
    }

    
}
