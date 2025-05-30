package ir.maktab127.repository;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Student;
import ir.maktab127.entity.user.Teacher;
import ir.maktab127.repository.base.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class CourseRepositoryImpl extends CrudRepositoryImpl<Course, Long> implements CourseRepository {
    protected CourseRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Course> getDomainClass() {
        return Course.class;
    }

    @Override
    public Optional<Course> findByCode(String code) {
        try {
            TypedQuery<Course> query = entityManager.createQuery(
                    "SELECT c FROM Course c WHERE c.code = :code", getDomainClass());
            query.setParameter("code", code);
            try {
                Course course = query.getSingleResult();
                return Optional.of(course);
            } catch (NoResultException e) {
                return Optional.empty();
            }
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> findByTeacher(Teacher teacher) {
        try{
            TypedQuery<Course> query = entityManager.createQuery(
                    "SELECT c FROM Course c WHERE c.teacher = :teacher", getDomainClass());
            query.setParameter("teacher", teacher);
            return query.getResultList();

        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> findByStudent(Student student) {
        try {
            TypedQuery<Course> query = entityManager.createQuery(
                    "SELECT c FROM Course c WHERE :student MEMBER OF c.students", getDomainClass());
            query.setParameter("student", student);
            return query.getResultList();

        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> findByTitleContaining(String title) {
        try {
            TypedQuery<Course> query = entityManager.createQuery(
                    "SELECT c FROM Course c WHERE LOWER(c.title) LIKE LOWER(:title)", Course.class);
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByCode(String code) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(c) FROM Course c WHERE c.code = :code", Long.class);
            query.setParameter("code", code);
            return query.getSingleResult() > 0;
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean existsByCourseAndStudent(Course course, Student student) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(c) FROM Course c WHERE c = :course AND :student MEMBER OF c.students", Long.class);
            query.setParameter("course", course);
            query.setParameter("student", student);
            return query.getSingleResult() > 0;

        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
}
