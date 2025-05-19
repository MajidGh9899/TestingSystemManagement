package ir.maktab127.repository;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.Question;
import ir.maktab127.repository.base.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question,Long> {
    List<Question> findByCourse(Course course);

    List<Question> findByTitleContaining(String title);

    List<Question> findByContentContaining(String content);

    List<Question> findByCourseAndTitleContaining(Course course, String title);
}
