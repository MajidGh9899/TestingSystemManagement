package ir.maktab127.service;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.Exam;
import ir.maktab127.entity.Question;
import ir.maktab127.entity.QuestionScore;
import ir.maktab127.entity.user.Student;
import ir.maktab127.repository.ExamRepository;
import ir.maktab127.repository.QuestionScoreRepository;
import ir.maktab127.service.base.BaseServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExamServiceImpl extends BaseServiceImpl<Exam, Long, ExamRepository> implements ExamService {
    QuestionScoreRepository  questionScoreRepository;
    public ExamServiceImpl(ExamRepository repository, QuestionScoreRepository questionScoreRepository  ) {
        super(repository);
        this.questionScoreRepository = questionScoreRepository;
    }

    @Override
    public List<Exam> findByCourse(Course course) {


        List<Exam> exams;
        try {
            repository.beginTransaction();
             exams = repository.findByCourse(course);
            repository.commitTransaction();

        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return exams;
    }

    @Override
    public List<Exam> findByTitleContaining(String title) {

        List<Exam> exams;
        try {
            repository.beginTransaction();
            exams = repository.findByTitleContaining(title);
            repository.commitTransaction();
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return exams;
    }

    @Override
    public List<Exam> findByStartTimeAfter(LocalDateTime startTime) {

        List<Exam> exams;
        try {
            repository.beginTransaction();
            exams = repository.findByStartTimeAfter(startTime);
            repository.commitTransaction();

        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return exams;
    }

    @Override
    public List<Exam> findByStartTimeBefore(LocalDateTime endTime) {

        List<Exam> exams;
        try {
            repository.beginTransaction();
            exams = repository.findByStartTimeBefore(endTime);
            repository.commitTransaction();
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return exams;
    }

    @Override
    public List<Exam> findAvailableExamsForStudent(Student student, LocalDateTime currentTime) {


        List<Exam> exams;
        try {
            repository.beginTransaction();
            exams = repository.findAvailableExamsForStudent(student,currentTime);
            repository.commitTransaction();

        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return exams;
    }

    @Override
    public void addQuestion(Exam exam, Question question, Double score) {


        try {
            repository.beginTransaction();
            exam.addQuestion(question);
            question.getExams().add(exam);
            repository.save(exam);
            repository.commitTransaction();
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        try {
            questionScoreRepository.beginTransaction();
            Optional<QuestionScore> question1 = questionScoreRepository.findByExamAndQuestion(exam,question);
            QuestionScore questionScore;
            if (question1.isPresent()){
                questionScore = question1.get();
                questionScore.setScore(score);

            }
            else{
                questionScore = new QuestionScore(exam, question, score);
            }
            questionScoreRepository.save(questionScore);
            questionScoreRepository.commitTransaction();

        }catch (RuntimeException e) {
            if(questionScoreRepository.isTransactionActive()){
                questionScoreRepository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeQuestion(Exam exam, Question question) {

              if(!exam.getQuestions().contains(question)){
                  throw new RuntimeException("Question is not in this exam");
              }
        try {
            exam.removeQuestion(question);
            question.getExams().remove(exam);
            repository.beginTransaction();
            repository.save(exam);
            repository.commitTransaction();
            questionScoreRepository.beginTransaction();
            Optional<QuestionScore> question1 = questionScoreRepository.findByExamAndQuestion(exam,question);
            question1.ifPresent(questionScore -> questionScoreRepository.deleteById(questionScore.getId()));

        }catch (RuntimeException e) {
            if(repository.isTransactionActive()||questionScoreRepository.isTransactionActive()){
                repository.rollbackTransaction();
                questionScoreRepository.rollbackTransaction();

            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Question, Double> getExamQuestions(Exam exam) {

        List<QuestionScore> questions;
        try {
            questionScoreRepository.beginTransaction();
            questions = questionScoreRepository.findByExam(exam);
            questionScoreRepository.commitTransaction();


        }catch (RuntimeException e) {
            if(questionScoreRepository.isTransactionActive()){
                questionScoreRepository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return questions.stream().collect(
                Collectors.toMap(QuestionScore::getQuestion, QuestionScore::getScore));
    }

    @Override
    public Double getTotalScore(Exam exam) {


        List<QuestionScore> questions;
        try {
            questionScoreRepository.beginTransaction();
            questions = questionScoreRepository.findByExam(exam);
            questionScoreRepository.commitTransaction();
        }catch (RuntimeException e) {
            if(questionScoreRepository.isTransactionActive()){
                questionScoreRepository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return questions.stream()
                .mapToDouble(QuestionScore::getScore).sum();
    }
}
