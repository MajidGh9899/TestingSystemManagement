package ir.maktab127.Controller;

import ir.maktab127.entity.*;
import ir.maktab127.entity.user.Student;
import ir.maktab127.service.ExamResultService;

import java.util.List;
import java.util.Optional;

public class ExamResultController {
    private final ExamResultService examResultService;

    public ExamResultController(ExamResultService examResultService) {
        this.examResultService = examResultService;
    }

    public List<ExamResult> findByExam(Exam exam) {
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null");
        }
        return examResultService.findByExam(exam);
    }

    public List<ExamResult> findByStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        return examResultService.findByStudent(student);
    }

    public Optional<ExamResult> findByExamAndStudent(Exam exam, Student student) {
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null");
        }
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        return examResultService.findByExamAndStudent(exam, student);
    }

    public List<ExamResult> findByExamAndCompletedStatus(Exam exam, boolean completed) {
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null");
        }
        return examResultService.findByExamAndCompletedStatus(exam, completed);
    }

    public List<ExamResult> findByStudentAndCompletedStatus(Student student, boolean completed) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        return examResultService.findByStudentAndCompletedStatus(student, completed);
    }

    public boolean existsByExamAndStudent(Exam exam, Student student) {
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null");
        }
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        return examResultService.existsByExamAndStudent(exam, student);
    }

    public ExamResult startExam(Exam exam, Student student) {
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null");
        }
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (examResultService.existsByExamAndStudent(exam, student)) {
            throw new IllegalArgumentException("Student has already started this exam");
        }
        return examResultService.startExam(exam, student);
    }

    public void completeExam(ExamResult examResult) {
        if (examResult == null) {
            throw new IllegalArgumentException("Exam result cannot be null");
        }
        if (examResult.isCompleted()) {
            throw new IllegalArgumentException("Exam is already completed");
        }
        examResultService.completeExam(examResult);
    }

    public void submitAnswer(ExamResult examResult, Question question, Answer answer) {
        if (examResult == null) {
            throw new IllegalArgumentException("Exam result cannot be null");
        }
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        if (answer == null) {
            throw new IllegalArgumentException("Answer cannot be null");
        }
        if (examResult.isCompleted()) {
            throw new IllegalArgumentException("Cannot submit answer for completed exam");
        }
        examResultService.submitAnswer(examResult, question, answer);
    }

    public void gradeMultipleChoiceAnswers(ExamResult examResult) {
        if (examResult == null) {
            throw new IllegalArgumentException("Exam result cannot be null");
        }
        if (!examResult.isCompleted()) {
            throw new IllegalArgumentException("Cannot grade answers for incomplete exam");
        }
        examResultService.gradeMultipleChoiceAnswers(examResult);
    }

    public void gradeDescriptiveAnswer(ExamResult examResult, Question question, Double score) {
        if (examResult == null) {
            throw new IllegalArgumentException("Exam result cannot be null");
        }
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        if (score == null) {
            throw new IllegalArgumentException("Score cannot be null");
        }
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        if (!examResult.isCompleted()) {
            throw new IllegalArgumentException("Cannot grade answers for incomplete exam");
        }
        if (!(question instanceof DescriptiveQuestion)) {
            throw new IllegalArgumentException("Question must be a descriptive question");
        }
        examResultService.gradeDescriptiveAnswer(examResult, question, score);
    }

    public void calculateTotalScore(ExamResult examResult) {
        if (examResult == null) {
            throw new IllegalArgumentException("Exam result cannot be null");
        }
        if (!examResult.isCompleted()) {
            throw new IllegalArgumentException("Cannot calculate score for incomplete exam");
        }
        examResultService.calculateTotalScore(examResult);
    }
} 