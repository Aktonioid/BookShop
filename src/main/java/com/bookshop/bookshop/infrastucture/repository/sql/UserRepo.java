package com.bookshop.bookshop.infrastucture.repository.sql;

import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookshop.bookshop.core.coreRepositories.IUserRepo;
import com.bookshop.bookshop.core.models.UserModel;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class UserRepo implements IUserRepo
{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public UserModel UserById(UUID id) {
    
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserModel> cq = cb.createQuery(UserModel.class); 
        Root<UserModel> root = cq.from(UserModel.class);

        cq.select(root).where(root.get("id").in(id));
        UserModel userModel = session.createQuery(cq).uniqueResult(); 

        session.close();

        return userModel;
    }
    

    @Override
    public UserModel UserByEmail(String email) 
    {
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserModel> cq = cb.createQuery(UserModel.class); 
        Root<UserModel> root = cq.from(UserModel.class);

        cq.select(root).where(root.get("email").in(email));

        UserModel userModel = session.createQuery(cq).uniqueResult(); 

        session.close();
        return userModel;
    }

    @Override
    public UserModel UserByUsername(String username) 
    {
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserModel> cq = cb.createQuery(UserModel.class); 
        Root<UserModel> root = cq.from(UserModel.class);

        cq.select(root)
            .where(root.get("username").in(username));
        
        UserModel userModel = session.createQuery(cq).uniqueResult(); 

        session.close();
        return userModel;
    }

    @Override
    public boolean CreateUser(UserModel userModel) 
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try
        {
            transaction.begin();
            session.persist(userModel);
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
            throw e;
        }   
        finally
        {
            session.close();
        }
        return true;
    }

    @Override
    public boolean DeleteUserById(UUID id) 
    {
        Session session =  sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<UserModel> cd = cb.createCriteriaDelete(UserModel.class);
        Root<UserModel> root = cd.from(UserModel.class);

        cd.where(root.get("id").in(id));

        MutationQuery mutation = session.createMutationQuery(cd);
         
        try
        {
            transaction.begin();
            mutation.executeUpdate();
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
            throw e;
        }
        finally
        {
            session.close();
        }

        return true;
    }

    @Override
    public boolean UpdateUser(UserModel userModel) 
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        

        try
        {
            transaction.begin();
            session.merge(userModel);
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
            throw e;
        }
        finally
        {
            session.close();
        }

        return true;
    }
    
}
