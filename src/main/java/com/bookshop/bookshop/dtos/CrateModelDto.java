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
public class CrateModelDto 
{
    private UUID id;

    private UUID userId;

    List<CratePartModelDto> books;
}
