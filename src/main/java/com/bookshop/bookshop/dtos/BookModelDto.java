package com.bookshop.bookshop.dtos;

import java.util.Set;
import java.util.UUID;

import com.bookshop.bookshop.core.models.GenreModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookModelDto 
{
    private UUID id;
    
    private String bookName; //Название книги

    private String authorName; // автор
    private String publisher; //издательство
    private int price; // стоимость
    private String isbn; //Уникальный идентификатор книги
    
    private Set<GenreModel> genres; //Жанры книги
    
    private String description; // описание книги
    private String pictureUrl; // ссылка на url книги
    private short leftovers; // остатки на складе 
}
