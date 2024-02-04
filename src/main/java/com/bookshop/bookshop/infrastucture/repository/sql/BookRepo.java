package com.bookshop.bookshop.infrastucture.repository.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookshop.bookshop.core.coreRepositories.IBookRepo;
import com.bookshop.bookshop.core.models.BookModel;
import com.bookshop.bookshop.core.models.GenreModel;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class BookRepo implements IBookRepo
{
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<BookModel> GetAllBooksByPage(int pageNumber) 
    {
        Session session = sessionFactory.getCurrentSession();

        int pageSize = 20;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<BookModel> cq = cb.createQuery(BookModel.class);
        Root<BookModel> root = cq.from(BookModel.class); 
        
        cq.select(root);
        Query<BookModel> query = session.createQuery(cq);
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);

        return Collections.synchronizedList(new ArrayList<BookModel>(query.getResultList()));
    }

    @Override
    public BookModel GetBookBookById(UUID id) 
    {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<BookModel> cq = cb.createQuery(BookModel.class);
        Root<BookModel> root = cq.from(BookModel.class);

        cq.select(root).where(root.get("id").in(id));

        Query<BookModel> query = session.createQuery(cq);
        
        return query.uniqueResult();
    }

    @Override
    public boolean CreateBook(BookModel model) 
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();

        try
        {
            transaction.begin();
            session.persist(model);
            transaction.commit();
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
        catch(Exception e)// чтоб точно при ошибках не записаласть только часть данных
        {
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }

        return true;
    }

    @Override
    public boolean DeleteBookById(UUID id) 
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();

        // создание query для удаления книги по id
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<BookModel> cq = cb.createCriteriaDelete(BookModel.class);
        Root<BookModel> root = cq.from(BookModel.class); 
        cq.where(root.get("id").in(id));
        MutationQuery query = session.createMutationQuery(cq);

        try
        {
            transaction.begin();
            query.executeUpdate();
            transaction.commit();
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback();
            throw e;     
        }

        return true;
    }

    @Override
    public boolean UpdateBook(BookModel model) 
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();

        try
        {
            transaction.begin();
            session.merge(model);
            transaction.commit();
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }

        return true;
    }

    @Override
    public List<BookModel> GetBooksByGenres(ArrayList<GenreModel> genres, int page) 
    {
        int pageSize = 20;

        Session session = sessionFactory.getCurrentSession();
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<BookModel> cq = cb.createQuery(BookModel.class);
        Root<BookModel> root = cq.from(BookModel.class);

        cq.select(root).where(root.get("genres").in(genres));

        Query<BookModel> query = session.createQuery(cq);
        
        query.setFirstResult((page-1) * pageSize);
        query.setMaxResults(pageSize);
        
        return Collections.synchronizedList(new ArrayList<BookModel>());
    }

    
}
