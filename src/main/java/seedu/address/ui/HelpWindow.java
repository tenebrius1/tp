package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {
    public static final String USERGUIDE_URL = "https://ay2122s1-cs2103t-t17-2.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;
    @FXML
    private Hyperlink helpLink;

    @FXML
    private TableView<SubjectTagModel> tagTbData;
    @FXML
    private TableColumn<SubjectTagModel, String> educationLevel;
    @FXML
    private TableColumn<SubjectTagModel, String> subject;
    @FXML
    private TableColumn<SubjectTagModel, String> tag;

    @FXML
    private TableView<CommandListModel> commandTbData;
    @FXML
    private TableColumn<CommandListModel, String> action;
    @FXML
    private TableColumn<CommandListModel, String> format;

    // Data to populate the tags table
    private ObservableList<SubjectTagModel> subjectTagModels = FXCollections.observableArrayList(
            new SubjectTagModel("Primary", "English", "PE"),
            new SubjectTagModel("Primary", "Math", "PM"),
            new SubjectTagModel("Primary", "Science", "PS"),
            new SubjectTagModel("Secondary", "Biology", "SB"),
            new SubjectTagModel("Secondary", "Chemistry", "SC"),
            new SubjectTagModel("Secondary", "English", "SE"),
            new SubjectTagModel("Secondary", "Geography", "SG"),
            new SubjectTagModel("Secondary", "History", "SH"),
            new SubjectTagModel("Secondary", "Literature", "SL"),
            new SubjectTagModel("Secondary", "Math", "SM"),
            new SubjectTagModel("Secondary", "Physics", "SP"),
            new SubjectTagModel("Tertiary", "Biology", "TB"),
            new SubjectTagModel("Tertiary", "Chemistry", "TC"),
            new SubjectTagModel("Tertiary", "Economics", "TE"),
            new SubjectTagModel("Tertiary", "Geography", "TG"),
            new SubjectTagModel("Tertiary", "History", "TH"),
            new SubjectTagModel("Tertiary", "Literature", "TL"),
            new SubjectTagModel("Tertiary", "Math", "TM"),
            new SubjectTagModel("Tertiary", "Physics", "TP")
    );

    // Data to populate the commands table
    private ObservableList<CommandListModel> commandListModels = FXCollections.observableArrayList(
            new CommandListModel("Add", "add t n/NAME p/PHONE_NUMBER g/GENDER q/QUALIFICATIONS t/TAG..."
                    + "\nadd s n/NAME p/PHONE_NUMBER g/GENDER t/TAG"
                    + "\ne.g. add t n/John Doe p/98765432 g/M q/3 t/PM"),
            new CommandListModel("Delete", "delete t INDEX" + "\ndelete t INDEX" + "\ne.g. delete s 3"),
            new CommandListModel("Help", "help"),
            new CommandListModel("Edit",
                    "edit t INDEX [n/NAME] [p/PHONE_NUMBER] [g/GENDER] [q/QUALIFICATIONS] [t/TAG...]"
                    + "\nedit s INDEX [n/NAME] [p/PHONE_NUMBER] [g/GENDER] [t/TAG]"
                    + "\ne.g. edit t 2 n/John Doe q/1"),
            new CommandListModel("List", "list"),
            new CommandListModel("Find", "find t n/NAME" + "\nfind s n/NAME" + "\ne.g. find s n/John"),
            new CommandListModel("Match", "match INDEX" + "\ne.g. match 1"),
            new CommandListModel("Clear", "clear"),
            new CommandListModel("Exit", "exit")
    );

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpLink.setText(HELP_MESSAGE);

        // Initialize Subject Tag table
        educationLevel.setCellValueFactory(new PropertyValueFactory<>("educationLevel"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        tag.setCellValueFactory(new PropertyValueFactory<>("tag"));

        tagTbData.setItems(subjectTagModels);

        // Initialize Command table
        action.setCellValueFactory(new PropertyValueFactory<>("action"));
        format.setCellValueFactory(new PropertyValueFactory<>("format"));

        commandTbData.setItems(commandListModels);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Redirects user to user guide website.
     */
    public void openInBrowser() {
        try {
            Desktop.getDesktop().browse(new URL(USERGUIDE_URL).toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
