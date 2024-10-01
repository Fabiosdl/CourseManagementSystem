package com.fabiolima.coursemanagementsys.dao;

import com.fabiolima.coursemanagementsys.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {



}
