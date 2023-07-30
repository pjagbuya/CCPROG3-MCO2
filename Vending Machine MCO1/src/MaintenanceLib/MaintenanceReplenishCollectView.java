package MaintenanceLib;

import java.util.ArrayList;

import Boxes.AlertBox;
import DenomLib.DenomSummaryView;
import DenomLib.Denomination;
import DenomLib.SetDenomPaneView;
import ItemSelectLib.SetItemPaneView;
import VMLib.VMachineModelPaneView;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MaintenanceReplenishCollectView extends BorderPane 
{
    public MaintenanceReplenishCollectView(Stage parentWin)
    {
        GridPane mainGridPane = new GridPane();
        this.rightSetDenomPane = new SetDenomPaneView(parentWin);
        this.denomSummaryView = new DenomSummaryView();
        
        // Must extact labels from VM machine and then add them to screen of Vmachinemodelview
        //this.centerVMachineModelPaneView.addItemWithComp(getAccessibleHelp(), centerVMachineModelPaneView, null);
        mainGridPane.add(denomSummaryView, 0, 0);
        
        this.alertBox = new AlertBox();
        this.setRight(this.rightSetDenomPane);
        this.setCenter(mainGridPane);
    }

    public void updateCountLabel(int i) 
    {
        this.denomSummaryView.updateCountLabel(i);
    }
    public void updateCountLabel(String moneyName) 
    {
        this.denomSummaryView.updateCountLabel(moneyName);
    }
    public void updateCountLabel(Double moneyVal) 
    {
        this.denomSummaryView.updateCountLabel(moneyVal);
    }
    public void subCountLabel(int i) 
    {
        this.denomSummaryView.subCountLabel(i);
    }
    public void subCountLabel(String moneyName) 
    {
        this.denomSummaryView.subCountLabel(moneyName);
    }
    public void subCountLabel(Double moneyVal) 
    {
        this.denomSummaryView.subCountLabel(moneyVal);
    }   
    public SetDenomPaneView getRightSetDenomPane() 
    {
        return rightSetDenomPane;
    }

    private AlertBox alertBox;
    private SetDenomPaneView rightSetDenomPane;
    private DenomSummaryView denomSummaryView;
}
