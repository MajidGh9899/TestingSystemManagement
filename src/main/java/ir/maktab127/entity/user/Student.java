package ir.maktab127.entity.user;

import ir.maktab127.entity.Course;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@DiscriminatorValue("STUDENT")
public class Student extends User {

    @Column(name = "student_code",nullable = false,unique = true)
    private String studentCode;
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses = new HashSet<>();

    public Student() {
        super();
    }

    public Student(String firstName, String lastName, String username, String password, String studentCode) {
        super(firstName, lastName, username, password);
        this.studentCode = studentCode;
    }



    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }
}