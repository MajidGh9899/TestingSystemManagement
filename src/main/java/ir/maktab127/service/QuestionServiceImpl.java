package ir.maktab127.service;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.DescriptiveQuestion;
import ir.maktab127.entity.MultipleChoiceQuestion;
import ir.maktab127.entity.Question;
import ir.maktab127.repository.QuestionRepository;
import ir.maktab127.service.base.BaseServiceImpl;

import java.util.List;

public class QuestionServiceImpl extends BaseServiceImpl<Question, Long, QuestionRepository> implements QuestionService {
    public QuestionServiceImpl(QuestionRepository repository) {
        super(repository);
    }

    @Override
    public List<Question> findByCourse(Course course) {

            try {
                repository.beginTransaction();
                List<Question> questions = repository.findByCourse(course);
                repository.commitTransaction();
                return questions;
            }catch (RuntimeException e) {
                if(repository.isTransactionActive()){
                    repository.rollbackTransaction();
                }
                throw new RuntimeException(e);
            }

    }

    @Override
    public List<Question> findByTitleContaining(String title) {


             try {
                 repository.beginTransaction();
                 List<Question> questions = repository.findByTitleContaining(title);
                 repository.commitTransaction();
                 return questions;
             }catch (RuntimeException e) {
                 if(repository.isTransactionActive()){
                     repository.rollbackTransaction();
                 }
                 throw new RuntimeException(e);
             }

    }

    @Override
    public List<Question> findByContentContaining(String content) {


             try {
                 repository.beginTransaction();
                 List<Question> questions = repository.findByContentContaining(content);
                 repository.commitTransaction();
                 return questions;
             }catch (RuntimeException e) {
                 if(repository.isTransactionActive()){
                     repository.rollbackTransaction();
                 }
                 throw new RuntimeException(e);
             }

    }

    @Override
    public List<Question> findByCourseAndTitleContaining(Course course, String title) {


             try {
                 repository.beginTransaction();
                 List<Question> questions = repository.findByCourseAndTitleContaining(course,title);
                 repository.commitTransaction();
                  return questions;
             }catch (RuntimeException e) {
                 if(repository.isTransactionActive()){
                     repository.rollbackTransaction();
                 }
                 throw new RuntimeException(e);
             }

    }

    @Override
    public MultipleChoiceQuestion createMultipleChoiceQuestion(String title, String content, Course course, List<String> options, int correctOptionIndex) {

              try {
                  MultipleChoiceQuestion question = new MultipleChoiceQuestion(title, content);
                  options.forEach(question::addOption);
                  question.setCorrectOptionIndex(correctOptionIndex);
                  question.setCourse(course);
                  course.addQuestion(question);
                  repository.beginTransaction();
                  repository.save(question);
                  repository.commitTransaction();
                  return question;
              }catch (RuntimeException e) {
                  if(repository.isTransactionActive()){
                      repository.rollbackTransaction();
                  }
                  throw new RuntimeException(e);
              }


    }

    @Override
    public DescriptiveQuestion createDescriptiveQuestion(String title, String content, Course course) {
            try {
                DescriptiveQuestion question = new DescriptiveQuestion(title, content);
                question.setCourse(course);
                course.addQuestion(question);
                repository.beginTransaction();
                repository.save(question);
                repository.commitTransaction();
                return question;
            }catch (RuntimeException e) {
                if(repository.isTransactionActive()){
                    repository.rollbackTransaction();
                }
                throw new RuntimeException(e);
            }

    }

    @Override
    public void addQuestionToCourse(Question question, Course course) {

            if(!course.getQuestionBank().contains(question)){
            try {
                question.setCourse(course);
                course.addQuestion(question);
                repository.beginTransaction();
                repository.save(question);
                repository.commitTransaction();
            }catch (RuntimeException e) {
                if(repository.isTransactionActive()){
                    repository.rollbackTransaction();
                }
                throw new RuntimeException(e);
            }

            }else{
                throw new RuntimeException("Question is already in this course");
            }




    }

    @Override
    public void removeQuestionFromCourse(Question question) {


        try {
            Course course = question.getCourse();
            course.removeQuestion(question);
            question.getCourse().removeQuestion(question);
            question.setCourse(null);
            repository.beginTransaction();
            repository.save(question);
            repository.commitTransaction();


        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();

            }
            throw new RuntimeException(e);
        }

    }
}
