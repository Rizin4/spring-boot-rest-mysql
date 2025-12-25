package com.maria.resth2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student extends Auditable{
    
    private String name;
    private String course;
    
    @Column(unique = true)
    private String email;

    public Student() { }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
