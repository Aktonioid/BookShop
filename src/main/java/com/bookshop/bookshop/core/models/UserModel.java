package com.bookshop.bookshop.core.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel 
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;    

    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_surname")
    private String userSurname;

    private String email;
    private String password;
    
}
