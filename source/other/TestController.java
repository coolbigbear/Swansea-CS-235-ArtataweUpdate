package other;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TestController {
	
	private int topButtonClicks = 0;
	private int centerButtonClicks = 0;
	
	@FXML
	Button topButton;
	
	@FXML
	Button centerButton;
	
	
	@FXML
	void clickedTopButton(ActionEvent event) {
		topButtonClicks++;
		topButton.setText("Clicked " + topButtonClicks + " times");
	}
	
	@FXML
	void clickedCenterButton(ActionEvent event) {
		centerButtonClicks++;
		centerButton.setText("Clicked " + centerButtonClicks + " times");
	}
	
}
