package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CliTutors;
import seedu.address.model.ReadOnlyCliTutors;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * An Immutable CliTutors that is serializable to JSON format.
 */
@JsonRootName(value = "clitutors")
class JsonSerializableCliTutors {

    public static final String MESSAGE_DUPLICATE_TUTOR = "Tutors list contains duplicate tutor(s).";
    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";

    private final List<JsonAdaptedTutor> tutors = new ArrayList<>();
    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCliTutors} with the given persons.
     */
    @JsonCreator
    public JsonSerializableCliTutors(@JsonProperty("tutors") List<JsonAdaptedTutor> tutors,
                                       @JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.tutors.addAll(tutors);
        this.students.addAll(students);
    }

    /**
     * Converts a given {@code ReadOnlyCliTutors} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCliTutors}.
     */
    public JsonSerializableCliTutors(ReadOnlyCliTutors source) {
        tutors.addAll(source.getTutorList().stream().map(JsonAdaptedTutor::new).collect(Collectors.toList()));
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code CliTutors} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CliTutors toModelType() throws IllegalValueException {
        CliTutors cliTutors = new CliTutors();
        // Add tutors to cliTutors
        for (JsonAdaptedTutor jsonAdaptedTutor : tutors) {
            Tutor tutor = jsonAdaptedTutor.toModelType();
            if (cliTutors.hasTutor(tutor)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TUTOR);
            }
            cliTutors.addTutor(tutor);
        }
        // Add students to cliTutors
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (cliTutors.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            cliTutors.addStudent(student);
        }
        return cliTutors;
    }

}
