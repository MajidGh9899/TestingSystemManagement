package ir.maktab127.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = MultipleChoiceQuestion.TABLE_NAME)
@Getter
@Setter
@DiscriminatorValue("multiple_choice")

public class MultipleChoiceQuestion extends Question {
    public static final String TABLE_NAME="multiple_choice_questions";




    @ElementCollection
    @CollectionTable(
            name = "question_options",
            joinColumns = @JoinColumn(name = "question")
    )
    private List<String>  options=new ArrayList<>();
    @Column(name = "correct_option_index")
    private int correctOptionIndex;

    public MultipleChoiceQuestion() {
        super();
    }

    public MultipleChoiceQuestion(String title, String content) {
        super(title, content);
    }
    public void addOption(String option) {
        options.add(option);
    }

    public void removeOption(String option) {
        options.remove(option);
    }
    public void setCorrectOptionIndex(int correctOptionIndex) {
        if (correctOptionIndex >= 0 && correctOptionIndex < options.size()) {
            this.correctOptionIndex = correctOptionIndex;
        } else {
            throw new IllegalArgumentException("Correct option index out of bounds");
        }
    }

    public boolean isCorrect(int selectedOption) {
        return selectedOption == correctOptionIndex;
    }

}
