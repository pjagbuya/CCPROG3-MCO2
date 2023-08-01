package CustomSetup;

import javafx.geometry.Insets;



import DenomLib.SetDenomPaneController;
import DenomLib.SetDenomPaneView;
import ItemSelectLib.CreateMenuController;
import ItemSelectLib.SetItemPaneView;
import VMLib.VMachineModelPaneView;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CustomTopBarView extends HBox
{
    public CustomTopBarView(Stage parentWin)
    {
        String colorLightest = "#97FEED";
        String colorBg = "#071952";
        this.parentWin = parentWin;
        setDefault();
        this.prefHeight(MIN_HEIGHT);
        this.prefWidth(MIN_WIDTH);
        this.setStyle(
            "-fx-background-color: " + colorBg + "; " +
            "-fx-border-color: " + colorLightest + ";" +
            "-fx-border-width: 3px;"+
            "-fx-border-style: solid;"+
            "-fx-padding: 0 0 0 10px;"
        );


        
        this.getChildren().addAll(buttonStartVBox, buttonFinishVBox);
   

    }
        
    // your constructor and methods...


    public void setDefault()
    {
        Font headerBoldLabel = Font.font("Helvetica", FontWeight.BOLD, 16);
        String colorLightest = "#97FEED";
        String colorBg = "#071952";
        buttonStartVBox = new VBox();
        buttonFinishVBox = new VBox();

        // Place Holder
        finishBtn = new Button("Finish");
        finishBtn.setStyle("-fx-base: " + BG_COLOR+ ";"
                        +"-fx-border-color: " + colorLightest + ";" +
                         "-fx-border-width: 1.5px;");
        finishBtn.setFont(headerBoldLabel);

        finishBtn.setAlignment(Pos.CENTER_RIGHT);

        exitBtn = new Button("Exit");
        buttonStartVBox.setAlignment(Pos.CENTER_LEFT);
        exitBtn.setStyle("-fx-base: " + BG_COLOR+ ";"
                        +"-fx-border-color: " + colorLightest + ";" +
                         "-fx-border-width: 1.5px;");
        exitBtn.setFont(headerBoldLabel);
   
 
        buttonStartVBox.getChildren().add(exitBtn);

        buttonFinishVBox.getChildren().add(finishBtn);
        buttonFinishVBox.setAlignment(Pos.CENTER_RIGHT);
  
    }
    public void addToChildren(Node node, Node nodeLabel)
    {
        HBox cont1 = new HBox();


        cont1.getChildren().addAll(node, nodeLabel);
        cont1.setAlignment(Pos.CENTER);
        
        
        if(!this.getChildren().contains(buttonFinishVBox))
        {
            this.getChildren().clear();
            this.getChildren().addAll(buttonStartVBox,  cont1,  buttonFinishVBox);
        }
            
        else
        {
            this.getChildren().clear();
            this.getChildren().addAll(buttonStartVBox,  cont1);
        }



    }
    public void addToChildren(Node node)
    {
        HBox cont1 = new HBox();

        cont1.getChildren().add(node);
        cont1.setAlignment(Pos.CENTER);  
        HBox.setHgrow(cont1, Priority.ALWAYS);
        this.getChildren().clear();
        


        if(!this.getChildren().contains(buttonFinishVBox))
        {
            this.getChildren().clear();
            this.getChildren().addAll(buttonStartVBox,  cont1,  buttonFinishVBox);
        }
            
        else
        {
            this.getChildren().clear();
            this.getChildren().addAll(buttonStartVBox,  cont1);
        }

    }
    public void removeFinishBtn()
    {
        this.getChildren().remove(buttonFinishVBox);
    }


    public Button getExitBtn() {
        return exitBtn;
    }
    public Button getFinishBtn() {
        return finishBtn;
    }

    public Stage getParentWin() {
        return parentWin;
    }


    public void changeWindowScene(Scene newScene)
    {
        parentWin.setScene(newScene);
        parentWin.centerOnScreen();

    }

    public void setStageWidthPropertyListener(ChangeListener<Number> changeListener)
    {
        this.parentWin.widthProperty().addListener(changeListener);
    }
    public void setExitBtnListener(EventHandler<ActionEvent> eventHandler) {
        this.exitBtn.setOnAction(eventHandler);
    }
    
    public void setFinishBtnListener(EventHandler<ActionEvent> eventHandler) {
        this.finishBtn.setOnAction(eventHandler);
    }
    







    private Stage parentWin;
    private Button exitBtn;
    private Button finishBtn;
    private VBox buttonStartVBox, buttonFinishVBox;

    private final double MIN_HEIGHT = 100;
    private final double MIN_WIDTH = 1200;
    private final String BG_COLOR = "#071952";
}
