package StartLib;

import javafx.geometry.Insets;
import CustomSetup.CustomTopBarView;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CreateRegTopBarView extends CustomTopBarView
{
    public CreateRegTopBarView(Stage parentWin, Scene prevScene)
    {
        super(parentWin, prevScene);
        
        HBox nameAndTxtFieldPane;
        nameLabel = new Label("Name: ");
        nameLabel.setFont(new Font("Arial", 16));
        nameTextField = new TextField();
        nameTextField.setPromptText("Enter name of Vending Machine");
        nameTextField.prefWidthProperty().bind(this.widthProperty().divide(4));

        nameAndTxtFieldPane = new HBox();
        HBox.setMargin(nameAndTxtFieldPane, new Insets(10, 0, 10, 0));
        nameAndTxtFieldPane.getChildren().addAll(nameLabel, nameTextField);


        super.addToChildren(nameAndTxtFieldPane);
        nameAndTxtFieldPane.setAlignment(Pos.CENTER);



    }
    

    public TextField getNameTextField() 
    {
        return nameTextField;
    }
    public Label getNameLabel() 
    {
        return nameLabel;
    }
    public void setStageWidthPropertyListener(ChangeListener<Number> changeListener)
    {
        super.getParentWin().widthProperty().addListener(changeListener);
    }
    public void setExitBtnListener(EventHandler<ActionEvent> eventHandler)
    {

        super.getExitBtn().setOnAction(eventHandler);

    }
    public void setFinishBtnListener(EventHandler<ActionEvent> eventHandler)
    {

        super.getFinishBtn().setOnAction(eventHandler);

    }      
    private TextField nameTextField;
    private Label nameLabel;


}
