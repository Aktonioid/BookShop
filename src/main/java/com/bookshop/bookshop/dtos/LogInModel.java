package com.bookshop.bookshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogInModel 
{
    private String username; // мб поменяю на email 
    private String password;     
}
