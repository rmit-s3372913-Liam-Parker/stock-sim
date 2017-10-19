package view;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Message;

public class MessageView  extends BorderPane
{
	private static final String NO_MESSAGE = "No message exists between you and this you user";

	private VBox verticalLayout = new VBox();
	private ObservableList<Message> messageList = FXCollections.observableArrayList();
	private ListView<Message> displayMessageList = new ListView<>(messageList);
	
	public MessageView(List<Message> messageList)
	{
		populate(messageList);
	}
	
	private void populate(List<Message> messageList)
	{
		if (messageList.isEmpty())
			this.setCenter(new Text(NO_MESSAGE));
		else
		{
			for(int i = 0; i < messageList.size(); ++i)
			{
				this.messageList.add(messageList.get(i));
			}
			
			// Setup friend selected listener
			displayMessageList.setEditable(false);
			
			// Embed into main view.
			verticalLayout.getChildren().addAll(displayMessageList);
			this.setCenter(verticalLayout);
		}
	}
}
