package com.bookshop.bookshop.core.coreServices;

import java.util.UUID;

import com.bookshop.bookshop.dtos.RefreshTokenModelDto;

public interface IRefreshTokenService 
{
    public RefreshTokenModelDto GetTokenById(UUID tokenId);
    public boolean CreateToken(RefreshTokenModelDto token);
    public boolean DeleteTokenById(UUID tokenId);
}
