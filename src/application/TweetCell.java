package application;

import twitter4j.Status;
import javafx.scene.control.ListCell;

public class TweetCell extends ListCell<Status>{
	@Override
	public void updateItem(Status status, boolean empty) {
		super.updateItem(status, empty);
		if(status != null) {
			CellLayoutC cellController = new CellLayoutC(status);
			setGraphic(cellController.getAnchorPane());
		}
	}
}
