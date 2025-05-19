package ir.maktab127.repository;

import ir.maktab127.entity.Exam;
import ir.maktab127.entity.ExamResult;
import ir.maktab127.entity.user.Student;
import ir.maktab127.repository.base.CrudRepository;

import java.util.List;
import java.util.Optional;
public interface ExamResultRepository extends CrudRepository<ExamResult,Long> {
    List<ExamResult> findByExam(Exam exam);

    List<ExamResult> findByStudent(Student student);

    Optional<ExamResult> findByExamAndStudent(Exam exam, Student student);

    List<ExamResult> findByExamAndCompletedStatus(Exam exam, boolean completed);

    List<ExamResult> findByStudentAndCompletedStatus(Student student, boolean completed);

    boolean existsByExamAndStudent(Exam exam, Student student);
}
