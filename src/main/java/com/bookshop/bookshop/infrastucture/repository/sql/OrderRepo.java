package com.bookshop.bookshop.infrastucture.repository.sql;

import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookshop.bookshop.core.coreRepositories.IOrderRepo;
import com.bookshop.bookshop.core.models.OrderModel;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class OrderRepo implements IOrderRepo
{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<OrderModel> GetAllOrders() 
    {
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<OrderModel> cq = cb.createQuery(OrderModel.class);
        Root<OrderModel> root = cq.from(OrderModel.class);

        cq.select(root);
        List<OrderModel> orderModels = session.createQuery(cq).getResultList();

        session.close();
        return orderModels;
    }

    @Override
    public OrderModel GetOrderById(UUID id) 
    {
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<OrderModel> cq = cb.createQuery(OrderModel.class);
        Root<OrderModel> root = cq.from(OrderModel.class);

        cq.select(root).where(root.get("id").in(id));
        
        OrderModel order = session.createQuery(cq).uniqueResult();

        session.close();
        return order;
    }

    @Override
    public List<OrderModel> GetOrdersByUserId(UUID userId, int page) 
    {
        int pageSize = 20;

        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<OrderModel> cq = cb.createQuery(OrderModel.class);
        Root<OrderModel> root = cq.from(OrderModel.class);

        cq.select(root).where(root.get("userId").in(userId));

        Query<OrderModel> query = session.createQuery(cq);
        // пагинация
        query.setFirstResult((page-1) * pageSize);// Чтоб ничего не брать все записи из бд
        query.setMaxResults(pageSize);

        List<OrderModel> orderModels = query.getResultList();

        session.close();

        return orderModels;
    }

    @Override
    public boolean CreateOrder(OrderModel model) 
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
    public boolean UpdateOrder(OrderModel model) 
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
    public boolean DeleteOrderById(UUID id) 
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<OrderModel> cd = cb.createCriteriaDelete(OrderModel.class);
        Root<OrderModel> root = cd.from(OrderModel.class);

        cd.where(root.get("id").in(id));
        
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

        return true;
    }
    
}
