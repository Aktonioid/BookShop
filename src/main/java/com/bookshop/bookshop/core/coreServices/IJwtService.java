package com.bookshop.bookshop.core.coreServices;

import com.bookshop.bookshop.core.models.UserModel;

public interface IJwtService 
{
    public String ExtractUserName(String token);
    public String ExtractEmail(String token);
    public String ExtractId(String token);    
    public String GenerateToken(UserModel user);
    public boolean IsTokenValid(String token, UserModel user);
}
