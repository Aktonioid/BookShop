package com.bookshop.bookshop.core.coreRepositories;

import java.util.UUID;
import java.util.List;

import com.bookshop.bookshop.core.models.BookModel;
import com.bookshop.bookshop.core.models.GenreModel;

public interface IBookRepo 
{
    
    public List<BookModel> GetAllBooksByPage(int page);   
    public BookModel GetBookBookById(UUID id);
    public boolean CreateBook(BookModel model);
    public boolean DeleteBookById(UUID id);
    public boolean UpdateBook(BookModel model);
    public List<BookModel> GetBooksByGenres(List<GenreModel> genres, int page);
}
