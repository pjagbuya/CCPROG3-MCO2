package StartLib;

import javafx.geometry.Insets;
import CustomSetup.CustomTopBarView;
import DenomLib.SetDenomPaneController;
import DenomLib.SetDenomPaneView;
import ItemSelectLib.CreateMenuController;
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

public class SetupVMTopBarView extends CustomTopBarView
{
    public SetupVMTopBarView(Stage parentWin)
    {
        super(parentWin);



    }
    
    public Scene getSceneToCreateVM() 
    {
        return sceneToCreateVM;
    }
    public TextField getNameTextField() 
    {
        return nameTextField;
    }
    public Label getNameLabel() 
    {
        return nameLabel;
    }

    
    private TextField nameTextField;
    private Scene sceneToCreateVM;
    private Label nameLabel;



}
