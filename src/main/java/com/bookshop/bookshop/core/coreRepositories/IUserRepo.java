package com.bookshop.bookshop.core.coreRepositories;

import java.util.List;
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
    public boolean IsUserExhistsByEmail(String email);
    public boolean IsUserExhistsByUsername(String username);
    public List<UserModel> GetAllUsersByPage(int page); // для админ панели
}
