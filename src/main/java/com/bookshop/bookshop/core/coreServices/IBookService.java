package com.bookshop.bookshop.core.coreServices;

import java.util.ArrayList;
import java.util.UUID;

import com.bookshop.bookshop.dtos.BookModelDto;
import com.bookshop.bookshop.dtos.GenreModelDto;

public interface IBookService 
{
    public ArrayList<BookModelDto> GetAllModelsByPage(int page);   
    public BookModelDto GetBookModelById(UUID id);
    public boolean CreateModel(BookModelDto model);
    public boolean DeleteModelById(UUID id);
    public boolean UpdateModel(BookModelDto model);
    public boolean GetBooksByGenres(ArrayList<GenreModelDto> genres, int page);
}
