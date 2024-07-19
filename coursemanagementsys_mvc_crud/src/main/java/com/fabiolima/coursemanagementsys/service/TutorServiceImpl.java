package com.fabiolima.coursemanagementsys.service;

import com.fabiolima.coursemanagementsys.dao.CourseRepository;
import com.fabiolima.coursemanagementsys.dao.TutorRepository;
import com.fabiolima.coursemanagementsys.dao.specialdao.TutorSpecialDAO;
import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.entity.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class TutorServiceImpl implements TutorService{

    private TutorRepository tutorRepository;
    private TutorSpecialDAO tutorSpecialDAO;
    private CourseRepository courseRepository;

    @Autowired
    public TutorServiceImpl(TutorRepository theTutorRepository,
                            TutorSpecialDAO tutorSpecialDAO, CourseRepository courseRepository){
        tutorRepository = theTutorRepository;
        this.tutorSpecialDAO = tutorSpecialDAO;
        this.courseRepository = courseRepository;
    }

    @Override
    public Tutor saveTutor(Tutor theTutor) {
        return tutorRepository.save(theTutor);
    }

    @Override
    public List<Tutor> findAllTutors() {
        return tutorRepository.findAll();
    }

    @Override
    public Tutor findTutorById(int theId) {
        Optional<Tutor> result = tutorRepository.findById(theId);

        if(result.isEmpty()) throw new RuntimeException("Tutor id: " + theId + "not found");
        else return result.get();
    }

    @Override
    public void deleteTutorById(int theId) {
        Optional<Tutor> result = tutorRepository.findById(theId);

        if(result.isEmpty()) throw new RuntimeException("Tutor id: " + theId + "not found");
        else tutorRepository.deleteById(theId);
    }

    @Override
    public Tutor findTutorAndRelatedCoursesByTutorId(int theId) {
        Tutor tempTutor = tutorSpecialDAO.findTutorAndRelatedCourses(theId);
        if(tempTutor != null) return tempTutor;
        else throw new RuntimeException("Could not find Tutor Id: " + theId);
    }

    @Override
    public void withdrawCourseByTutorId(int tutorId, int courseId) {
        Tutor tempTutor = findTutorAndRelatedCoursesByTutorId(tutorId);
        List<Course> courseList = tempTutor.getCourseList();

        Iterator<Course> iterator = courseList.iterator();
        int index=0;
        boolean courseFound = false;
        Course tempCourse = new Course();
        while(iterator.hasNext()){
            Course c = iterator.next();
            if(c.getId() == courseId){
                tempCourse = c;
                courseFound = true;
                iterator.remove();
                break;
            }
        }
        if(courseFound) saveTutor(tempTutor);
        else throw new RuntimeException(tempTutor.getFirstName() + " " + tempTutor.getLastName() + " is not enrolled in the " + tempCourse.getTitle());
    }

    @Override
    public void enrollTutorInCourse(int tutorId, int courseId) {
        Tutor tempTutor = findTutorById(tutorId);
        Optional<Course> optCourse = courseRepository.findById(courseId);
        if(optCourse.isEmpty()) throw new RuntimeException("Could not found course id: " + courseId);
        else{
            Course tempCourse = optCourse.get();
            tempTutor.addCourse(tempCourse);
            saveTutor(tempTutor);
        }
    }
}
