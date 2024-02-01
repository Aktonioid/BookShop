package com.bookshop.bookshop.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreModelDto 
{
    private UUID id;
    
    private String genreName;

    public GenreModelDto(UUID id)
    {
        this.id =id;
    }

}
