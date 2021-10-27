package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutor;

/**
 * Panel containing the list of matched tutors.
 */
public class MatchedListPanel<T extends Person> extends UiPart<Region> {
    private static final String FXML = "MatchedListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MatchedListPanel.class);

    @FXML
    private ListView<T> matchedListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public MatchedListPanel(ObservableList<T> personList) {
        super(FXML);
        matchedListView.setItems(personList);
        matchedListView.setCellFactory(listView -> new MatchedListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class MatchedListViewCell extends ListCell<T> {
        @Override
        protected void updateItem(T person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                Tutor tutor = (Tutor) person;
                setGraphic(new TutorCard(tutor, getIndex() + 1).getRoot());
            }
        }
    }

}