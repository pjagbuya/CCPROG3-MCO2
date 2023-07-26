package CustomSetup;

import javafx.geometry.Insets;



import DenomLib.SetDenomPaneController;
import DenomLib.SetDenomPaneView;
import ItemSelectLib.SetItemPaneController;
import ItemSelectLib.SetItemPaneView;
import VMLib.VMachineModelPaneController;
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

public abstract class CustomTopBarView extends HBox
{
    public CustomTopBarView(Stage parentWin, Scene targetScene)
    {
        
        Font headerBoldLabel = Font.font("Helvetica", FontWeight.BOLD, 16);

   
        
        String colorLightest = "#97FEED";
        String colorBg = "#071952";


        
        this.parentWin = parentWin;


        buttonStartVBox = new VBox();
        buttonFinishVBox = new VBox();

        

        this.targetScene = targetScene;

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

    public void addToChildren(Node node, Node nodeLabel)
    {
        this.getChildren().clear();
        HBox spacer1 = new HBox();
        HBox spacer2 = new HBox();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        this.getChildren().addAll(buttonStartVBox, spacer1, nodeLabel, node, spacer2, buttonFinishVBox);


    }
    public void addToChildren(Node node)
    {
        this.getChildren().clear();
        HBox spacer1 = new HBox();
        HBox spacer2 = new HBox();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        this.getChildren().addAll(buttonStartVBox, spacer1, node, spacer2, buttonFinishVBox);


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
    public Scene getTargetScene() {
        return targetScene;
    }





    private Scene targetScene;
    private Stage parentWin;
    private Button exitBtn, finishBtn;
    private VBox buttonStartVBox, buttonFinishVBox;


    private final double MIN_HEIGHT = 100;
    private final double MIN_WIDTH = 1200;
    private final String BG_COLOR = "#071952";
}
