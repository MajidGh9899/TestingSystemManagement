package ir.maktab127.repository;

import ir.maktab127.entity.Exam;
import ir.maktab127.entity.Question;
import ir.maktab127.entity.QuestionScore;
import ir.maktab127.repository.base.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class QuestionScoreRepositoryImpl extends CrudRepositoryImpl<QuestionScore,Long> implements QuestionScoreRepository {

    protected QuestionScoreRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<QuestionScore> findByExam(Exam exam) {
        try {
            TypedQuery<QuestionScore> query = entityManager.createQuery(
                    "SELECT qs FROM QuestionScore qs WHERE qs.exam = :exam", QuestionScore.class);
            query.setParameter("exam", exam);
            return query.getResultList();
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<QuestionScore> findByQuestion(Question question) {
        try {
            TypedQuery<QuestionScore> query = entityManager.createQuery(
                    "SELECT qs FROM QuestionScore qs WHERE qs.question = :question", QuestionScore.class);
            query.setParameter("question", question);
            return query.getResultList();
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<QuestionScore> findByExamAndQuestion(Exam exam, Question question) {
        try {
            TypedQuery<QuestionScore> query = entityManager.createQuery(
                    "SELECT qs FROM QuestionScore qs WHERE qs.exam = :exam AND qs.question = :question",
                    QuestionScore.class);
            query.setParameter("exam", exam);
            query.setParameter("question", question);
            try {
                QuestionScore questionScore = query.getSingleResult();
                return Optional.of(questionScore);
            } catch (NoResultException e) {
                return Optional.empty();
            }
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByExamAndQuestion(Exam exam, Question question) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(qs) FROM QuestionScore qs WHERE qs.exam = :exam AND qs.question = :question",
                    Long.class);
            query.setParameter("exam", exam);
            query.setParameter("question", question);
            return query.getSingleResult() > 0;
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Class<QuestionScore> getDomainClass() {
        return QuestionScore.class;
    }
}
