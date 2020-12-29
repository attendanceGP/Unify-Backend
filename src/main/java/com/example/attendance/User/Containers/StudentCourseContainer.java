package com.example.attendance.User.Containers;

import com.example.attendance.User.Model.Student;

public class StudentCourseContainer {
    private Student student;
    private CourseListContainer courseList[];

    public StudentCourseContainer() {
    }

    public StudentCourseContainer(Student student, CourseListContainer[] courseList) {
        this.student = student;
        this.courseList = courseList;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CourseListContainer[] getCourseList() {
        return courseList;
    }

    public void setCourseList(CourseListContainer[] courseList) {
        this.courseList = courseList;
    }
}


