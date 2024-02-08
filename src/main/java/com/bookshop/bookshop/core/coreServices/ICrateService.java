package com.bookshop.bookshop.core.coreServices;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.bookshop.bookshop.dtos.CrateModelDto;
import com.bookshop.bookshop.dtos.CratePartModelDto;

public interface ICrateService 
{
    public CompletableFuture<CrateModelDto> GetCrateByUserId(UUID id);    
    public CompletableFuture<List<CrateModelDto>> GetAllCrates();
    public CompletableFuture<Boolean> CreateCrate(CrateModelDto crateDto);
    public CompletableFuture<Boolean> AddBookToCrate(CratePartModelDto part, UUID userId); // сча посмотрю мб понадобится
    public CompletableFuture<Boolean> AddBookCount(UUID partId);
    public CompletableFuture<Boolean> UpdateCrate(CrateModelDto crateDto);
    public CompletableFuture<Boolean> DeleteCrateByUserId(UUID userId);
}
