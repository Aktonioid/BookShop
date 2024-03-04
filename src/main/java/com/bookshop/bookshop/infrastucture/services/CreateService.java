package com.bookshop.bookshop.infrastucture.services;

import java.util.List;
import java.util.UUID;
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
    public CrateModelDto GetCrateById(UUID id) 
    {
        return CrateModelMapper.AsDto(crateRepo.GetCrateById(id));
    }

    @Override
    @Async
    public List<CrateModelDto> GetAllCrates() 
    {
        return crateRepo.GetAllCrateModels().stream()
                .map(CrateModelMapper::AsDto)
                .collect(Collectors.toList());        
    }

    @Override
    @Async
    public boolean CreateCrate(CrateModelDto crateDto) 
    {
        return crateRepo.CreateCrate(CrateModelMapper.AsEntity(crateDto));
    }

    @Override
    @Async
    public boolean AddBookToCrate(CratePartModelDto part, UUID userId) 
    {
        return crateRepo.AddBookToCrate(CratePartModelMapper.AsEntity(part), userId);
    }

    @Override
    @Async
    public boolean AddBookCount(UUID partId) 
    {
        return crateRepo.AddBookCount(partId);
    }

    @Override
    @Async
    public boolean UpdateCrate(CrateModelDto crateDto) 
    {
        return crateRepo.UpdateCrate(CrateModelMapper.AsEntity(crateDto));
    }

    @Override
    @Async
    public boolean DeleteCrateByUserId(UUID userId) 
    {
        return crateRepo.DeleteCrateById(userId);
    }
    
}
