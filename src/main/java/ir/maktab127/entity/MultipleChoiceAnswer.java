package ir.maktab127.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("multiple_choice")
public class MultipleChoiceAnswer extends Answer {

    @Column(name = "selected_option_index")
    private Integer selectedOptionIndex;

    public boolean isCorrect() {
        if (!(getQuestion() instanceof MultipleChoiceQuestion) || selectedOptionIndex == null) {
            return false;
        }

        MultipleChoiceQuestion question = (MultipleChoiceQuestion) getQuestion();
        return question.isCorrect(selectedOptionIndex);
    }
}
