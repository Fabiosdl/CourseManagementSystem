package com.fabiolima.coursemanagementsys.service;

import com.fabiolima.coursemanagementsys.dao.AdminRepository;
import com.fabiolima.coursemanagementsys.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    public void saveAdmin(Admin theAdmin){
        adminRepository.save(theAdmin);
    }

}
