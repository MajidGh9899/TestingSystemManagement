package ir.maktab127.entity;

import ir.maktab127.entity.base.BaseEntity;
import ir.maktab127.entity.user.Student;
import ir.maktab127.entity.user.Teacher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Course.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
public class Course extends BaseEntity<Long> {
    public static final String TABLE_NAME = "courses";
    public static final String COURSE_NAME = "course_name";
    public static final String COURSE_CODE = "course_code";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String TEACHER = "teacher";
    public static final String STUDENTS_COURSE = "students_course";

    @Column(name = COURSE_NAME, nullable = false)
    private String title;
    @Column(name = COURSE_CODE, nullable = false,unique = true)
    private String code;
    @Column(name = START_DATE, nullable = false)
    private LocalDate startDate;
    @Column(name = END_DATE, nullable = false)
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = TEACHER, nullable = false)
    private Teacher teacher;
    @ManyToMany
    @JoinTable(name = STUDENTS_COURSE,
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "course" ,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Exam> exams=new HashSet<>();
    @OneToMany(mappedBy = "course" ,cascade = CascadeType.ALL,orphanRemoval = true)

    private Set<Question> questionBank=new HashSet<>();

    public Course(String title, String code, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;

    }
    public void addStudent(Student student) {
        students.add(student);
        student.getCourses().add(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.getCourses().remove(this);
    }
    public void addExam(Exam exam) {
        exams.add(exam);
        exam.setCourse(this);
    }

    public void removeExam(Exam exam) {
        exams.remove(exam);
        exam.setCourse(null);
    }
    public void addQuestion(Question question) {
        questionBank.add(question);
        question.setCourse(this);
    }

    public void removeQuestion(Question question) {
        questionBank.remove(question);
        question.setCourse(null);
    }

}
