package com.fabiolima.coursemanagementsys.dao.specialdao;

import com.fabiolima.coursemanagementsys.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO{

    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public User findUserByUserName(String userName) {

        TypedQuery<User> query = entityManager.createQuery(
                "from User where username=:uName", User.class);
        query.setParameter("uName", userName);

        User theUser = null;

        try{
            theUser = query.getSingleResult();
        }catch (Exception e){
            theUser = null;
        }
        return theUser;
    }

    @Override
    @Transactional
    public void saveUser(User theUser) {
        entityManager.merge(theUser);
    }
}
