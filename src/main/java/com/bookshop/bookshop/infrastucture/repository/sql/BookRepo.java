package com.bookshop.bookshop.infrastucture.repository.sql;

import java.util.ArrayList;
import java.util.UUID;

import com.bookshop.bookshop.core.coreRepositories.IBookRepo;
import com.bookshop.bookshop.core.models.BookModel;
import com.bookshop.bookshop.core.models.GenreModel;

public class BookRepo implements IBookRepo
{

    @Override
    public ArrayList<BookModel> GetAllBooksByPage(int page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetAllBooksByPage'");
    }

    @Override
    public BookModel GetBookBookById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetBookBookById'");
    }

    @Override
    public boolean CreateBook(BookModel model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'CreateBook'");
    }

    @Override
    public boolean DeleteBookById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DeleteBookById'");
    }

    @Override
    public boolean UpdateBook(BookModel model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UpdateBook'");
    }

    @Override
    public boolean GetBooksByGenres(ArrayList<GenreModel> genres, int page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetBooksByGenres'");
    }
    
}
