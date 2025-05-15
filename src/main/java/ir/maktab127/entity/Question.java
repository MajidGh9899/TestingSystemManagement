package ir.maktab127.entity;

import ir.maktab127.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = Question.TABLE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name ="question_type"  ,discriminatorType = DiscriminatorType.STRING )
@NoArgsConstructor
public abstract class Question extends BaseEntity<Long> {
    public static final String TABLE_NAME="questions";
    public static final String TITLE="title";
    public static final String CONTENT="content";
    public static final String COURSE="course";

    @Column(name = TITLE, nullable = false)
    private String title;
    @Column(name = CONTENT, nullable = false)
    private String content;
    @ManyToOne
    @JoinColumn(name = COURSE, nullable = false)
    private Course course;
    @ManyToMany(mappedBy = "questions")
    private Set<Exam> exams=new HashSet<>();

    public Question(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
