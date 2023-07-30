package MaintenanceLib;

import java.awt.Button;
import java.awt.ScrollPane;
import java.lang.reflect.Array;
import java.util.ArrayList;

import Boxes.AlertBox;
import ItemSelectLib.SetItemPaneView;
import Models.VM_Regular;
import VMLib.VMachineModelPaneController;
import VMLib.VMachineModelPaneView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MaintenanceReplaceView extends BorderPane
{
    public MaintenanceReplaceView(Stage parentWin)
    {


        rightItemSelectPane = new SetItemPaneView(parentWin);
        centerVMachineModelPaneView = new VMachineModelPaneView(parentWin);

        // Must extact labels from VM machine and then add them to screen of Vmachinemodelview
        //this.centerVMachineModelPaneView.addItemWithComp(getAccessibleHelp(), centerVMachineModelPaneView, null);
        
        
        this.alertBox = new AlertBox();
        this.setRight(rightItemSelectPane);
        this.setCenter(centerVMachineModelPaneView);
 
    }

    public void setItemSlotListeners(EventHandler<MouseEvent> eventHandler, int trackedIndex)
    {

        rightItemSelectPane.addEventHandlerOnMouseClick(eventHandler, trackedIndex);

    }

    public VMachineModelPaneView getLeftVMachineModelPaneView() 
    {
        return centerVMachineModelPaneView;
    }
 
    public SetItemPaneView getRightItemSelectPane() 
    {
        return rightItemSelectPane;
    }

    private AlertBox alertBox;
    private ArrayList<Button> selectButton;
    private SetItemPaneView rightItemSelectPane;
    private VMachineModelPaneView centerVMachineModelPaneView;



}
