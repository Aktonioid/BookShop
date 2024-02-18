package com.bookshop.bookshop.core.coreServices;

import java.util.UUID;

import com.bookshop.bookshop.core.models.UserModel;

public interface IJwtService 
{
    public String ExtractUserName(String token);
    public String ExtractEmail(String token);
    public String ExtractId(String token);    
    public String ExtractTokenId(String token);
    public String GenerateAccessToken(UserModel user, UUID tokenId);
    public String GenerateRefreshToken(UserModel user, UUID tokenId);
    public boolean IsTokenValid(String token, UserModel user);
    public boolean IsTokenValidNoTime(String token);

}
