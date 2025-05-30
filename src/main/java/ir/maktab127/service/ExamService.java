package ir.maktab127.service;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.Exam;
import ir.maktab127.entity.Question;
import ir.maktab127.entity.user.Student;
import ir.maktab127.service.base.BaseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ExamService extends BaseService<Exam, Long> {

    List<Exam> findByCourse(Course course);

    List<Exam> findByTitleContaining(String title);

    List<Exam> findByStartTimeAfter(LocalDateTime startTime);

    List<Exam> findByStartTimeBefore(LocalDateTime endTime);

    List<Exam> findAvailableExamsForStudent(Student student, LocalDateTime currentTime);

    void addQuestion(Exam exam, Question question, Double score);

    void removeQuestion(Exam exam, Question question);

    Map<Question, Double> getExamQuestions(Exam exam);

    Double getTotalScore(Exam exam);
}
