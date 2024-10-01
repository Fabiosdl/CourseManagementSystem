package com.fabiolima.coursemanagementsys.service;

import com.fabiolima.coursemanagementsys.dao.UserRepository;
import com.fabiolima.coursemanagementsys.dao.specialdao.UserDAO;
import com.fabiolima.coursemanagementsys.entity.Student;
import com.fabiolima.coursemanagementsys.entity.Tutor;
import com.fabiolima.coursemanagementsys.entity.User;
import com.fabiolima.coursemanagementsys.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    private UserDAO userDAO;

    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder passwordEncoder,
                           UserRepository userRepository){
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User findByUserName(String username) {
        return userDAO.findUserByUserName(username);
    }

    @Override
    public void save(WebUser webUser) {
        User user;

        // Determine user role and instantiate the appropriate subclass
        switch (webUser.getRole()) {
            case "STUDENT":
                user = new Student();
                ((Student) user).setFirstName(webUser.getFirstName());
                ((Student) user).setLastName(webUser.getLastName());
                ((Student) user).setEmail(webUser.getEmail());
                break;
            case "TUTOR":
                user = new Tutor();
                ((Tutor) user).setFirstName(webUser.getFirstName());
                ((Tutor) user).setLastName(webUser.getLastName());
                ((Tutor) user).setEmail(webUser.getEmail());
                break;
            default:
                throw new IllegalArgumentException("Unknown role: " + webUser.getRole());
        }

        // Set common properties
        user.setUsername(webUser.getUserName());
        user.setPassword(passwordEncoder.encode(webUser.getPassword()));
        user.setRole(webUser.getRole());
        // save user in the database
        userDAO.saveUser(user);
    }

    @Override
    public void deleteUserById(int userId) {
        User tempUser;
        Optional<User> result = userRepository.findById(userId);
        if(result.isPresent()){
            tempUser = result.get();
        } else throw new RuntimeException("User not found.");
        userRepository.delete(tempUser);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findUserByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        // Assuming 'user.getRoles()' returns a single role as a string, modify if it's an object or collection.
        String role = user.getRole(); // Fetch the single role directly

        // Map the single role to a SimpleGrantedAuthority
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

        // Return the UserDetails object
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
