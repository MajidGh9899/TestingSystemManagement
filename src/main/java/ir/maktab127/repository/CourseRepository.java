package ir.maktab127.repository;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Student;
import ir.maktab127.entity.user.Teacher;
import ir.maktab127.repository.base.CrudRepository;
import java.util.Optional;
import java.util.List;

public interface CourseRepository extends CrudRepository<Course,Long> {
    Optional<Course> findByCode(String code);

    List<Course> findByTeacher(Teacher teacher);

    List<Course> findByStudent(Student student);

    List<Course> findByTitleContaining(String title);

    boolean existsByCode(String code);

    boolean existsByCourseAndStudent(Course course, Student student);

}
