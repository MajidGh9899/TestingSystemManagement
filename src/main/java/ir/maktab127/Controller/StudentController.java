package ir.maktab127.Controller;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Student;
import ir.maktab127.service.StudentService;

import java.util.List;
import java.util.Optional;

public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    public Optional<Student> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("Username must be between 3 and 50 characters");
        }
        return studentService.findByUsername(username);
    }

    public List<Student> findByCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        return studentService.findByCourse(course);
    }

    public void enrollInCourse(Student student, Course course) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        studentService.enrollInCourse(student, course);
    }

    public void unenrollFromCourse(Student student, Course course) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        studentService.unenrollFromCourse(student, course);
    }
} 