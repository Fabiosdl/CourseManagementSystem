package com.fabiolima.coursemanagementsys.dao.specialdao;

import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentSpecialDAOImpl implements StudentSpecialDAO{

    private EntityManager entityManager;

    @Autowired
    public StudentSpecialDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    public Student findStudentAndEnrolledCourses(int theId) {

        TypedQuery<Student> query = entityManager.createQuery(
                // left join fetch will return student even if the courselist is empty.
                // if use join fetch only, it will return an error if the courselist is empty
                // as it will return both student and the course that doesn't exist in the student list
                "select s from Student s " +
                        "LEFT JOIN FETCH s.courseList " +
                        "where s.id = :data", Student.class);
        query.setParameter("data", theId);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
