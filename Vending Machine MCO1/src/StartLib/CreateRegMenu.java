package StartLib;



import java.util.NoSuchElementException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
 

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


// Classes below are used to validate if something contains a number
import java.util.regex.Pattern;

import Buttons.DenominationBtn;

import java.util.regex.Matcher;

import DenomLib.SetDenomPaneController;
import DenomLib.SetDenomPaneView;
import ItemSelectLib.SetItemPaneController;
import ItemSelectLib.SetItemPaneView;
import VMLib.VMachineModelPaneController;
import VMLib.VMachineModelPaneView;

public class CreateRegMenu
{
   

    public CreateRegMenu(String title, Stage parentWin, Scene prevScene)
    {


        // Viewer to the Vending Machine model
        VMachineModelPaneView vMachineModelPaneView = new VMachineModelPaneView(parentWin);
        
        // Denominations viewer
        SetDenomPaneView leftDenominationsPane = new SetDenomPaneView(parentWin);
        SetDenomPaneController setDenomPaneController = new SetDenomPaneController(leftDenominationsPane);

        // Items to choose from
        SetItemPaneView rightSetItemPane = new SetItemPaneView(parentWin);
        SetItemPaneController setItemPaneController = new SetItemPaneController(parentWin, rightSetItemPane, vMachineModelPaneView);

        
        VMachineModelPaneController vMachineModelPaneController = new VMachineModelPaneController(rightSetItemPane, vMachineModelPaneView);
        
        CreateRegTopBarView createRegTopBarView = new CreateRegTopBarView(parentWin, prevScene);
        CreateRegTopBarController createRegTopBarController = new CreateRegTopBarController(createRegTopBarView, setDenomPaneController, setItemPaneController, prevScene);

        mainCanvasBorderPane = new BorderPane();

        // mainCanvasBorderPane.getStylesheets().add(getClass().getResource("/style/denomPane.css").toExternalForm());
        mainCanvasBorderPane.setStyle("-fx-base: " + BG_COLOR+ ";");
        mainCanvasBorderPane.setTop(createRegTopBarView);
        mainCanvasBorderPane.setCenter(vMachineModelPaneView);
        mainCanvasBorderPane.setRight(rightSetItemPane);
        mainCanvasBorderPane.setLeft(leftDenominationsPane);

        scene = new Scene(mainCanvasBorderPane, MIN_WIDTH, MIN_HEIGHT);

    }



    public BorderPane getMainCanvasBorderPane() {
        return mainCanvasBorderPane;
    }
    public Scene getScene() {
        return scene;
    }


    private Scene scene;
    private BorderPane mainCanvasBorderPane;

    private final double MIN_HEIGHT = 600;
    private final double MIN_WIDTH = 1200;
    private final String BG_COLOR = "#071952";

    
}
