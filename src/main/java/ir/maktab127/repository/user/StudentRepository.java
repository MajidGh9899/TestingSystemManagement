package ir.maktab127.repository.user;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Student;

import java.util.List;

public interface StudentRepository extends UserRepository<Student> {
    List<Student> findByCourse(Course course);
}

