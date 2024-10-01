package com.fabiolima.coursemanagementsys.service;

import com.fabiolima.coursemanagementsys.entity.User;
import com.fabiolima.coursemanagementsys.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public User findByUserName(String username);

    void save(WebUser webUser);

    void deleteUserById(int userId);
}
