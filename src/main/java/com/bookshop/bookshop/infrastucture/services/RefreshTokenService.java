package com.bookshop.bookshop.infrastucture.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.bookshop.bookshop.core.coreRepositories.IRefreshTokenRepo;
import com.bookshop.bookshop.core.coreServices.IRefreshTokenService;
import com.bookshop.bookshop.core.mappers.RefreshTokenModelMapper;
import com.bookshop.bookshop.core.models.RefreshTokenModel;
import com.bookshop.bookshop.dtos.RefreshTokenModelDto;

@Service
@EnableAsync
public class RefreshTokenService implements IRefreshTokenService
{
    @Autowired
    IRefreshTokenRepo refreshTokenRepo;

    @Override
    @Async
    public RefreshTokenModelDto GetTokenById(UUID tokenId) 
    {
        RefreshTokenModel model = refreshTokenRepo.GetTokenById(tokenId);

        if(model == null)
        {
            return null;
        }
        return RefreshTokenModelMapper.AsDto(model);
    }

    @Override
    @Async
    public boolean CreateToken(RefreshTokenModelDto token) 
    {
        return refreshTokenRepo.CreateToken(RefreshTokenModelMapper.AsEntity(token));
    }

    @Override
    @Async
    public boolean DeleteTokenById(UUID tokenId) 
    {
        return refreshTokenRepo.DeleteTokenById(tokenId);
    }

    
}
