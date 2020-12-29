package com.example.attendance.User.Containers;

public class CourseListContainer {
    private String courseCode;
    private String group;

    public CourseListContainer() {
    }

    public CourseListContainer(String courseCode, String group) {
        this.courseCode = courseCode;
        this.group = group;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
