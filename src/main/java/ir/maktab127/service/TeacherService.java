package ir.maktab127.service;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Teacher;
import ir.maktab127.service.base.BaseService;

import java.util.List;
import java.util.Optional;
public interface TeacherService extends BaseService<Teacher, Long> {

    Optional<Teacher> findByUsername(String username);

    List<Course> findCourses(Teacher teacher);

    void assignCourse(Teacher teacher, Course course);

    void unassignCourse(Teacher teacher, Course course);
}
