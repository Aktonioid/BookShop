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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookshop.bookshop.core.coreRepositories.IGenreRepo;
import com.bookshop.bookshop.core.models.GenreModel;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class GnereRepo implements IGenreRepo
{

    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public List<GenreModel> GetAllGenres() 
    {
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<GenreModel> cq = cb.createQuery(GenreModel.class);
        Root<GenreModel> root = cq.from(GenreModel.class);

        cq.select(root);

        List<GenreModel> models = Collections.synchronizedList(new ArrayList<GenreModel>(session.createQuery(cq).getResultList()));

        session.close();

        return models;
    }

    @Override
    public GenreModel GetGenreById(UUID id) 
    {
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<GenreModel> cq = cb.createQuery(GenreModel.class);
        Root<GenreModel> root = cq.from(GenreModel.class);

        cq.select(root).where(root.get("id").in(id));

        GenreModel model = session.createQuery(cq).uniqueResult(); 
        
        session.close();

        return model;  
    }

    @Override
    public boolean CreateModel(GenreModel model) 
    {
        Session session = sessionFactory.openSession();
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
        catch(Exception e)
        {
            transaction.rollback();
            session.close();
            throw e;
        }

        session.close();

        return true;
    }

    @Override
    public boolean UpdateModel(GenreModel model) 
    {
        Session session = sessionFactory.openSession();
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
            transaction.rollback();
            session.close();
            throw e;
        }

        session.close();

        return true;
    }

    @Override
    public boolean DeleteModelById(UUID id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<GenreModel> cd = cb.createCriteriaDelete(GenreModel.class);
        Root<GenreModel> root = cd.from(GenreModel.class);  

        cd.where(root.get("id").in(id));

        MutationQuery query = session.createMutationQuery(cd);

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
            return false;
        }
        catch(Exception e)
        {
            transaction.rollback();
            session.close();
            throw e;
        }

        session.close();

        return true;
    }

    @Override
    public boolean IsGenreExhistsByName(String genreName) 
    {
        boolean isExhists = false;

        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<GenreModel> cq = cb.createQuery(GenreModel.class);
        Root<GenreModel> root = cq.from(GenreModel.class);

        cq.select(root).where(root.get("name").in(genreName));

        GenreModel model = session.createQuery(cq).uniqueResult();

        if(model != null)
        {
            isExhists = true;
        }
        
        session.close();

        return isExhists;
    }
    
}
