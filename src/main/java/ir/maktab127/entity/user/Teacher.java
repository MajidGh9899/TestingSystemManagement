package ir.maktab127.entity.user;
import ir.maktab127.entity.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@DiscriminatorValue("TEACHER")
public class Teacher extends User {
    @Column(name = "teacher_code",nullable = false,unique = true)
    private String teacherCode;

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses = new HashSet<>();

    public Teacher() {
        super();
    }

    public Teacher(String firstName, String lastName, String username, String password, String teacherCode) {
        super(firstName, lastName, username, password);
        this.teacherCode = teacherCode;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
        course.setTeacher(this);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        course.setTeacher(null);
    }
}
