package miniProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BookingManage extends Application{

	@Override
	public void start(Stage stage) throws Exception{
		try {										
			Parent root = FXMLLoader.load(getClass().getResource("BookingFx.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().getClass().getResource("Booking.css");
			scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
			stage.setScene(scene); 
			stage.setResizable(false);
			//stage.initStyle(StageStyle.TRANSPARENT);
			stage.show();							
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] ar) {
		launch(ar);																	
	}

}
