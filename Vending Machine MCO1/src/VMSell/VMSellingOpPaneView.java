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
    public VMSellingOpPaneView(Stage parentWin, Scene prevScene)
    {
        // Viewer to the Vending Machine model
        VMachineModelPaneView vMachineModelPaneView = new VMachineModelPaneView(parentWin);
        
        // Denominations viewer
        DenomNumPadView denomNumPadView = new DenomNumPadView();
        DenomNumPaneController denomNumPaneController = new DenomNumPaneController(denomNumPadView);

        // Selector
        NumPaneView numPaneView = new NumPaneView();
        NumPaneController numPaneController = new NumPaneController(numPaneView);
        
        VMSellingTopBarView vmSellingTopBarView = new VMSellingTopBarView(parentWin, prevScene);

        this.setStyle("-fx-base: " + BG_COLOR+ ";");
        this.setTop(vmSellingTopBarView);
        this.setCenter(vMachineModelPaneView);
        this.setRight(numPaneView);
        this.setLeft(denomNumPadView);

    }


    public double getMIN_HEIGHT() {
        return MIN_HEIGHT;
    }
    public double getMIN_WIDTH() {
        return MIN_WIDTH;
    }


    private final double MIN_HEIGHT = 600;
    private final double MIN_WIDTH = 1200;
    private final String BG_COLOR = "#071952";
}
