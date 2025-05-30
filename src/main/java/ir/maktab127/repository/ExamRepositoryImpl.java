package ir.maktab127.repository;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.Exam;
import ir.maktab127.entity.user.Student;
import ir.maktab127.repository.base.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class ExamRepositoryImpl extends CrudRepositoryImpl<Exam,Long> implements ExamRepository {
    protected ExamRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Exam> findByCourse(Course course) {
        try {
            TypedQuery<Exam> query = entityManager.createQuery(
                    "SELECT e FROM Exam e WHERE e.course = :course", Exam.class);
            query.setParameter("course", course);
            return query.getResultList();
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Exam> findByTitleContaining(String title) {
        try {
            TypedQuery<Exam> query = entityManager.createQuery(
                    "SELECT e FROM Exam e WHERE LOWER(e.title) ILIKE LOWER(:title)", Exam.class);
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Exam> findByStartTimeAfter(LocalDateTime startTime) {
        try {
            TypedQuery<Exam> query = entityManager.createQuery(
                    "SELECT e FROM Exam e WHERE e.startTime > :startTime", Exam.class);
            query.setParameter("startTime", startTime);
            return query.getResultList();
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Exam> findByStartTimeBefore(LocalDateTime endTime) {
        try {
            TypedQuery<Exam> query = entityManager.createQuery(
                    "SELECT e FROM Exam e WHERE e.startTime < :endTime", Exam.class);
            query.setParameter("endTime", endTime);
            return query.getResultList();
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Exam> findAvailableExamsForStudent(Student student, LocalDateTime currentTime) {
        try {

            TypedQuery<Exam> query = entityManager.createQuery(
                    "SELECT e FROM Exam e " +
                            "JOIN e.course c " +
                            "JOIN c.students s " +
                            "WHERE s = :student " +
                            "AND :currentTime >= e.startTime " +
                            "AND :currentTime <= e.startTime + MINUTES(e.durationMinutes) " +
                            "AND NOT EXISTS (SELECT r FROM ExamResult r WHERE r.exam = e AND r.student = :student AND r.endTime IS NOT NULL)",
                    Exam.class);
            query.setParameter("student", student);
            query.setParameter("currentTime", currentTime);
            return query.getResultList();
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Class<Exam> getDomainClass() {
        return  Exam.class;
    }
}
