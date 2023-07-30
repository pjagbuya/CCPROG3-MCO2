
import StartLib.*;

import java.util.NoSuchElementException;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

// Classes below are used to validate if something contains a number
import java.util.regex.Pattern;

import Boxes.ConfirmBox;

import java.util.regex.Matcher;



public class App extends Application {

    @Override
    public void start(Stage primaryStage) 
    {
        
        Stage window = primaryStage;
        AppModel appModel = new AppModel();
        AppView appView = new AppView(window);
        AppController appController = new AppController(appView, appModel);


        

        
    }


    public static void main(String[] args) {


        launch(args);
    }
 
    private void closeProgram(Stage window)
    {   ConfirmBox boxMessage = new ConfirmBox();
        boolean answer = boxMessage.display("Warning", "Are you sure you want to exit?");


        if(answer)
            window.close();
    }
    private boolean containsNumber(String input) {
        Pattern pattern = Pattern.compile(".*\\d.*");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }


    private final double MIN_HEIGHT = 600;
    private final double MIN_WIDTH = 600;

}