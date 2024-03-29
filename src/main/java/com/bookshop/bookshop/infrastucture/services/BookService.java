package com.bookshop.bookshop.infrastucture.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookshop.bookshop.core.coreRepositories.IBookRepo;
import com.bookshop.bookshop.core.coreServices.IBookService;
import com.bookshop.bookshop.core.exceptions.StorageException;
import com.bookshop.bookshop.core.mappers.BookModelMapper;
import com.bookshop.bookshop.core.mappers.GenreModelMapper;
import com.bookshop.bookshop.dtos.BookModelDto;
import com.bookshop.bookshop.dtos.GenreModelDto;


@Service
public class BookService implements IBookService 
{

    @Autowired
    IBookRepo bookRepo;
    @Autowired
    private Environment env;

    @Override
    @Async
    public CompletableFuture<List<BookModelDto>> GetAllModelsByPage(int page) 
    {
        List<BookModelDto> books = Collections.synchronizedList(bookRepo.GetAllBooksByPage(page)
                                                    .stream()
                                                    .map(BookModelMapper::AsDto)
                                                    .collect(Collectors.toList())
                                                    );

        return CompletableFuture.completedFuture(books);
    }

    @Override
    @Async
    public CompletableFuture<BookModelDto> GetBookModelById(UUID id) 
    {
        return CompletableFuture.completedFuture(BookModelMapper.AsDto(bookRepo.GetBookBookById(id)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> CreateModel(BookModelDto model) 
    {
        return CompletableFuture.completedFuture(bookRepo.CreateBook(BookModelMapper.AsEntity(model)));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> DeleteModelById(UUID id) 
    {
        return CompletableFuture.completedFuture(bookRepo.DeleteBookById(id));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> UpdateModel(BookModelDto model) 
    {
        return CompletableFuture.completedFuture(bookRepo.UpdateBook(BookModelMapper.AsEntity(model)));
    }

    @Override
    @Async
    public CompletableFuture<List<BookModelDto>> GetBooksByGenres(List<GenreModelDto> genres, int page) 
    {
        List<BookModelDto> models = Collections.synchronizedList(
            bookRepo.GetBooksByGenres(genres.stream().map(GenreModelMapper::AsEntity).collect(Collectors.toList()), page)
                .stream()
                .map(BookModelMapper::AsDto)
                .collect(Collectors.toList())
        );

        return CompletableFuture.completedFuture(models);
    }

    @Override
    @Async
    public CompletableFuture<String> SaveBookCover(MultipartFile model) 
    {
         // проверяем на то null ли поступаемый файл
        if(model.isEmpty())
        {
            throw new StorageException("File cannot be null");
        }

        // получаем path из files.properties и преобразуем в абсолютный путь
        Path path = Path.of(env.getProperty("files.path")+"/covers").toAbsolutePath(); 
        
        // создаем путь до самого файла(к пути path добавляем название загруженного файла) и переводим в абсолютный путь
        Path destinationPath = path.resolve(
            Paths.get(model.getOriginalFilename())).normalize().toAbsolutePath();
        

        if(!Files.exists(path)) // по идее созлание дирректории, если её нет
        {
            path.toFile().mkdir();
        }

        // проверка на то что путь просто до папки и до файла не идентичны(что к изначальному пути добавлено имя файла)
        if(!destinationPath.getParent().equals(path.toAbsolutePath()))
        {
            throw new StorageException("Error at saving in direction"); // коли они одинаковый кидаем ошибку
        }   

        // получаем inputSteram из присланного файла
        try(InputStream inputStream = model.getInputStream())
        {
            // копирукм файл из потока по пути на котором он будет теперь лежать
            Files.copy(inputStream, destinationPath, 
            StandardCopyOption.REPLACE_EXISTING);// сохраняем с заменой уже существующего файл(Если такой есть)
        }
        catch(IOException e)
        {
            throw new StorageException("Exception during saving the file");
        }
        
        return CompletableFuture.completedFuture(destinationPath.toString());
    }

    @Override
    @Async
    public CompletableFuture<List<BookModelDto>> FindBooksByAuthor(String authorName, int page) 
    {
        List<BookModelDto> books = bookRepo.FindBooksByAuthor(authorName, page)
                                            .stream()
                                            .map(BookModelMapper::AsDto)
                                            .collect(Collectors.toList());
        return CompletableFuture.completedFuture(books);
    }

    @Override
    @Async
    public CompletableFuture<List<BookModelDto>> FindBooksByName(String bookName, int page) 
    {
        List<BookModelDto> books = bookRepo.FindBooksByName(bookName, page)
                                            .stream()
                                            .map(BookModelMapper::AsDto)
                                            .collect(Collectors.toList());
        return CompletableFuture.completedFuture(books);
    }

    @Override
    @Async
    public CompletableFuture<Long> GetMaxPageForAll() 
    {
        return CompletableFuture.completedFuture(bookRepo.GetMaxPageForAll());        
    }

    @Override
    @Async
    public CompletableFuture<Long> GetMaxPageForGenresSearch(List<GenreModelDto> genres) 
    {
        return CompletableFuture.completedFuture(bookRepo.GetMaxPageForGenresSearch(genres.stream()
                    .map(GenreModelMapper::AsEntity)
                    .collect(Collectors.toList())));
    }

    @Override
    @Async
    public CompletableFuture<Long> GetMaxPageForSearchByAuthor(String authorName) 
    {
        return CompletableFuture.completedFuture(bookRepo.GetMaxPageForSearchByAuthor(authorName));
    }

    @Override
    @Async
    public CompletableFuture<Long> GetMaxPageForSearchByBookName(String bookName) 
    {
        return CompletableFuture.completedFuture(bookRepo.GetMaxPageForSearchByBookName(bookName));
    }

}
