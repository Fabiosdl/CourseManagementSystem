package com.fabiolima.coursemanagementsys.dao.specialdao;

import com.fabiolima.coursemanagementsys.entity.Tutor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TutorSpecialDAOImpl implements TutorSpecialDAO{

    private EntityManager entityManager;
    @Autowired
    public TutorSpecialDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Tutor findTutorAndRelatedCourses(int tutorId) {
        TypedQuery<Tutor> query = entityManager.createQuery(
                "select t from Tutor t "
                + "LEFT JOIN FETCH t.courseList "
                + "where t.id = :data", Tutor.class
        );
        query.setParameter("data", tutorId);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
