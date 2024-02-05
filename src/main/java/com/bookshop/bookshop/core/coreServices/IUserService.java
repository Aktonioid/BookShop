package com.bookshop.bookshop.core.coreServices;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.bookshop.bookshop.dtos.UserModelDto;

public interface IUserService 
{
    public CompletableFuture<UserModelDto> GetUserById(UUID id);
    public CompletableFuture<UserModelDto> GetUserByEmail(String email);
    public CompletableFuture<UserModelDto> GetUserByUserName(String username);
    public UserDetailsService UserDetailsService();
    public CompletableFuture<Boolean> CreateUser(UserModelDto model);
    public CompletableFuture<Boolean> UpdateUser(UserModelDto model);
    public CompletableFuture<Boolean> DeleteUserById(UUID id);

}
