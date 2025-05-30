package ir.maktab127.service;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Teacher;
import ir.maktab127.repository.CourseRepository;
import ir.maktab127.repository.user.TeacherRepository;
import ir.maktab127.repository.user.UserRepository;
import ir.maktab127.service.base.BaseServiceImpl;

import java.util.List;
import java.util.Optional;

public class TeacherServiceImpl extends BaseServiceImpl<Teacher, Long, TeacherRepository> implements TeacherService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    public TeacherServiceImpl(TeacherRepository repository, UserRepository userRepository, CourseRepository courseRepository) {
        super(repository);
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Optional<Teacher> findByUsername(String username) {

        try{
            repository.beginTransaction();
            Optional<Teacher> teacher = userRepository.findByUsername(username)
                    .filter(user->user instanceof Teacher)
                    .map(user->(Teacher)user);
            repository.commitTransaction();
            return teacher;

        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> findCourses(Teacher teacher) {
        try {
            repository.beginTransaction();
            List<Course> courses = courseRepository.findByTeacher(teacher);
            repository.commitTransaction();
            return courses;
        } catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);

        }
    }

    @Override
    public void assignCourse(Teacher teacher, Course course) {

                try {
                    course.setTeacher(teacher);
                    repository.beginTransaction();
                    courseRepository.save(course);
                    repository.commitTransaction();
                } catch (RuntimeException e) {
                    if(repository.isTransactionActive()){
                        repository.rollbackTransaction();
                    }
                    throw new RuntimeException(e);
                }

    }

    @Override
    public void unassignCourse(Teacher teacher, Course course) {
            if(course.getTeacher().getId().equals(teacher.getId())) {
                try {
                    course.setTeacher(null);
                    repository.beginTransaction();
                    courseRepository.save(course);

                    repository.commitTransaction();

                } catch (RuntimeException e) {
                    if(repository.isTransactionActive()){
                        repository.rollbackTransaction();
                    }
                    throw new RuntimeException(e);
                }
            }
    }
}
