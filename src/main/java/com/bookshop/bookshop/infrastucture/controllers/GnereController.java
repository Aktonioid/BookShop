package com.bookshop.bookshop.infrastucture.controllers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.bookshop.core.coreServices.IGenreService;
import com.bookshop.bookshop.dtos.GenreModelDto;

@RestController
@RequestMapping("/genres")
public class GnereController 
{
    @Autowired
    IGenreService genreService;

    // получаем все жанры
    @GetMapping("/")
    public ResponseEntity<List<GenreModelDto>> GetAllGenres() throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(genreService.GetAllGenres().get());
    }

    // получаем жанр по id
    @GetMapping("/{genreId}")
    public ResponseEntity<GenreModelDto> GetGenreById(@PathVariable(name = "genreId") UUID genreId)
    {
        GenreModelDto modelDto = null;

        try {
            modelDto = genreService.GetGenreById(genreId).get();
        } 
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }

        if(modelDto == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(modelDto);
    } 
}
