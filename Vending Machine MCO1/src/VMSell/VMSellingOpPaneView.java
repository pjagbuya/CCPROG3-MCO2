package VMSell;

import DenomLib.SetDenomPaneController;
import DenomLib.SetDenomPaneView;
import ItemSelectLib.SetItemPaneController;
import ItemSelectLib.SetItemPaneView;
import NumPad.DenomNumPadView;
import NumPad.DenomNumPaneController;
import NumPad.NumPaneController;
import NumPad.NumPaneView;
import StartLib.CreateRegTopBarController;
import StartLib.CreateRegTopBarView;
import VMLib.VMachineModelPaneController;
import VMLib.VMachineModelPaneView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class VMSellingOpPaneView extends BorderPane
{
    public VMSellingOpPaneView(Stage parentWin)
    {
        // Viewer to the Vending Machine model
        vMachineModelPaneView = new VMachineModelPaneView(parentWin);
        denomNumPadView = new DenomNumPadView();
        numPaneView = new NumPaneView();
        vmSellingTopBarView = new VMSellingTopBarView(parentWin);
        // Denominations viewer
        

        this.parentWin = parentWin;
        this.setStyle("-fx-base: " + BG_COLOR+ ";");
        this.setTop(vmSellingTopBarView);
        this.setCenter(vMachineModelPaneView);
        this.setRight(numPaneView);
        this.setLeft(denomNumPadView);

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
    private final double MIN_HEIGHT = 600;
    private final double MIN_WIDTH = 1200;
    private final String BG_COLOR = "#071952";
}
