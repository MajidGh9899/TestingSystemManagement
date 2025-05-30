package ir.maktab127.Controller;

import ir.maktab127.entity.*;
import ir.maktab127.service.AnswerService;

import java.util.List;
import java.util.Optional;

public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    public List<Answer> findByExamResult(ExamResult examResult) {
        if (examResult == null) {
            throw new IllegalArgumentException("Exam result cannot be null");
        }
        return answerService.findByExamResult(examResult);
    }

    public Optional<Answer> findByExamResultAndQuestion(ExamResult examResult, Question question) {
        if (examResult == null) {
            throw new IllegalArgumentException("Exam result cannot be null");
        }
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        return answerService.findByExamResultAndQuestion(examResult, question);
    }

    public MultipleChoiceAnswer createMultipleChoiceAnswer(Question question, Integer selectedOption) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        if (!(question instanceof MultipleChoiceQuestion)) {
            throw new IllegalArgumentException("Question must be a multiple choice question");
        }
        if (selectedOption == null) {
            throw new IllegalArgumentException("Selected option cannot be null");
        }
        MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
        if (selectedOption < 0 || selectedOption >= mcQuestion.getOptions().size()) {
            throw new IllegalArgumentException("Invalid selected option index");
        }
        return answerService.createMultipleChoiceAnswer(question, selectedOption);
    }

    public DescriptiveAnswer createDescriptiveAnswer(Question question, String content) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        if (!(question instanceof DescriptiveQuestion)) {
            throw new IllegalArgumentException("Question must be a descriptive question");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        
        return answerService.createDescriptiveAnswer(question, content);
    }

    public void gradeAnswer(Answer answer, Double score) {
        if (answer == null) {
            throw new IllegalArgumentException("Answer cannot be null");
        }
        if (score == null) {
            throw new IllegalArgumentException("Score cannot be null");
        }
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        if (!(answer instanceof DescriptiveAnswer)) {
            throw new IllegalArgumentException("Only descriptive answers can be graded");
        }
        answerService.gradeAnswer(answer, score);
    }
} 