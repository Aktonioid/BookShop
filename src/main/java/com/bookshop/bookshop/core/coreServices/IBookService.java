package com.bookshop.bookshop.core.coreServices;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.bookshop.bookshop.dtos.BookModelDto;
import com.bookshop.bookshop.dtos.GenreModelDto;

public interface IBookService 
{
    public List<BookModelDto> GetAllModelsByPage(int page);   
    public BookModelDto GetBookModelById(UUID id);
    public boolean CreateModel(BookModelDto model);
    public boolean DeleteModelById(UUID id);
    public boolean UpdateModel(BookModelDto model);
    public List<BookModelDto> GetBooksByGenres(List<GenreModelDto> genres, int page);// нахождение книг по жанрам 
    public List<BookModelDto> FindBooksByAuthor(String authorName, int page);// нахождение книг по авторам
    public List<BookModelDto> FindBooksByName(String bookName, int page);// нахождение книг по именам
    public String SaveBookCover(MultipartFile model); // сохранение обложки файла
    public long GetMaxPageForAll();// получениие максимальной страницы для всех книг
    public long GetMaxPageForGenresSearch(List<GenreModelDto> genres);// получениие максимальной 
                                                                                        //страницы при поиске по жанрам
    public long GetMaxPageForSearchByAuthor(String authorName);// получениие максимальной 
                                                                                //страницы пр и поиске по автору
    public long GetMaxPageForSearchByBookName(String bookName);// получениие максимальной 
                                                                                //страницы пр и поиске по названию

}
