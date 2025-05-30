package ir.maktab127.repository.user;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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
        if(course != null) {
            try {
                TypedQuery<Student> query = entityManager.createQuery(
                        "SELECT s FROM Student s WHERE :course MEMBER OF s.courses", getDomainClass());
                query.setParameter("course", course);
                return query.getResultList();

            }catch (RuntimeException e){
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Course is null");


    }
    @Override
    public boolean existsByStudentAndCourse(Student student, Course course) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(s) FROM Student s WHERE s = :student AND :course MEMBER OF s.courses", Long.class);
            query.setParameter("student", student);
            query.setParameter("course", course);
            return query.getSingleResult() > 0;

        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
}
