package com.fabiolima.coursemanagementsys.dao.specialdao;

import com.fabiolima.coursemanagementsys.entity.User;

public interface UserDAO {

    User findUserByUserName(String userName);

    void saveUser(User theUser);

    //User findRoleByName(String roleName);
}
