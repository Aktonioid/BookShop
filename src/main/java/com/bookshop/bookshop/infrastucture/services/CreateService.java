package com.bookshop.bookshop.infrastucture.services;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.bookshop.bookshop.core.coreRepositories.ICrateRepo;
import com.bookshop.bookshop.core.coreServices.ICrateService;
import com.bookshop.bookshop.core.mappers.CrateModelMapper;
import com.bookshop.bookshop.core.mappers.CratePartModelMapper;
import com.bookshop.bookshop.dtos.CrateModelDto;
import com.bookshop.bookshop.dtos.CratePartModelDto;

@Service
@EnableAsync
public class CreateService implements ICrateService
{

    @Autowired
    ICrateRepo crateRepo;

    @Override
    @Async
    public CompletableFuture<CrateModelDto> GetCrateById(UUID id) 
    {
        return CompletableFuture.completedFuture(CrateModelMapper.AsDto(crateRepo.GetCrateById(id)));
    }

    @Override
    @Async
    public CompletableFuture<List<CrateModelDto>> GetAllCrates() 
    {
        return CompletableFuture.completedFuture(crateRepo.GetAllCrateModels().stream()
                .map(CrateModelMapper::AsDto)
                .collect(Collectors.toList()));        
    }

    @Override
    @Async
    public CompletableFuture<Boolean> CreateCrate(CrateModelDto crateDto) 
    {
        return CompletableFuture.completedFuture(crateRepo.CreateCrate(CrateModelMapper.AsEntity(crateDto)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> AddBookToCrate(CratePartModelDto part, UUID userId) 
    {
        return CompletableFuture.completedFuture(crateRepo.AddBookToCrate(CratePartModelMapper.AsEntity(part), userId));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> AddBookCount(UUID partId) 
    {
        return CompletableFuture.completedFuture(crateRepo.AddBookCount(partId));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> UpdateCrate(CrateModelDto crateDto) 
    {
        return CompletableFuture.completedFuture(crateRepo.UpdateCrate(CrateModelMapper.AsEntity(crateDto)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> DeleteCrateByUserId(UUID userId) 
    {
        return CompletableFuture.completedFuture(crateRepo.DeleteCrateById(userId));
    }
    
}
