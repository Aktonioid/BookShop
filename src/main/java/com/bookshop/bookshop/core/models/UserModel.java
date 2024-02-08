package com.bookshop.bookshop.core.models;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class UserModel implements UserDetails
{
    @Id // генерируем сами при создании
    UUID id;    

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "user_surname")
    private String userSurname;

    private String email;
    private String password;

    @Column(name = "role", nullable = false)
    private Role role;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() 
    {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public boolean isAccountNonExpired() 
    {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() 
    {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() 
    {
        return true;
    }
    @Override
    public boolean isEnabled() 
    {
        return true;
    }
   
    public UserModel(UUID id)
    {
        this.id = id;
    }
}
