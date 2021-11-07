package seedu.address.testutil;

import seedu.address.model.CliTutors;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * A utility class to help with building Clitutors objects.
 * Example usage: <br>
 *     {@code CliTutors ab = new CliTutorsBuilder().withPerson("John", "Doe").build();}
 */
public class CliTutorsBuilder {

    private final CliTutors cliTutors;

    public CliTutorsBuilder() {
        cliTutors = new CliTutors();
    }

    public CliTutorsBuilder(CliTutors cliTutors) {
        this.cliTutors = cliTutors;
    }

    /**
     * Adds a new {@code Tutor} to the {@code CliTutors} that we are building.
     */
    public CliTutorsBuilder withTutor(Tutor tutor) {
        cliTutors.addTutor(tutor);
        return this;
    }

    /**
     * Adds a new {@code Student} to the {@code CliTutors} that we are building.
     */
    public CliTutorsBuilder withStudent(Student student) {
        cliTutors.addStudent(student);
        return this;
    }

    public CliTutors build() {
        return cliTutors;
    }
}
