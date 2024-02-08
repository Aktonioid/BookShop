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
public class CratePartModelDto 
{
    private UUID id;
    private BookModelDto book;
    private int bookCount;
    private UUID crateId;
}
