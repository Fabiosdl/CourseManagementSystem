package com.fabiolima.coursemanagementsys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tutor")
public class Tutor extends User {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @JsonIgnore // to prevent infinite looping while getting the course list
    @OneToMany(mappedBy = "tutor",
                cascade = {CascadeType.DETACH, CascadeType.MERGE,
                CascadeType.PERSIST, CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    private List<Course> courseList;

    public Tutor(){
        super();
    }
    public Tutor(String firstName, String lastName, String email) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public void addCourse(Course tempCourse){
        if(courseList==null){
            courseList = new ArrayList<>();
        }
        courseList.add(tempCourse);
        tempCourse.setTutor(this);
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "id=" + getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
