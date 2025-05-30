package ir.maktab127.repository;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.Exam;
import ir.maktab127.entity.user.Student;
import ir.maktab127.repository.base.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ExamRepository extends CrudRepository<Exam,Long> {
    List<Exam> findByCourse(Course course);

    List<Exam> findByTitleContaining(String title);

    List<Exam> findByStartTimeAfter(LocalDateTime startTime);

    List<Exam> findByStartTimeBefore(LocalDateTime endTime);

    List<Exam> findAvailableExamsForStudent(Student student, LocalDateTime currentTime);
}
