package AlexUITestNav;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Observable;

public class Login extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Art-atawe Login");
        Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("LoginNavigate.fxml"));

        Scene myScene = new Scene(myPane);

        //Adding the scene to Stage
        primaryStage.setScene(myScene);

        //Displaying the contents of the stage
        primaryStage.show();

        //Create the Text object
        Text text = new Text();

        //Set the font for the text
        text.setFont(new Font(45));

        //Set the positioning of the text
        text.setX(50);
        text.setY(150);

        //Text added
        text.setText("Artatawe!");

        //creating a Group object
        Group root = new Group();

        //obserable list of objects
        ObservableList list = root.getChildren();

        //set text object into the list
        list.add(text);

        //Creating a Scene by passing the group object, height and width
        Scene scene = new Scene(root ,600, 300);

        //setting color to the scene
        scene.setFill(Color.BROWN);

        //Adding the scene to Stage
        primaryStage.setScene(scene);

        //Displaying the contents of the stage
        primaryStage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
