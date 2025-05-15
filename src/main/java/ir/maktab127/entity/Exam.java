package ir.maktab127.entity;

import ir.maktab127.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = Exam.TABLE_NAME)
@NoArgsConstructor

public class Exam extends BaseEntity<Long> {
    public static final String TABLE_NAME = "exams";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DURATION_MINUTES = "duration_minutes";
    public static final String START_TIME = "start_time";
    public static final String COURSE = "course";
    public static final String QUESTIONS = "exam_questions";
    public static final String RESULTS = "results";


    @Column(name = TITLE, nullable = false)
    private String title;
    @Column(name = DESCRIPTION)
    private String description;
    @Column(name = DURATION_MINUTES, nullable = false)
    private int durationMinutes;
    @Column(name = START_TIME, nullable = false)

    private LocalDateTime startTime;
    @ManyToOne
    @JoinColumn(name = COURSE, nullable = false)
    private Course course;
    @ManyToMany
    @JoinTable(name = QUESTIONS,
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))

    private Set<Question> questions = new HashSet<>();
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExamResult> results = new HashSet<>();



    public Exam(String title, String description, int durationMinutes, LocalDateTime startTime) {
        this.title = title;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.startTime = startTime;
    }

    public void addQuestion(Question question) {
        questions.add(question);
        question.getExams().add(this);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        question.getExams().remove(this);
    }

    public void addResult(ExamResult result) {
        results.add(result);
        result.setExam(this);
    }

    public void removeResult(ExamResult result) {
        results.remove(result);
        result.setExam(null);
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(durationMinutes);
    }


}
