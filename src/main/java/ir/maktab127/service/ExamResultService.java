package ir.maktab127.service;

import ir.maktab127.entity.Answer;
import ir.maktab127.entity.Exam;
import ir.maktab127.entity.ExamResult;
import ir.maktab127.entity.Question;
import ir.maktab127.entity.user.Student;
import ir.maktab127.service.base.BaseService;

import java.util.List;
import java.util.Optional;
public interface ExamResultService extends BaseService<ExamResult, Long> {

    List<ExamResult> findByExam(Exam exam);

    List<ExamResult> findByStudent(Student student);

    Optional<ExamResult> findByExamAndStudent(Exam exam, Student student);

    List<ExamResult> findByExamAndCompletedStatus(Exam exam, boolean completed);

    List<ExamResult> findByStudentAndCompletedStatus(Student student, boolean completed);

    boolean existsByExamAndStudent(Exam exam, Student student);

    ExamResult startExam(Exam exam, Student student);

    void completeExam(ExamResult examResult);

    void submitAnswer(ExamResult examResult, Question question, Answer answer);

    void gradeMultipleChoiceAnswers(ExamResult examResult);

    void gradeDescriptiveAnswer(ExamResult examResult, Question question, Double score);

    void calculateTotalScore(ExamResult examResult);
}
