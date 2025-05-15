package ir.maktab127.entity;

import ir.maktab127.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = QuestionScore.TABLE_NAME)
public class QuestionScore extends BaseEntity<Long> {
    public static final String TABLE_NAME="question_scores";
    public static final String EXAM="exam";
    public static final String QUESTION="question";
    public static final String SCORE="score";

    @ManyToOne
    @JoinColumn(name = EXAM ,nullable = false)
    private Exam exam;
     @ManyToOne
    @JoinColumn(name = QUESTION ,nullable = false)
    private Question question;
     @Column(name = SCORE ,nullable = false)
    private Double score;

    public QuestionScore(Exam exam, Question question, Double score) {
        this.exam = exam;
        this.question = question;
        this.score = score;
    }
}
