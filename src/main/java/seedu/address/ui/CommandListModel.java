package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;

/**
 * A model class for the tag commands table in the help window.
 */
public class CommandListModel {
    private SimpleStringProperty action;
    private SimpleStringProperty format;

    /**
     * Initializes the CommandListModel object.
     * @param action the command name.
     * @param format an example of how to type the command.
     */
    public CommandListModel(String action, String format) {
        this.action = new SimpleStringProperty(action);
        this.format = new SimpleStringProperty(format);
    }

    public String getAction() {
        return action.get();
    }

    public void setAction(String action) {
        this.action.set(action);
    }

    public String getFormat() {
        return format.get();
    }

    public void setFormat(String format) {
        this.format.set(format);
    }
}
