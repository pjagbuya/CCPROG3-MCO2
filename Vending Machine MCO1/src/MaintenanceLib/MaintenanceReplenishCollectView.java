package MaintenanceLib;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
/**
 * This class shows replenish/collect menu UI of this program
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class MaintenanceReplenishCollectView extends BorderPane 
{
    public MaintenanceReplenishCollectView(Stage parentWin)
    {
        GridPane mainGridPane = new GridPane();
        this.rightSetDenomPane = new SetDenomPaneView(parentWin);
        this.denomSummaryView = new DenomSummaryView();
        this.parentWin = parentWin;
        
        // Must extact labels from VM machine and then add them to screen of Vmachinemodelview
        //this.centerVMachineModelPaneView.addItemWithComp(getAccessibleHelp(), centerVMachineModelPaneView, null);
        mainGridPane.add(denomSummaryView, 0, 0);
        
        this.alertBox = new AlertBox();
        this.setRight(this.rightSetDenomPane);
        this.setCenter(mainGridPane);
    }
    public void updateDenomSetSection(LinkedHashMap<String, Integer> denomCount)
    {
        for(Map.Entry<String, Integer> entry : denomCount.entrySet())
        {
            for(int i = 0; i < entry.getValue(); i++)
            {
                
                this.denomSummaryView.updateCountLabel(entry.getKey());
            }
        }

        this.rightSetDenomPane.updateView(denomCount);
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
    public void setCountLabel(String name, int count)
    {
        this.denomSummaryView.setCountLabel(name, count);
    }
    public LinkedHashMap<String, Integer> getDenominationCount()
    {
        return this.denomSummaryView.getDenominationCount();
    }
    public void resetCountLabels() 
    {
        this.denomSummaryView.reset();
    }
    public SetDenomPaneView getRightSetDenomPane() 
    {
        return rightSetDenomPane;
    }
    private Stage parentWin;
    private AlertBox alertBox;
    private SetDenomPaneView rightSetDenomPane;
    private DenomSummaryView denomSummaryView;
}
