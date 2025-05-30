package ir.maktab127.service;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Student;
import ir.maktab127.service.base.BaseService;

import java.util.List;
import java.util.Optional;

public interface StudentService extends BaseService<Student, Long> {

    Optional<Student> findByUsername(String username);

    List<Student> findByCourse(Course course);

    void enrollInCourse(Student student, Course course);

    void unenrollFromCourse(Student student, Course course);
}
