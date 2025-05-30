package ir.maktab127.Controller;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Teacher;
import ir.maktab127.service.TeacherService;

import java.util.List;
import java.util.Optional;

public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public Optional<Teacher> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("Username must be between 3 and 50 characters");
        }
        return teacherService.findByUsername(username);
    }

    public List<Course> findCourses(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null");
        }
        return teacherService.findCourses(teacher);
    }

    public void assignCourse(Teacher teacher, Course course) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        teacherService.assignCourse(teacher, course);
    }

    public void unassignCourse(Teacher teacher, Course course) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        teacherService.unassignCourse(teacher, course);
    }
} 