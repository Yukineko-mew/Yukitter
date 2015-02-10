package application;

import java.io.IOException;

import javax.swing.text.IconView;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CellLayoutC extends ListCell<Status>{
	
	private Status status;
	
	@FXML
	private AnchorPane theColumn;
	
	@FXML
	private Label name;
	
	@FXML
	private Label id;
	
	@FXML
	private Label text;
	
	@FXML
	private ImageView icon;
	
	@FXML
	private Pane tweetPane;
	
	@FXML
	private Pane buttonPane;
	
	@FXML
	private Button reply;

	@FXML
	private Button favolite;
	
	@FXML
	private Button retweet;
	
	@FXML
	private Button directMessage;

	public CellLayoutC(Status status) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CellLayout.fxml"));
		loader.setController(this);
		
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		this.status = status;
		
		name.setText("@"+status.getUser().getName());
		id.setText(status.getUser().getScreenName());
		text.setText(status.getText());
//		Image image = new Image(status.getUser().getProfileImageURL());
//		icon.setImage(image);
		
//		System.out.println("---  ---");
//		System.out.println("Name:" + status.getUser().getName());
//		System.out.println("Id:" + status.getUser().getScreenName());
//		System.out.println("Text:" + status.getText());
		setGraphic(getAnchorPane());
		
	}
	
	public void onReply() {
		mainController.getInstance().tweetText.setText("@" + status.getUser().getScreenName() + " ");
		System.out.println("replyDone");
	}
	
	public void onFavorite() {
		try {
			TwitterFactory.getSingleton().createFavorite(status.getId());
			System.out.println("FavoliteDone");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onRetweet() {
		try {
			TwitterFactory.getSingleton().retweetStatus(status.getId());
			System.out.println("RetweetDone");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onDirectMessage() {
		System.out.println("DirectMessageDone");		
	}
	
	@FXML
	private void onStackClicked() {
//		if(tweetPane.isDisable()) {
//			// ボタンペインを見えなくしてツイートパネルの影化を解除
//			buttonPane.setVisible(false);
//			tweetPane.setDisable(false);
//		} else {
			// ツイートペインを影化してボタンパネルを表示
			tweetPane.setDisable(true);
			buttonPane.setVisible(true);
//		}
	}
		
	public AnchorPane getAnchorPane() {
		return theColumn;
	}
}
