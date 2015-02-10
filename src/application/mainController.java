package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class mainController implements Initializable{
	
	public static mainController instance;
	
	public static TwitterStream twitterStream;
	
	private List<Status> list;
	
	@FXML
	public TextArea tweetText;

	@FXML
	public Label name;
	
	@FXML
	public Label screenId;
	
	@FXML
	public ImageView icon;
	
	@FXML
	private ListView<Status> homeTimeline;
	
	public static ObservableList<Status> observableList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {		
		//--- ツイッターストリームのインスタンス取得 ---//
        twitterStream = TwitterStreamFactory.getSingleton();
		twitterStream.addListener(new MyUserStreamAdapter());
		twitterStream.user();
		
		//--- ユーザ情報の取得 ---//
		try {
			name.setText(TwitterFactory.getSingleton().verifyCredentials().getName());
			screenId.setText("@"+TwitterFactory.getSingleton().getScreenName());
			Image image = new Image(TwitterFactory.getSingleton().verifyCredentials().getBiggerProfileImageURL());
			icon.setImage(image);
		} catch (IllegalStateException | TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//--- ListViewの設定 ---//		
		observableList = homeTimeline.getItems();
		try {
			list = TwitterFactory.getSingleton().getHomeTimeline();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		observableList.setAll(list);
		
		homeTimeline.setItems(observableList);
		homeTimeline.setCellFactory(new Callback<ListView<Status>,
				javafx.scene.control.ListCell<Status>>() {
			@Override
		    public ListCell<Status> call(ListView<Status> listView) {
				return new TweetCell();
				}
			});
		
		//--- 自身のインスタンスを保持 ---//
		instance = this;
	}
	
	public void onTweet(ActionEvent e) throws TwitterException {
		String txt = tweetText.getText();
		if(txt.length() == 0) return;
		TwitterFactory.getSingleton().updateStatus(txt);
				
		tweetText.setText("");
	}
	
	public void onOpenReplyWindow(ActionEvent e) {
		Stage replyWindow = new ReplyWindow();
		
	}
	
	public static mainController getInstance() {
		return instance;
	}
}