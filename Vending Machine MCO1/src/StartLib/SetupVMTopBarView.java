package StartLib;

import javafx.geometry.Insets;
import CustomSetup.CustomTopBarView;
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
/**
 * This class is a view of the top bar from the pop up view that have slightly different versions of usage per Pane view
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
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
