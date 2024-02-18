package com.bookshop.bookshop.core.coreRepositories;

import java.util.UUID;

import com.bookshop.bookshop.core.models.RefreshTokenModel;

// для работы с рефреш токенами в бд
public interface IRefreshTokenRepo 
{
    public RefreshTokenModel GetTokenById(UUID tokenId); // получить токен из бд
    public boolean CreateToken(RefreshTokenModel token); // создание нового токена
    public boolean DeleteTokenById(UUID tokenId); // удаление токена из бд
     
}
