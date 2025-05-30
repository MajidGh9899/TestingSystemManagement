package ir.maktab127.service;

import ir.maktab127.entity.Course;
import ir.maktab127.entity.user.Student;
import ir.maktab127.entity.user.Teacher;
import ir.maktab127.repository.CourseRepository;
import ir.maktab127.service.base.BaseService;
import ir.maktab127.service.base.BaseServiceImpl;

import java.util.List;
import java.util.Optional;

public class CourseServiceImpl extends BaseServiceImpl<Course, Long, CourseRepository> implements CourseService {


    public CourseServiceImpl(CourseRepository repository) {
        super(repository);
    }

    @Override
    public Optional<Course> findByCode(String code) {

        try {
            repository.beginTransaction();
            Optional<Course> course = repository.findByCode(code);
            repository.commitTransaction();
            return course;
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> findByTeacher(Teacher teacher) {


        try {
            repository.beginTransaction();
            List<Course> courses = repository.findByTeacher(teacher);
            repository.commitTransaction();
            return courses;
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> findByStudent(Student student) {

        try {
            repository.beginTransaction();
            List<Course> courses = repository.findByStudent(student);
            repository.commitTransaction();
            return courses;
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> findByTitleContaining(String title) {

        try {
            repository.beginTransaction();
            List<Course> courses = repository.findByTitleContaining(title);
            repository.commitTransaction();
            return courses;
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByCode(String code) {


        try {
            repository.beginTransaction();
            boolean exists = repository.existsByCode(code);
            repository.commitTransaction();
            return exists;
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addStudent(Course course, Student student) {

        if(course.getStudents().contains(student)){
            throw new RuntimeException("Student is already in this course");
        }
        try {
            repository.beginTransaction();
            course.addStudent(student);
            student.addCourse(course);
              repository.save(course);
            repository.commitTransaction();
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeStudent(Course course, Student student) {

              if(!course.getStudents().contains(student)){
                  throw new RuntimeException("Student is not in this course");
              }
        try {
            repository.beginTransaction();
            course.removeStudent(student);
            student.removeCourse(course);
            repository.save(course);
            repository.commitTransaction();
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void assignTeacher(Course course, Teacher teacher) {

              if(course.getTeacher() != null){
                  throw new RuntimeException("Course already has a teacher");
              }
              if(teacher.getCourses().contains(course)){
                  throw new RuntimeException("Teacher already teaches this course");
              }
        try {
            repository.beginTransaction();
            course.setTeacher(teacher);
            teacher.addCourse(course);
            repository.save(course);
            repository.commitTransaction();
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }
    public void unassignTeacher(Course course, Teacher teacher) {


        if(!course.getTeacher().equals(teacher)){
            throw new RuntimeException("Teacher does not teach this course");
        }
        try {
            repository.beginTransaction();
            course.setTeacher(null);
            teacher.removeCourse(course);
            repository.save(course);
            repository.commitTransaction();
        }catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }
}
