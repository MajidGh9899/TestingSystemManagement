package ir.maktab127.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DESCRIPTIVE")
public class DescriptiveQuestion extends Question {

    public DescriptiveQuestion() {
        super();
    }

    public DescriptiveQuestion(String title, String content) {
        super(title, content);
    }
}
