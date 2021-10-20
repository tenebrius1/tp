package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;

/**
 * A model class for the subject tag table in the help window.
 */
public class SubjectTagModel {
    private SimpleStringProperty educationLevel;
    private SimpleStringProperty subject;
    private SimpleStringProperty tag;

    /**
     * Initializes the SubjectTagModel object.
     * @param educationLevel the education level string.
     * @param subject the subject string.
     * @param tag the tag string.
     */
    public SubjectTagModel(String educationLevel, String subject, String tag) {
        this.educationLevel = new SimpleStringProperty(educationLevel);
        this.subject = new SimpleStringProperty(subject);
        this.tag = new SimpleStringProperty(tag);
    }

    public String getEducationLevel() {
        return educationLevel.get();
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel.set(educationLevel);
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getTag() {
        return tag.get();
    }

    public void setTag(String tag) {
        this.tag.set(tag);
    }
}
