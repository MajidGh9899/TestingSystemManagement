package ir.maktab127.service;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Student;
import ir.maktab127.entity.user.Teacher;
import ir.maktab127.service.base.BaseService;

import java.util.List;
import java.util.Optional;
public interface CourseService extends BaseService<Course, Long> {

    Optional<Course> findByCode(String code);

    List<Course> findByTeacher(Teacher teacher);

    List<Course> findByStudent(Student student);

    List<Course> findByTitleContaining(String title);

    boolean existsByCode(String code);

    void addStudent(Course course, Student student);

    void removeStudent(Course course, Student student);

    void assignTeacher(Course course, Teacher teacher);

    void unassignTeacher(Course course, Teacher teacher);
}
