package ir.maktab127.repository.user;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Student;
import jakarta.persistence.EntityManager;

import java.util.List;

public class StudentRepositoryImpl extends UserRepositoryImpl<Student> implements StudentRepository {
    protected StudentRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Student> getDomainClass() {
        return Student.class;
    }

    @Override
    public List<Student> findByCourse(Course course) {
        return List.of();
    }
}
