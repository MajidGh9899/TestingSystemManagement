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
@Table(name = Answer.TABLE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name ="answer_type"  ,discriminatorType = DiscriminatorType.STRING )
public abstract class Answer extends BaseEntity<Long> {
    public static final String TABLE_NAME="answers";
    public static final String QUESTION="question";
    public static final String EXAM_RESULT="exam_result";
    public static final String SCORE="score";


    @ManyToOne
    @JoinColumn(name = QUESTION )
    private Question  question;
    @ManyToOne
    @JoinColumn(name = EXAM_RESULT )
    private ExamResult examResult;
    @Column(name = SCORE )
    private Double score;

    public Answer(Question question) {
        this.question = question;
    }

}
