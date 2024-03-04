package com.bookshop.bookshop.infrastucture.repository.sql;

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
        Session session = sessionFactory.openSession();

        int pageSize = 20;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<BookModel> cq = cb.createQuery(BookModel.class);
        Root<BookModel> root = cq.from(BookModel.class); 
        
        cq.select(root);
        Query<BookModel> query = session.createQuery(cq);
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);

        List<BookModel> resultList = query.getResultList();

        session.close();
        return resultList;
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
    public List<BookModel> GetBooksByGenres(List<GenreModel> genres, int page) 
    {
        int pageSize = 20;

        Session session = sessionFactory.getCurrentSession();
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<BookModel> cq = cb.createQuery(BookModel.class);
        Root<BookModel> root = cq.from(BookModel.class);

        cq.select(root).where(root.get("genres").in(genres));

        Query<BookModel> query = session.createQuery(cq);
        
        query.setFirstResult((page-1) * pageSize); // задаем первую запись
        query.setMaxResults(pageSize); // задаем максимальное колличество элементов в ответе
        
        List<BookModel> resultList = query.getResultList();

        session.close();
        return resultList;
    }

    @Override
    public List<BookModel> FindBooksByAuthor(String authorName, int page) 
    {
        int pageSize = 20;

        Session session = sessionFactory.openSession();
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<BookModel> cq = cb.createQuery(BookModel.class);
        Root<BookModel> root = cq.from(BookModel.class);

        // выбираем только те данные в которых автор начинается как authrName. По факту sql команда like
        cq.select(root).where(cb.like(root.get("authorName"), authorName+"%"));
        
        Query<BookModel> query  = session.createQuery(cq);
        query.setFirstResult((page - 1) * pageSize); // задаем первую запись
        query.setMaxResults(pageSize); // задаем максимальное колличество элементов в ответе

        List<BookModel> resultList = query.getResultList(); // сохранили все книги в список

        session.close();
        return resultList;
    }

    @Override
    public List<BookModel> FindBooksByName(String bookName, int page) 
    {
        int pageSize = 20;
        Session session = sessionFactory.openSession();
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<BookModel> cq = cb.createQuery(BookModel.class);
        Root<BookModel> root = cq.from(BookModel.class);

        cq.select(root).where(cb.like(root.get("bookName"), bookName+"%"));

        Query<BookModel> query = session.createQuery(cq);
        query.setFirstResult((page - 1) * pageSize); // задаем первую запись
        query.setMaxResults(pageSize); // задаем максимальное колличество элементов в ответе

        List<BookModel> resuList = query.getResultList();

        session.close();
        return resuList;
    }

    @Override
    public long GetMaxPageForAll() 
    {
        int pageSize = 20;

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<BookModel> root = cq.from(BookModel.class);

        cq.select(cb.count(root));

        long count = session.createQuery(cq).uniqueResult(); // получаем сколько всего записей о книгах в бд

        session.close();

        long pagecount = count/pageSize; // считаем сколько страниц

        if(count%pageSize != 0)
        {
            pagecount++;
        }

        return pagecount;
    }

    @Override
    public long GetMaxPageForGenresSearch(List<GenreModel> genres) 
    {
        int pageSize = 20;

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<BookModel> root = cq.from(BookModel.class);

        cq.select(cb.count(root))
          .where(root.get("genres").in(genres));

        long count = session.createQuery(cq).uniqueResult(); // получаем сколько всего записей о книгах в бд

        session.close();

        long pagecount = count/pageSize; // считаем сколько страниц

        if(count%pageSize != 0)
        {
            pagecount++;
        }

        return pagecount;
    }

    @Override
    public long GetMaxPageForSearchByAuthor(String authrName) 
    {
        int pageSize = 20;

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<BookModel> root = cq.from(BookModel.class);

        cq.select(cb.count(root))
          .where(cb.like(root.get("authorName"), authrName+"%"));

        long count = session.createQuery(cq).uniqueResult(); // получаем сколько всего записей о книгах в бд

        session.close();

        long pagecount = count/pageSize; // считаем сколько страниц

        if(count%pageSize != 0)
        {
            pagecount++;
        }

        return pagecount;
    }

    @Override
    public long GetMaxPageForSearchByBookName(String bookName) 
    {
        int pageSize = 20;

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<BookModel> root = cq.from(BookModel.class);

        cq.select(cb.count(root))
          .where(cb.like(root.get("bookName"), bookName+"%"));

        long count = session.createQuery(cq).uniqueResult(); // получаем сколько всего записей о книгах в бд

        session.close();

        long pagecount = count/pageSize; // считаем сколько страниц

        if(count%pageSize != 0)
        {
            pagecount++;
        }

        return pagecount;
    }

}
