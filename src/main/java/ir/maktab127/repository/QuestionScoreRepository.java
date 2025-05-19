package ir.maktab127.repository;

import ir.maktab127.entity.Exam;
import ir.maktab127.entity.Question;
import ir.maktab127.entity.QuestionScore;
import ir.maktab127.repository.base.CrudRepository;

import java.util.List;
import java.util.Optional;
public interface QuestionScoreRepository extends CrudRepository<QuestionScore,Long> {
    List<QuestionScore> findByExam(Exam exam);

    List<QuestionScore> findByQuestion(Question question);

    Optional<QuestionScore> findByExamAndQuestion(Exam exam, Question question);

    boolean existsByExamAndQuestion(Exam exam, Question question);
}
