package com.bookshop.bookshop.core.coreServices;

import java.util.UUID;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.bookshop.bookshop.dtos.UserModelDto;

public interface IUserService 
{
    public UserModelDto GetUserById(UUID id);
    public UserModelDto GetUserByEmail(String email);
    public UserModelDto GetUserByUserName(String username);
    public UserDetailsService UserDetailsService();
    public boolean CreateUser(UserModelDto model);
    public boolean UpdateUser(UserModelDto model);
    public boolean DeleteUserById(UUID id);
    public UserModelDto GenerateNewPassword(String username);
    public List<UserModelDto> GetUsersByPage(int page);

}
