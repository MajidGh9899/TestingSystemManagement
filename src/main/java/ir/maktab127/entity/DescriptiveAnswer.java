package ir.maktab127.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("DESCRIPTIVE")
public class DescriptiveAnswer extends Answer {

    @Column(columnDefinition = "content")
    private String content;

    public DescriptiveAnswer() {
        super();
    }

    public DescriptiveAnswer(Question question, String content) {
        super(question);
        this.content = content;
    }

}
