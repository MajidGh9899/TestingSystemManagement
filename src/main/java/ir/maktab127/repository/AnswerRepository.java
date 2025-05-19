package ir.maktab127.repository;

import ir.maktab127.entity.Answer;
import ir.maktab127.entity.ExamResult;
import ir.maktab127.entity.Question;
import ir.maktab127.repository.base.CrudRepository;

import java.util.List;
import java.util.Optional;
public interface AnswerRepository extends CrudRepository<Answer,Long> {
    List<Answer> findByExamResult(ExamResult examResult);

    Optional<Answer> findByExamResultAndQuestion(ExamResult examResult, Question question);

    List<Answer> findByQuestionIn(List<Question> questions);

    boolean existsByExamResultAndQuestion(ExamResult examResult, Question question);
}
