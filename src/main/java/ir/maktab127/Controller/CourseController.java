package ir.maktab127.Controller;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Student;
import ir.maktab127.entity.user.Teacher;
import ir.maktab127.service.CourseService;

import java.util.List;
import java.util.Optional;

public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public Optional<Course> findByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        if (code.length() < 3 || code.length() > 20) {
            throw new IllegalArgumentException("Course code must be between 3 and 20 characters");
        }
        return courseService.findByCode(code);
    }

    public List<Course> findByTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null");
        }
        return courseService.findByTeacher(teacher);
    }

    public List<Course> findByStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        return courseService.findByStudent(student);
    }

    public List<Course> findByTitleContaining(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        
        return courseService.findByTitleContaining(title);
    }

    public boolean existsByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        
        return courseService.existsByCode(code);
    }

    public void addStudent(Course course, Student student) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        courseService.addStudent(course, student);
    }

    public void removeStudent(Course course, Student student) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        courseService.removeStudent(course, student);
    }

    public void assignTeacher(Course course, Teacher teacher) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null");
        }
        courseService.assignTeacher(course, teacher);
    }

    public void unassignTeacher(Course course, Teacher teacher) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null");
        }
        courseService.unassignTeacher(course, teacher);
    }
} 