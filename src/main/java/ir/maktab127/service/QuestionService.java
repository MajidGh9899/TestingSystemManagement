package ir.maktab127.service;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.DescriptiveQuestion;
import ir.maktab127.entity.MultipleChoiceQuestion;
import ir.maktab127.entity.Question;
import ir.maktab127.service.base.BaseService;

import java.util.List;

public interface QuestionService extends BaseService<Question, Long> {

    List<Question> findByCourse(Course course);

    List<Question> findByTitleContaining(String title);

    List<Question> findByContentContaining(String content);

    List<Question> findByCourseAndTitleContaining(Course course, String title);

    MultipleChoiceQuestion createMultipleChoiceQuestion(String title, String content, Course course, List<String> options, int correctOptionIndex);

    DescriptiveQuestion createDescriptiveQuestion(String title, String content, Course course);

    void addQuestionToCourse(Question question, Course course);

    void removeQuestionFromCourse(Question question);
}
