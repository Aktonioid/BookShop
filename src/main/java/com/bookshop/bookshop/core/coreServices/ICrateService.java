package com.bookshop.bookshop.core.coreServices;

import java.util.List;
import java.util.UUID;

import com.bookshop.bookshop.dtos.CrateModelDto;
import com.bookshop.bookshop.dtos.CratePartModelDto;

public interface ICrateService 
{
    public CrateModelDto GetCrateById(UUID id);    
    public List<CrateModelDto> GetAllCrates(); // для проверок, кину в админку или удалю 
    public boolean CreateCrate(CrateModelDto crateDto); // создаем вместе с юзером
    public boolean AddBookToCrate(CratePartModelDto part, UUID userId); // сча посмотрю мб понадобится
    public boolean AddBookCount(UUID partId);
    public boolean UpdateCrate(CrateModelDto crateDto);
    public boolean DeleteCrateByUserId(UUID userId);// удаляем вместе с юзером 
}
