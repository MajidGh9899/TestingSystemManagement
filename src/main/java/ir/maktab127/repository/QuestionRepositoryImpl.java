package ir.maktab127.repository;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.Question;
import ir.maktab127.repository.base.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class QuestionRepositoryImpl extends CrudRepositoryImpl<Question,Long> implements QuestionRepository {
    protected QuestionRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Question> findByCourse(Course course) {
        try {
            TypedQuery<Question> query = entityManager.createQuery(
                    "SELECT q FROM Question q WHERE q.course = :course", Question.class);
            query.setParameter("course", course);
            return query.getResultList();
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }}

    @Override
    public List<Question> findByTitleContaining(String title) {
        try {
            TypedQuery<Question> query = entityManager.createQuery(
                    "SELECT q FROM Question q WHERE LOWER(q.title) LIKE LOWER(:title)", Question.class);
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
        }

    @Override
    public List<Question> findByContentContaining(String content) {
        try {
            TypedQuery<Question> query = entityManager.createQuery(
                    "SELECT q FROM Question q WHERE LOWER(q.content) LIKE LOWER(:content)", Question.class);
            query.setParameter("content", "%" + content + "%");
            return query.getResultList();
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Question> findByCourseAndTitleContaining(Course course, String title) {
        try {
            TypedQuery<Question> query = entityManager.createQuery(
                    "SELECT q FROM Question q WHERE q.course = :course AND LOWER(q.title) LIKE LOWER(:title)",
                    Question.class);
            query.setParameter("course", course);
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Class<Question> getDomainClass() {
        return Question.class;
    }
}
