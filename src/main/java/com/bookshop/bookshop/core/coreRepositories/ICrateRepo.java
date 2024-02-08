package com.bookshop.bookshop.core.coreRepositories;

import java.util.List;
import java.util.UUID;

import com.bookshop.bookshop.core.models.CrateModel;
import com.bookshop.bookshop.core.models.CratePartModel;

public interface ICrateRepo 
{
    public CrateModel GetCrateByUserId(UUID id);
    public List<CrateModel> GetAllCrateModels(); //Удалю потом. Просто для тестов сдеал
    public boolean CreateCrate(CrateModel crateModel);
    public boolean AddBookToCrate(CratePartModel part, UUID userId); // сча посмотрю мб понадобится
    public boolean AddBookCount(UUID partId);
    public boolean UpdateCrate(CrateModel crateModel);
    public boolean DeleteCrateByUserId(UUID userId); 
}
