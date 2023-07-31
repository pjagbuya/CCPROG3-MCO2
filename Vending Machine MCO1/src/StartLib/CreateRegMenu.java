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

import Boxes.ConfirmBox;
import Buttons.DenominationBtn;

import java.util.regex.Matcher;

import DenomLib.SetDenomPaneController;
import DenomLib.SetDenomPaneView;
import ItemSelectLib.CreateMenuController;
import ItemSelectLib.SetItemPaneView;
import VMLib.VMachineModelPaneController;
import VMLib.VMachineModelPaneView;

public class CreateRegMenu extends BorderPane
{
   

    public CreateRegMenu(Stage parentWin)
    {

        ConfirmBox confirmBox = new ConfirmBox();
        vMachineModelPaneView = new VMachineModelPaneView(parentWin);
        // Denominations viewer
        leftDenominationsPane = new SetDenomPaneView(parentWin);

        // Items to choose from
        rightSetItemPane = new SetItemPaneView(parentWin);
        createRegTopBarView = new CreateRegTopBarView(parentWin);


        this.setStyle("-fx-base: " + BG_COLOR+ ";");
        this.setTop(createRegTopBarView);
        this.setCenter(vMachineModelPaneView);
        this.setRight(rightSetItemPane);
        this.setLeft(leftDenominationsPane);


    }
    public boolean raiseConfirmBox(String title, String message)
    {
        confirmBox = new ConfirmBox();
        return confirmBox.display(title, message);

    }
    public CreateRegTopBarView getCreateRegTopBarView() 
    {
        return createRegTopBarView;
    }
    public SetDenomPaneView getLeftDenominationsPane() 
    {
        return leftDenominationsPane;
    }
    public double getMIN_HEIGHT() 
    {
        return MIN_HEIGHT;
    }
    public double getMIN_WIDTH() 
    {
        return MIN_WIDTH;
    }
    public SetItemPaneView getRightSetItemPane() 
    {
        return rightSetItemPane;
    }

    public VMachineModelPaneView getvMachineModelPaneView() 
    {
        return vMachineModelPaneView;
    }

    private SetDenomPaneView leftDenominationsPane;
    private CreateRegTopBarView createRegTopBarView;

    private SetItemPaneView rightSetItemPane;
    private VMachineModelPaneView vMachineModelPaneView;
    private ConfirmBox confirmBox;

    private final double MIN_HEIGHT = 600;
    private final double MIN_WIDTH = 1200;
    private final String BG_COLOR = "#071952";

    
}
