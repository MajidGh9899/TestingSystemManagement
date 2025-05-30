package ir.maktab127.Controller;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.DescriptiveQuestion;
import ir.maktab127.entity.MultipleChoiceQuestion;
import ir.maktab127.entity.Question;
import ir.maktab127.service.QuestionService;

import java.util.List;

public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    public List<Question> findByCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        return questionService.findByCourse(course);
    }

    public List<Question> findByTitleContaining(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
       
        return questionService.findByTitleContaining(title);
    }

    public List<Question> findByContentContaining(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        
        return questionService.findByContentContaining(content);
    }

    public List<Question> findByCourseAndTitleContaining(Course course, String title) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        
        return questionService.findByCourseAndTitleContaining(course, title);
    }

    public MultipleChoiceQuestion createMultipleChoiceQuestion(String title, String content, Course course, List<String> options, int correctOptionIndex) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
       
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("Options cannot be null or empty");
        }
       
        if (correctOptionIndex < 0 || correctOptionIndex >= options.size()) {
            throw new IllegalArgumentException("Invalid correct option index");
        }
        return questionService.createMultipleChoiceQuestion(title, content, course, options, correctOptionIndex);
    }

    public DescriptiveQuestion createDescriptiveQuestion(String title, String content, Course course) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        return questionService.createDescriptiveQuestion(title, content, course);
    }

    public void addQuestionToCourse(Question question, Course course) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        questionService.addQuestionToCourse(question, course);
    }

    public void removeQuestionFromCourse(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        questionService.removeQuestionFromCourse(question);
    }
} 