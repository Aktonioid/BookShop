package com.bookshop.bookshop.core.coreServices;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.bookshop.bookshop.dtos.CrateModelDto;
import com.bookshop.bookshop.dtos.CratePartModelDto;

public interface ICrateService 
{
    public CompletableFuture<CrateModelDto> GetCrateById(UUID id);    
    public CompletableFuture<List<CrateModelDto>> GetAllCrates(); // для проверок, кину в админку или удалю 
    public CompletableFuture<Boolean> CreateCrate(CrateModelDto crateDto); // создаем вместе с юзером
    public CompletableFuture<Boolean> AddBookToCrate(CratePartModelDto part, UUID userId); // сча посмотрю мб понадобится
    public CompletableFuture<Boolean> AddBookCount(UUID partId);
    public CompletableFuture<Boolean> UpdateCrate(CrateModelDto crateDto);
    public CompletableFuture<Boolean> DeleteCrateByUserId(UUID userId);// удаляем вместе с юзером 
}
