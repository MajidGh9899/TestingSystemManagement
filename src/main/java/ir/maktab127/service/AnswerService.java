package ir.maktab127.service;

import ir.maktab127.entity.*;
import ir.maktab127.service.base.BaseService;

import java.util.List;
import java.util.Optional;
public interface AnswerService extends BaseService<Answer, Long> {

    List<Answer> findByExamResult(ExamResult examResult);

    Optional<Answer> findByExamResultAndQuestion(ExamResult examResult, Question question);

    MultipleChoiceAnswer createMultipleChoiceAnswer(Question question, Integer selectedOption);

    DescriptiveAnswer createDescriptiveAnswer(Question question, String content);

    void gradeAnswer(Answer answer, Double score);
}
