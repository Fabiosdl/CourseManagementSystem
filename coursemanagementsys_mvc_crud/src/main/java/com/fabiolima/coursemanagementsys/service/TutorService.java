package com.fabiolima.coursemanagementsys.service;

import com.fabiolima.coursemanagementsys.entity.Tutor;

import java.util.List;

public interface TutorService {

    Tutor saveTutor(Tutor theTutor);

    List<Tutor> findAllTutors();

    Tutor findTutorById(int theId);

    void deleteTutorById(int theId);

    Tutor findTutorAndRelatedCoursesByTutorId(int theId);

    void withdrawCourseByTutorId(int tutorId, int courseId);

    void enrollTutorInCourse(int tutorId, int courseId);
}
