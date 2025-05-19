package ir.maktab127.repository;

import ir.maktab127.entity.Exam;
import ir.maktab127.entity.ExamResult;
import ir.maktab127.entity.user.Student;
import ir.maktab127.repository.base.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class ExamResultRepositoryImpl extends CrudRepositoryImpl<ExamResult,Long> implements ExamResultRepository {

    protected ExamResultRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<ExamResult> getDomainClass() {
        return ExamResult.class;
    }

    @Override
    public List<ExamResult> findByExam(Exam exam) {
        try{
            TypedQuery<ExamResult> query=entityManager.createQuery(
                    "SELECT er FROM ExamResult er WHERE er.exam = :exam",getDomainClass());
            query.setParameter("exam",exam);
            return query.getResultList();

        }finally {
            entityManager.close();
        }
    }

    @Override
    public List<ExamResult> findByStudent(Student student) {
        try{
            TypedQuery<ExamResult> query=entityManager.createQuery(
                    "SELECT er FROM ExamResult er WHERE er.student = :student",getDomainClass());
            query.setParameter("student",student);
            return query.getResultList();

        }finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<ExamResult> findByExamAndStudent(Exam exam, Student student) {
        try{
            TypedQuery<ExamResult> query=entityManager.createQuery(
                    "SELECT er FROM ExamResult er WHERE er.exam = :exam AND er.student = :student",getDomainClass());
            query.setParameter("exam",exam);
            query.setParameter("student",student);
            try {
                ExamResult examResult = query.getSingleResult();
                return Optional.of(examResult);
            }catch (NoResultException e){
                return Optional.empty();
            }

        }finally {
            entityManager.close();
        }
    }

    @Override
    public List<ExamResult> findByExamAndCompletedStatus(Exam exam, boolean completed) {
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ExamResult> query = cb.createQuery(ExamResult.class);
            Root<ExamResult> root = query.from(getDomainClass());

            Predicate examPredicate = cb.equal(root.get("exam"), exam);

            return getExamResults(completed, cb, query, root, examPredicate);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<ExamResult> findByStudentAndCompletedStatus(Student student, boolean completed) {
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ExamResult> query = cb.createQuery(getDomainClass());
            Root<ExamResult> root = query.from(getDomainClass());

            Predicate examPredicate = cb.equal(root.get("student"), student);

            return getExamResults(completed, cb, query, root, examPredicate);
        } finally {
            entityManager.close();
        }
    }

    private List<ExamResult> getExamResults(boolean completed, CriteriaBuilder cb, CriteriaQuery<ExamResult> query, Root<ExamResult> root, Predicate examPredicate) {
        Predicate timePredicate;
        if (completed) {
            timePredicate = cb.isNotNull(root.get("endTime"));
        } else {
            timePredicate = cb.isNull(root.get("endTime"));
        }

        query.where(cb.and(examPredicate, timePredicate));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public boolean existsByExamAndStudent(Exam exam, Student student) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(er) FROM ExamResult er WHERE er.exam = :exam AND er.student = :student",
                    Long.class);
            query.setParameter("exam", exam);
            query.setParameter("student", student);
            return query.getSingleResult() > 0;
        } finally {
            entityManager.close();
        }
    }
}
