package com.bookshop.bookshop.infrastucture.repository.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.bookshop.bookshop.core.coreRepositories.ICrateRepo;
import com.bookshop.bookshop.core.models.CrateModel;
import com.bookshop.bookshop.core.models.CratePartModel;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;

public class CrateRepo implements ICrateRepo
{
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public CrateModel GetCrateByUserId(UUID id) 
    {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CrateModel> cq = cb.createQuery(CrateModel.class);
        Root<CrateModel> root = cq.from(CrateModel.class);

        cq.select(root).where(root.get("userId").in(id));
        
        CrateModel model = session.createQuery(cq).uniqueResult();

        session.close();
        return model;
    }

    @Override
    @Deprecated
    public List<CrateModel> GetAllCrateModels() 
    {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CrateModel> cq = cb.createQuery(CrateModel.class);
        Root<CrateModel> root = cq.from(CrateModel.class);

        cq.select(root);
        ArrayList<CrateModel> crateModels = new ArrayList<>(session.createQuery(cq).list());

        session.close();
        return Collections.synchronizedList(crateModels);
    }

    @Override
    public boolean CreateCrate(CrateModel crateModel) 
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        
        try
        {
            transaction.begin();
            session.persist(crateModel);
            transaction.commit();
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback();
            session.close();
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
    public boolean UpdateCrate(CrateModel crateModel) 
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try
        {
            transaction.begin();
            session.merge(crateModel);
            transaction.commit();
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return false;
        }
        catch(Exception e)
        {
            transaction.rollback();
            session.close();
            throw e;
        }

        session.close();
        return false;
    }

    @Override
    public boolean DeleteCrateByUserId(UUID userId) 
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<CrateModel> cd = cb.createCriteriaDelete(CrateModel.class);
        Root<CrateModel> root = cd.from(CrateModel.class);

        cd.where(root.get("user_id").in(userId));

        try
        {
            transaction.begin();
            session.createMutationQuery(cd).executeUpdate();
            transaction.commit();
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return false;
        }
        catch(Exception e)
        {
            transaction.rollback();
            session.close();
            throw e;
        }

        session.close();
        return false;
    }

    @Override
    public boolean AddBookToCrate(CratePartModel part, UUID userId) 
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CrateModel> cq = cb.createQuery(CrateModel.class);
        Root<CrateModel> root = cq.from(CrateModel.class);

        // берем id корзины для того чтобы запихнуть его в часть корзины и потом сохрангить
        cq.select(root.get("id"))
            .where(root.get("user_id").in(userId));

        try
        {
            transaction.begin();
            part.setCrate(session.createQuery(cq).uniqueResult());
            session.persist(part);
            transaction.commit();
        }
        catch(HibernateException e)
        {
            transaction.rollback();
            session.close();
            e.printStackTrace();
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
    public boolean AddBookCount(UUID partId) 
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaUpdate<CratePartModel> cu = cb.createCriteriaUpdate(CratePartModel.class);
        Root<CratePartModel> root = cu.from(CratePartModel.class);

        cu.set("bookCount", "bookCount+1");
        cu.where(root.get("id").in(partId));

        try
        {
            transaction.begin();
            session.createMutationQuery(cu).executeUpdate();
            transaction.commit();
        }
        catch(HibernateException e)
        {
            transaction.rollback();
            session.close();
            e.printStackTrace();
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
    
}
