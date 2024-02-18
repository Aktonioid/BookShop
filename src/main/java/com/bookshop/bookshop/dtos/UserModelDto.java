package com.bookshop.bookshop.dtos;

import java.util.UUID;

import com.bookshop.bookshop.core.models.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModelDto 
{
    UUID id;    

    private String username;
    
    private String name;

    private String userSurname;

    private String email;
    private String password;

    private String veridficationCode;

    private Role role;
    private boolean isEmailVerificated;
}
