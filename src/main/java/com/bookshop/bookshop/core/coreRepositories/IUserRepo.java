package com.bookshop.bookshop.core.coreRepositories;

import java.util.UUID;

import com.bookshop.bookshop.core.models.UserModel;

public interface IUserRepo 
{
    public UserModel UserById(UUID id);
    public UserModel UserByEmail(String email);
    public UserModel UserByUsername(String username);
    public boolean CreateUser(UserModel userModel);
    public boolean DeleteUserById(UUID id);
    public boolean UpdateUser(UserModel userModel);
}
