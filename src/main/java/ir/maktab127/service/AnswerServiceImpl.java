package ir.maktab127.service;

import ir.maktab127.entity.*;
import ir.maktab127.repository.AnswerRepository;
import ir.maktab127.service.base.BaseServiceImpl;

import java.util.List;
import java.util.Optional;

public class AnswerServiceImpl extends BaseServiceImpl<Answer, Long, AnswerRepository> implements AnswerService {
    public AnswerServiceImpl(AnswerRepository repository) {
        super(repository);
    }

    @Override
    public List<Answer> findByExamResult(ExamResult examResult) {

            try {
                repository.beginTransaction();
                List<Answer> answers = repository.findByExamResult(examResult);
                repository.commitTransaction();
                return answers;
            }catch (RuntimeException e) {
                if(repository.isTransactionActive()){
                    repository.rollbackTransaction();
                }
                throw new RuntimeException(e);
            }

    }

    @Override
    public Optional<Answer> findByExamResultAndQuestion(ExamResult examResult, Question question) {

        try {
            repository.beginTransaction();
            Optional<Answer> answer = repository.findByExamResultAndQuestion(examResult,question);
            repository.commitTransaction();
            return answer;
        }catch (RuntimeException e){
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public MultipleChoiceAnswer createMultipleChoiceAnswer(Question question, Integer selectedOption) {

        if(!(question instanceof MultipleChoiceQuestion mcQuestion)){
            throw new IllegalArgumentException("Question is not a multiple choice question");
        }
        if(selectedOption != null && (selectedOption < 0 || selectedOption >= mcQuestion.getOptions().size())){
            throw new IllegalArgumentException("Selected option index out of bounds");
        }
        MultipleChoiceAnswer answer=new MultipleChoiceAnswer(question,selectedOption);
        if(mcQuestion.isCorrect(selectedOption)){
            answer.setScore(1.0);
        }else {
            answer.setScore(0.0);
        }
        return answer;

    }

    @Override
    public DescriptiveAnswer createDescriptiveAnswer(Question question, String content) {

        if(!(question instanceof DescriptiveQuestion)){
            throw new IllegalArgumentException("Question is not a descriptive question");
        }


        return new DescriptiveAnswer(question,content);

    }

    @Override
    public void gradeAnswer(Answer answer, Double score) {

        if(score < 0 ){
            throw new IllegalArgumentException("Score is negative");
        }
        answer.setScore(score);
        repository.save(answer);

    }
}
