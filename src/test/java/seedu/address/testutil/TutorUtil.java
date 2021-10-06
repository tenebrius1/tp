package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUALIFICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Tutor.
 */
public class TutorUtil extends PersonUtil {

    /**
     * Returns an add command string for adding the {@code tutor}.
     */
    public static String getAddCommand(Tutor tutor, PersonType personType) {
        return AddCommand.COMMAND_WORD + " " + getTutorDetails(tutor);
    }

    /**
     * Returns the part of command string for the given {@code tutor}'s details.
     */
    public static String getTutorDetails(Tutor tutor) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + tutor.getName().fullName + " ");
        sb.append(PREFIX_PHONE + tutor.getPhone().value + " ");
        sb.append(PREFIX_GENDER + tutor.getGender().genderSymbol + " ");
        sb.append(PREFIX_QUALIFICATION + tutor.getQualification().index + " ");
        tutor.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditCommand.EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_GENDER).append(gender.genderSymbol).append(" "));
        descriptor.getQualification().ifPresent(qualification -> sb.append(PREFIX_QUALIFICATION)
                .append(qualification.index).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
