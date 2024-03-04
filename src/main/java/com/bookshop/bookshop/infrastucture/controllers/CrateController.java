package com.bookshop.bookshop.infrastucture.controllers;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.bookshop.core.coreServices.ICrateService;
import com.bookshop.bookshop.dtos.CrateModelDto;
import com.bookshop.bookshop.dtos.CratePartModelDto;

@RestController
@RequestMapping("/crate")
public class CrateController 
{
    @Autowired
    ICrateService crateService;

    @GetMapping("/{userId}")
    // получаем книгу по id юзера
    public ResponseEntity<CrateModelDto> GetCrateByUserId(@PathVariable UUID userId) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(crateService.GetCrateById(userId));
    }

    @PutMapping("/addbook")
    // закидываем в корзину еще одну книгу
    public ResponseEntity<CrateModelDto> AddBookToCrate(@RequestBody CratePartModelDto part, UUID userId) throws InterruptedException, ExecutionException
    {
        if(crateService.GetCrateById(userId) == null)
        {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }

        if(!crateService.AddBookToCrate(part, userId))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(crateService.GetCrateById(userId));
    }

    @PutMapping("/bookcount")
    // добавляем еще одну книгу в корзине(колличество одной и той же книги)
    public ResponseEntity<String> AddBookCount(UUID partId) throws InterruptedException, ExecutionException
    {
        if(!crateService.AddBookCount(partId))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("successful");
    }


    @PutMapping("/update")
    //обновление корзины
    public ResponseEntity<CrateModelDto> UpdateCrate(@RequestBody CrateModelDto dto) throws InterruptedException, ExecutionException
    {

        if(!crateService.UpdateCrate(dto))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(dto);
    }

    // public ResponseEntity<String>  
}
