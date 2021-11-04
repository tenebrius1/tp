package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Qualification.Qualifications;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.LevelSubjectCode;

/**
 * An UI component that displays information of a {@code tutor}.
 */
public class TutorCard extends UiPart<Region> {

    private static final String FXML = "TutorListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on CliTutors level 4</a>
     */

    public final Tutor tutor;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label gender;
    @FXML
    private Label qualification;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code tutorCode} with the given {@code tutor} and index to display.
     */
    public TutorCard(Tutor tutor, int displayedIndex) {
        super(FXML);
        this.tutor = tutor;
        id.setText(displayedIndex + ". ");
        name.setText(tutor.getName().formatName());
        phone.setText("Contact: " + tutor.getPhone().value);
        gender.setText("Gender: " + tutor.getGender().genderSymbol);
        qualification.setText("Qualification: " + Qualifications.getLabel(tutor.getQualification().index));
        if (tutor.isRemarkEmpty()) {
            remark.setVisible(false);
        }
        remark.setText("Remark: " + tutor.getRemark().description);
        tutor.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(LevelSubjectCode.getLabel(tag.tagName))));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorCard)) {
            return false;
        }

        // state check
        TutorCard card = (TutorCard) other;
        return id.getText().equals(card.id.getText())
                && tutor.equals(card.tutor);
    }
}
