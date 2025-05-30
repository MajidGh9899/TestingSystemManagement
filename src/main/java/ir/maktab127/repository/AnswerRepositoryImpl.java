package ir.maktab127.repository;

import ir.maktab127.entity.Answer;
import ir.maktab127.entity.ExamResult;
import ir.maktab127.entity.Question;
import ir.maktab127.repository.base.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class AnswerRepositoryImpl extends CrudRepositoryImpl<Answer,Long> implements AnswerRepository {
    protected AnswerRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Answer> findByExamResult(ExamResult examResult) {

        try {
            TypedQuery<Answer> query = entityManager.createQuery(
                    "SELECT a FROM Answer a WHERE a.examResult = :examResult",getDomainClass());
            query.setParameter("examResult",examResult);
            return query.getResultList();
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Answer> findByExamResultAndQuestion(ExamResult examResult, Question question) {
        try {
            TypedQuery<Answer> query = entityManager.createQuery(
                    "SELECT a FROM Answer a WHERE a.examResult = :examResult AND a.question = :question",getDomainClass());
            query.setParameter("examResult",examResult);
            query.setParameter("question",question);
            try {
                Answer answer = query.getSingleResult();
                return Optional.of(answer);
            }catch (NoResultException e){
                return Optional.empty();
            }
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Answer> findByQuestionIn(List<Question> questions) {

        try {
            TypedQuery<Answer> query = entityManager.createQuery(
                    "SELECT a FROM Answer a WHERE a.question IN :questions",getDomainClass());
            query.setParameter("questions",questions);
            return query.getResultList();

        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByExamResultAndQuestion(ExamResult examResult, Question question) {
        try{
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(a) FROM Answer a WHERE a.examResult = :examResult AND a.question = :question",Long.class);
            query.setParameter("examResult",examResult);
            query.setParameter("question",question);
            return query.getSingleResult()>0;
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Class<Answer> getDomainClass() {
        return Answer.class;
    }
}
