package ir.maktab127.service;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Student;
import ir.maktab127.entity.user.Teacher;
import ir.maktab127.repository.CourseRepository;
import ir.maktab127.repository.user.StudentRepository;
import ir.maktab127.repository.user.UserRepository;
import ir.maktab127.service.base.BaseServiceImpl;

import java.util.List;
import java.util.Optional;

public class StudentServiceImpl extends BaseServiceImpl<Student, Long, StudentRepository> implements StudentService {
    private final UserRepository<Student> userRepository;
    private final CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository repository, UserRepository<Student> userRepository , CourseRepository courseRepository) {
        super(repository);
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Optional<Student> findByUsername(String username) {
        try{
            repository.beginTransaction();
            Optional<Student> student = userRepository.findByUsername(username)
                    .filter(user->user instanceof Student)
                    .map(user->(Student) user);
            repository.commitTransaction();
            return student;

        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> findByCourse(Course course) {
         try {
             repository.beginTransaction();
             List<Student> students = repository.findByCourse(course);
             repository.commitTransaction();
             return students;
         } catch (RuntimeException e) {
             if(repository.isTransactionActive()){
                 repository.rollbackTransaction();
             }
             throw new RuntimeException(e);
         }
    }

    @Override
    public void enrollInCourse(Student student, Course course) {

            if(!repository.existsByStudentAndCourse(student,course)){
            try {
                course.addStudent(student);
                student.addCourse(course);
                repository.beginTransaction();
                repository.save(student);
                repository.commitTransaction();

            }catch (RuntimeException e) {
                if(repository.isTransactionActive()){
                    repository.rollbackTransaction();
                }
            }
        }



    }

    @Override
    public void unenrollFromCourse(Student student, Course course) {

            if(!course.getStudents().contains(student)){
                throw new RuntimeException("Student is not enrolled in this course");

            }
            try {
                course.removeStudent(student);
                courseRepository.beginTransaction();
                courseRepository.save(course);
                courseRepository.commitTransaction();


            }catch (RuntimeException e) {
                if(courseRepository.isTransactionActive()){
                    courseRepository.rollbackTransaction();
                }
            }



    }
}
