package com.fabiolima.coursemanagementsys.dao.specialdao;

import com.fabiolima.coursemanagementsys.entity.Tutor;

public interface TutorSpecialDAO {
    Tutor findTutorAndRelatedCourses(int tutorId);
}
