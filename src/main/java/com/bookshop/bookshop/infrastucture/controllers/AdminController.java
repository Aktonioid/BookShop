package com.bookshop.bookshop.infrastucture.controllers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookshop.bookshop.core.coreServices.IBookService;
import com.bookshop.bookshop.core.coreServices.IGenreService;
import com.bookshop.bookshop.core.coreServices.IOrderService;
import com.bookshop.bookshop.core.coreServices.IUserService;
import com.bookshop.bookshop.dtos.BookModelDto;
import com.bookshop.bookshop.dtos.GenreModelDto;
import com.bookshop.bookshop.dtos.OrderModelDto;
import com.bookshop.bookshop.dtos.UserModelDto;


@RestController
@RequestMapping("/admin")
public class AdminController 
{
    @Autowired
    IBookService bookService;
    @Autowired
    IGenreService genreService;
    @Autowired
    IUserService userService;
    @Autowired
    IOrderService orderService;

    //
    // работа с книгами
    //

    // создание книги
    @PostMapping("/books")
    public ResponseEntity<String> CreateBook(@RequestPart(name = "book", required = true) BookModelDto model,
                                             @RequestPart(value = "file", required = false) MultipartFile file
                                            ) throws InterruptedException, ExecutionException
    {
        String bookCover = "";

        if(file != null)// Проверка на то что есть что сохранять
        {
            bookCover = bookService.SaveBookCover(file).get();
        }

        if(!bookCover.equals("")) // проверяем было ли что сохранять
        {
            model.setPictureUrl(bookCover);
        }

        boolean isBookCreate = bookService.CreateModel(model).get();

        if(!isBookCreate)
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return ResponseEntity.ok("Created");
    }

    @PutMapping("/books")
    // обновить информацию по книге
    public ResponseEntity<String> UpdateBook(@RequestPart(name = "book", required = true) BookModelDto model,
                                            @RequestPart(name = "cover", required = false) MultipartFile file
                                            ) throws InterruptedException, ExecutionException
    {
        // проверка на то что такая модель вообще есть
        if(bookService.GetBookModelById(model.getId()).get() == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String bookCover = "";
        if(file != null) // Проверка на то что есть что сохранять
        {
            bookCover = bookService.SaveBookCover(file).get();
        }
        
        if(!bookCover.equals("")) // проверяем было ли что сохранять
        {
            model.setPictureUrl(bookCover);
        }

        if(!bookService.UpdateModel(model).get())
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return ResponseEntity.ok("updated");
    }

    //удаление книги
    @DeleteMapping("/books")
    public ResponseEntity<String> DeleteBookById(UUID bookId) throws InterruptedException, ExecutionException
    {
        if(bookService.GetBookModelById(bookId).get() == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);    
        }
        
        if(!bookService.DeleteModelById(bookId).get())
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return ResponseEntity.ok("deleted");
    }

    //
    //Работа с жанрами
    //

    // создание жанра
    @PostMapping("/genres")
    public ResponseEntity<String> CreateGenre(@RequestBody GenreModelDto genreModel) throws InterruptedException, ExecutionException
    {
        System.out.println(genreModel.getName());

        if(genreModel.getName() == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // проаерка на то существует ли жанр с таким именем
        if(genreService.IsGenreExhistsByName(genreModel.getName()).get())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        boolean isGenreCreated = genreService.CreateModel(genreModel).get();

        if(!isGenreCreated)
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return ResponseEntity.ok("successful");
    }

    //удаление жанра
    @DeleteMapping("/genres")
    public ResponseEntity<String> DeleteGenreById(UUID genreId) throws InterruptedException, ExecutionException
    {
        boolean isGenreDeleted = genreService.DeleteModelById(genreId).get();

        if(!isGenreDeleted)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("удалено");
    }

    //
    // Работа с пользователями
    //

    //Принудительное удаление пользователя
    @DeleteMapping("/users")
    public ResponseEntity<String> DeleteUserById(UUID userId) throws InterruptedException, ExecutionException
    {

        if(userService.GetUserById(userId).get() == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean isUserDeleted = userService.DeleteUserById(userId).get();

        if(!isUserDeleted)
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return ResponseEntity.ok("");
    }

    // получение списка всех юзеров
    @GetMapping("/users/{page}")
    public ResponseEntity<List<UserModelDto>> GetUsersByPage(@PathVariable("page") int page) throws InterruptedException, ExecutionException
    {
        List<UserModelDto> users = userService.GetUsersByPage(page).get();

        return ResponseEntity.ok(users);
    }

    //
    //Работа с заказами
    //

    //получение всех заказов всех юзеров
    @GetMapping("orders/{page}")
    public ResponseEntity<List<OrderModelDto>> GetAllOrders(@PathVariable("page") int page) throws InterruptedException, ExecutionException
    {
        List<OrderModelDto> orders = orderService.GetAllOrdersByPage(page).get();

        return ResponseEntity.ok(orders);
    }
}
