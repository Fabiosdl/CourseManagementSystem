package com.fabiolima.coursemanagementsys.dao.specialdao;

import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseSpecialDAOImpl implements CourseSpecialDAO{

    private EntityManager entityManager;

    @Autowired
    public CourseSpecialDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int theId) {

        TypedQuery<Course> query = entityManager.createQuery("select c from Course c " +
                                                             "JOIN FETCH c.studentList " +
                                                             "where c.id = :data", Course.class);
        query.setParameter("data", theId);

        System.out.println(query.getSingleResult());
        return query.getSingleResult();
    }
}

