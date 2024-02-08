package com.bookshop.bookshop.core.models;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "crate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrateModel 
{
    private UUID id;     
    
    @JoinColumn(name = "user_id")
    private UserModel userId;

    @OneToMany
    @JoinColumn(name = "part_id")
    private Set<CratePartModel> books;

    public CrateModel(UUID id)
    {
        this.id = id;
    }
}
