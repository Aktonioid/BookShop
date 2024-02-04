package com.bookshop.bookshop.dtos;

import java.util.List;
import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookModelDto 
{
    private UUID id;
    
    private String bookName; //Название книги

    private String authorName; // автор
    private String publisher; //издательство
    private int price; // стоимость
    private String isbn; //Уникальный идентификатор книги
    
    private List<GenreModelDto> genres; //Жанры книги
    
    private String description; // описание книги
    private String pictureUrl; // ссылка на url книги
    private short leftovers; // остатки на складе 
}
