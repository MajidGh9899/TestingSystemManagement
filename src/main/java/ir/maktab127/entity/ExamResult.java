package ir.maktab127.entity;

import ir.maktab127.entity.base.BaseEntity;
import ir.maktab127.entity.user.Student;
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
@NoArgsConstructor
 @Table(name = ExamResult.TABLE_NAME)

public class ExamResult extends BaseEntity<Long> {
    public static final String TABLE_NAME="exam_results";
    public static final String EXAM="exam";
    public static final String STUDENT="student";
    public static final String START_TIME="start_time";
    public static final String END_TIME="end_time";
    public static final String SCORE="score";



    @ManyToOne
    @JoinColumn(name = EXAM)
    private Exam exam;
    @ManyToOne
    @JoinColumn(name = STUDENT)
    private Student student;
    @Column(name = START_TIME)
    private LocalDateTime startTime;
    @Column(name = END_TIME)
    private LocalDateTime endTime;
    @Column(name = SCORE)
    private Double score;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Answer> answers=new HashSet<>();

    public ExamResult(Exam exam, Student student) {
        this.exam = exam;
        this.student = student;
        this.startTime = LocalDateTime.now();
    }
    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setExamResult(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setExamResult(null);
    }

    public boolean isCompleted() {
        return endTime != null;
    }

    public void complete() {
        this.endTime = LocalDateTime.now();
    }

}
