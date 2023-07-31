package VMSell;

import java.util.ArrayList;

import DenomLib.SetDenomPaneController;
import DenomLib.SetDenomPaneView;
import ItemSelectLib.CreateMenuController;
import ItemSelectLib.SetItemPaneView;
import Models.VM_Regular;
import NumPad.DenomNumPadView;
import NumPad.DenomNumPaneController;
import NumPad.NumPaneController;
import NumPad.NumPaneView;
import StartLib.CreateRegTopBarController;
import StartLib.CreateRegTopBarView;

import VMLib.VMachineModelPaneView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class VMSellingOpPaneView extends BorderPane
{
    public VMSellingOpPaneView(Stage parentWin)
    {
        // Viewer to the Vending Machine model
        this.vMachineModelPaneView = new VMachineModelPaneView(parentWin);
        this.denomNumPadView = new DenomNumPadView();
        this.numPaneView = new NumPaneView();
        this.vmSellingTopBarView = new VMSellingTopBarView(parentWin);
        this.dispensedItemView = new DispensedItemView();
        // Denominations viewer
        

        this.parentWin = parentWin;
        this.setStyle("-fx-base: " + BG_COLOR+ ";");
        this.setTop(vmSellingTopBarView);
        this.setCenter(this.vMachineModelPaneView);
        this.setRight(numPaneView);
        this.setLeft(denomNumPadView);
        this.setBottom(this.dispensedItemView);

    }
    


    public void passVMDataToView( ArrayList<Image> imageOrder, 
                                  ArrayList<String> itemNames)
    {
        
        this.vMachineModelPaneView.setUpVendMachView(imageOrder, itemNames);
    }
    public void setMaxSlotVMView( int maxSlot)
    {
        
        this.vMachineModelPaneView.setMaxSlots(maxSlot);
    }

    public void changeToScene(Scene sceneTarget)
    {
        this.parentWin.setScene(sceneTarget);
    }

    public DenomNumPadView getDenomNumPadView() {
        return denomNumPadView;
    }
    public NumPaneView getNumPaneView() {
        return numPaneView;
    }
    public VMSellingTopBarView getVmSellingTopBarView() {
        return vmSellingTopBarView;
    }
    public VMachineModelPaneView getvMachineModelPaneView() {
        return vMachineModelPaneView;
    }
    public DispensedItemView getDispensedItemView() {
        return dispensedItemView;
    }
    public double getMIN_HEIGHT() {
        return MIN_HEIGHT;
    }
    public double getMIN_WIDTH() {
        return MIN_WIDTH;
    }

    private Stage parentWin;
    private NumPaneView numPaneView;
    private VMSellingTopBarView vmSellingTopBarView;
    private DenomNumPadView denomNumPadView;
    private VMachineModelPaneView vMachineModelPaneView;
    private DispensedItemView dispensedItemView;
    private final double MIN_HEIGHT = 600;
    private final double MIN_WIDTH = 1200;
    private final String BG_COLOR = "#071952";
}
