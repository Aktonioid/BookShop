package com.bookshop.bookshop.infrastucture.services;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<UserModelDto> GetUserById(UUID id) 
    {
        return CompletableFuture.completedFuture(UserModelMapper.AsDto(userRepo.UserById(id)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> CreateUser(UserModelDto model) 
    {
        return CompletableFuture.completedFuture(userRepo.CreateUser(UserModelMapper.AsEntity(model)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> UpdateUser(UserModelDto model) // хз как правильно сделать это
    {
        throw new UnsupportedOperationException("Unimplemented method 'UpdateUser'");
        // return CompletableFuture.completedFuture(userRepo.UpdateUser(UserModelMapper.AsEntity(model)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> DeleteUserById(UUID id) 
    {
        return CompletableFuture.completedFuture(userRepo.DeleteUserById(id));
    }

    @Override
    @Async
    public CompletableFuture<UserModelDto> GetUserByEmail(String email) 
    {
        return CompletableFuture.completedFuture(UserModelMapper.AsDto(userRepo.UserByEmail(email)));
    }

    @Override
    @Async
    public CompletableFuture<UserModelDto> GetUserByUserName(String username) 
    {
        return CompletableFuture.completedFuture(UserModelMapper.AsDto(userRepo.UserByUsername(username)));
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

    
}
