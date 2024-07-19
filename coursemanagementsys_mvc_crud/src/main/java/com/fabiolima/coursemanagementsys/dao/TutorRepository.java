package com.fabiolima.coursemanagementsys.dao;

import com.fabiolima.coursemanagementsys.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Integer> {

}