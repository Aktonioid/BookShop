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
    public List<BookModel> FindBooksByAuthor(String authorName, int page); // поиск по жанрам
    public List<BookModel> FindBooksByName(String bookName, int page); // поиск по названию книги
    public long GetMaxPageForAll(); // Получаем номер последней страницы для запроса ,tp abkmhjd
    public long GetMaxPageForGenresSearch(List<GenreModel> genres);// Получаем номер последней страницы для поиска книг по жанрам 
    public long GetMaxPageForSearchByAuthor(String authorName);// Получаем номер последней страницы для 
                                                                //запроса поиска книг по автору
    public long GetMaxPageForSearchByBookName(String bookName);// Получаем номер последней страницы для 
                                                                //запроса поиска книг по названию книги
}
