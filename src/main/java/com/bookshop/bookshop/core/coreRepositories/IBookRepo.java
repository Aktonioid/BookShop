package com.bookshop.bookshop.core.coreRepositories;

import java.util.ArrayList;
import java.util.UUID;

import com.bookshop.bookshop.core.models.BookModel;
import com.bookshop.bookshop.core.models.GenreModel;

public interface IBookRepo 
{
    public ArrayList<BookModel> GetAllBooksByPage(int page);   
    public BookModel GetBookBookById(UUID id);
    public boolean CreateBook(BookModel model);
    public boolean DeleteBookById(UUID id);
    public boolean UpdateBook(BookModel model);
    public boolean GetBooksByGenres(ArrayList<GenreModel> genres, int page);
    
}
