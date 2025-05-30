package ir.maktab127.Controller;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.Exam;
import ir.maktab127.entity.Question;
import ir.maktab127.entity.user.Student;
import ir.maktab127.service.ExamService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    public List<Exam> findByCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        return examService.findByCourse(course);
    }

    public List<Exam> findByTitleContaining(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        
        return examService.findByTitleContaining(title);
    }

    public List<Exam> findByStartTimeAfter(LocalDateTime startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be null");
        }
        
        return examService.findByStartTimeAfter(startTime);
    }

    public List<Exam> findByStartTimeBefore(LocalDateTime endTime) {
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be null");
        }
        
        return examService.findByStartTimeBefore(endTime);
    }

    public List<Exam> findAvailableExamsForStudent(Student student, LocalDateTime currentTime) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (currentTime == null) {
            throw new IllegalArgumentException("Current time cannot be null");
        }
        return examService.findAvailableExamsForStudent(student, currentTime);
    }

    public void addQuestion(Exam exam, Question question, Double score) {
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null");
        }
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        if (score == null) {
            throw new IllegalArgumentException("Score cannot be null");
        }
        if (score <= 0) {
            throw new IllegalArgumentException("Score must be greater than 0");
        }
        examService.addQuestion(exam, question, score);
    }

    public void removeQuestion(Exam exam, Question question) {
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null");
        }
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        examService.removeQuestion(exam, question);
    }

    public Map<Question, Double> getExamQuestions(Exam exam) {
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null");
        }
        return examService.getExamQuestions(exam);
    }

    public Double getTotalScore(Exam exam) {
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null");
        }
        return examService.getTotalScore(exam);
    }
} 