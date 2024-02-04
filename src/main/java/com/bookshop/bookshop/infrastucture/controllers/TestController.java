package com.bookshop.bookshop.infrastucture.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.bookshop.core.models.GenreModel;
import com.bookshop.bookshop.infrastucture.repository.sql.GnereRepo;

@Controller
@RequestMapping("test")
public class TestController 
{
    @Autowired
    GnereRepo repo;

    @GetMapping("/")
    public ResponseEntity<List<GenreModel>> GetAll()
    {
        return ResponseEntity.ok(repo.GetAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreModel> GetById(@PathVariable(name = "id") UUID id)
    {
        return ResponseEntity.ok(repo.GetGenreById(id));
    }

    @PostMapping("/")
    public ResponseEntity<String> Create(@RequestBody GenreModel model)
    {
        repo.CreateModel(model);
        return ResponseEntity.ok("");
    }
}
