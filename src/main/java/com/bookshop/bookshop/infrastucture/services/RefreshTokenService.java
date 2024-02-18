package com.bookshop.bookshop.infrastucture.services;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<RefreshTokenModelDto> GetTokenById(UUID tokenId) 
    {
        RefreshTokenModel model = refreshTokenRepo.GetTokenById(tokenId);

        if(model == null)
        {
            return null;
        }
        return CompletableFuture.completedFuture(RefreshTokenModelMapper.AsDto(model));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> CreateToken(RefreshTokenModelDto token) 
    {
        System.out.println("что-то");
        return CompletableFuture.completedFuture(refreshTokenRepo.CreateToken(RefreshTokenModelMapper.AsEntity(token)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> DeleteTokenById(UUID tokenId) 
    {
        return CompletableFuture.completedFuture(refreshTokenRepo.DeleteTokenById(tokenId));
    }

    
}
