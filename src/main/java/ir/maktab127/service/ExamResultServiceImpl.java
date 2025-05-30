package ir.maktab127.service;

import ir.maktab127.entity.*;
import ir.maktab127.entity.user.Student;
import ir.maktab127.repository.AnswerRepository;
import ir.maktab127.repository.ExamResultRepository;
import ir.maktab127.repository.QuestionScoreRepository;
import ir.maktab127.repository.QuestionScoreRepositoryImpl;
import ir.maktab127.service.base.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamResultServiceImpl extends BaseServiceImpl<ExamResult, Long, ExamResultRepository> implements ExamResultService {

    private final AnswerRepository  answerRepository;
    private final QuestionScoreRepository  questionScoreRepository;
    private final ExamService  examService;

    public ExamResultServiceImpl(ExamResultRepository repository, AnswerRepository answerRepository, QuestionScoreRepository questionScoreRepository, ExamService examService) {
        super(repository);
        this.answerRepository = answerRepository;
        this.questionScoreRepository = questionScoreRepository;
        this.examService = examService;
    }


    @Override
    public List<ExamResult> findByExam(Exam exam) {

        List<ExamResult> examResults=new ArrayList<>() ;
        try {
            repository.beginTransaction();
            examResults = repository.findByExam(exam);
            repository.commitTransaction();

        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return examResults;
    }

    @Override
    public List<ExamResult> findByStudent(Student student) {

        List<ExamResult> examResults=new ArrayList<>() ;
        try {
            repository.beginTransaction();
            examResults = repository.findByStudent(student);
            repository.commitTransaction();
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return examResults;
    }

    @Override
    public Optional<ExamResult> findByExamAndStudent(Exam exam, Student student) {

        Optional<ExamResult> examResult;
        try {
            repository.beginTransaction();
            examResult = repository.findByExamAndStudent(exam,student);
            repository.commitTransaction();
        } catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return examResult;
    }

    @Override
    public List<ExamResult> findByExamAndCompletedStatus(Exam exam, boolean completed) {


        List<ExamResult> examResults;
        try {
            repository.beginTransaction();
            examResults = repository.findByExamAndCompletedStatus(exam,completed);
            repository.commitTransaction();

        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return examResults;
    }

    @Override
    public List<ExamResult> findByStudentAndCompletedStatus(Student student, boolean completed) {

        List<ExamResult> examResults;
        try {
            repository.beginTransaction();
            examResults = repository.findByStudentAndCompletedStatus(student,completed);
            repository.commitTransaction();
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return examResults;
    }

    @Override
    public boolean existsByExamAndStudent(Exam exam, Student student) {

        boolean exists;
        try {
            repository.beginTransaction();
            exists = repository.existsByExamAndStudent(exam,student);
            repository.commitTransaction();

        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
        return exists;
    }

    @Override
    public ExamResult startExam(Exam exam, Student student) {

        Optional<ExamResult> examResult = findByExamAndStudent(exam, student);
       if (examResult.isPresent()) {
           ExamResult examResult1 = examResult.get();
           if (examResult1.isCompleted()) {
               throw new RuntimeException("Student has already completed this exam");
           }
           return examResult1;
       }
       ExamResult examResult2 = new ExamResult(exam, student);
       try {
           repository.beginTransaction();
            repository.save(examResult2);
           repository.commitTransaction();
       }catch (RuntimeException e) {
           if(repository.isTransactionActive()){
               repository.rollbackTransaction();
           }
           throw new RuntimeException(e);
       }
       return examResult2;
    }

    @Override
    public void completeExam(ExamResult examResult) {


        try {
            examResult.complete();
            gradeMultipleChoiceAnswers(examResult);
            calculateTotalScore(examResult);
            repository.beginTransaction();
            repository.save(examResult);
            repository.commitTransaction();
        }
        catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();

            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public void submitAnswer(ExamResult examResult, Question question, Answer answer) {


        if(answerRepository.existsByExamResultAndQuestion(examResult,question)) {
            throw new RuntimeException("Answer already exists");
        }
        answer.setExamResult(examResult);
        answer.setQuestion(question);
        Optional<Answer> answer1;
        try {
            answerRepository.beginTransaction();
            answer1 = answerRepository.findByExamResultAndQuestion(examResult,question);
            answer1.ifPresent(answer2 -> answerRepository.deleteById(answer2.getId()));
            answerRepository.save(answer);
            examResult.addAnswer(answer);
            answerRepository.commitTransaction();



        } catch (RuntimeException e) {
            if(answerRepository.isTransactionActive()){
                answerRepository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gradeMultipleChoiceAnswers(ExamResult examResult) {

            List<Answer> answers;
            try {
                answerRepository.beginTransaction();
                answers = answerRepository.findByExamResult(examResult);
                for(Answer answer: answers) {
                    if(answer instanceof MultipleChoiceAnswer ){
                        MultipleChoiceAnswer multipleChoiceAnswer = (MultipleChoiceAnswer) answer;
                        Question question = multipleChoiceAnswer.getQuestion();
                        if (multipleChoiceAnswer.isCorrect()) {

                            Optional<QuestionScore> question1 = questionScoreRepository.findByExamAndQuestion(examResult.getExam(),question);
                            if (question1.isPresent()){
                                QuestionScore questionScore = question1.get();
                                multipleChoiceAnswer.setScore(questionScore.getScore());
                                answerRepository.save(multipleChoiceAnswer);


                            }
                        }else {
                            multipleChoiceAnswer.setScore(0.0);
                            answerRepository.save(multipleChoiceAnswer);
                            answerRepository.commitTransaction();
                        }
                    }
                }
            }catch (RuntimeException e) {
                if(answerRepository.isTransactionActive()||questionScoreRepository.isTransactionActive()){
                    answerRepository.rollbackTransaction();
                    questionScoreRepository.rollbackTransaction();
                }
                throw new RuntimeException(e);
            }
    }

    @Override
    public void gradeDescriptiveAnswer(ExamResult examResult, Question question, Double score) {


        if(!examResult.getExam().getQuestions().contains(question)) {
            throw new RuntimeException("Question is not in this exam");
        }
        Optional<QuestionScore> questionscore1;
        try {
            questionScoreRepository.beginTransaction();
            questionscore1 = questionScoreRepository.findByExamAndQuestion(examResult.getExam(),question);
            questionScoreRepository.commitTransaction();
            if (questionscore1.isPresent()){
                QuestionScore questionScore = questionscore1.get();
                if(score > questionScore.getScore()) {
                    throw new IllegalArgumentException("Score is higher than the maximum score");
                }
            }else{
                throw new RuntimeException("Question is not in this exam");
            }
            answerRepository.beginTransaction();
            answerRepository.findByExamResultAndQuestion(examResult,question)
                    .filter(answer  -> answer instanceof DescriptiveAnswer)
                    .ifPresent(answer -> {
                        answer.setScore(score);
                        answerRepository.save(answer);
                        calculateTotalScore(examResult);

                    });
            answerRepository.commitTransaction();

        }catch (RuntimeException e) {
            if(questionScoreRepository.isTransactionActive()||answerRepository.isTransactionActive()){
                questionScoreRepository.rollbackTransaction();
                answerRepository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public void calculateTotalScore(ExamResult examResult) {

            List<Answer> answers;
            try {
                repository.beginTransaction();

                answers=answerRepository.findByExamResult(examResult);

                double totalScore = answers.stream()
                        .filter(answer -> answer.getScore() != null)
                        .mapToDouble(Answer::getScore)
                        .sum();
                examResult.setScore(totalScore);

                repository.save(examResult);
                repository.commitTransaction();



            } catch (RuntimeException e) {
                if(answerRepository.isTransactionActive()||repository.isTransactionActive()){
                    answerRepository.rollbackTransaction();
                    repository.rollbackTransaction();
                }
                throw new RuntimeException(e);
            }
    }
}
