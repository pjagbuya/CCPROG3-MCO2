package MaintenanceLib;

import ItemSelectLib.CreateMenuController;
import ItemSelectLib.SetItemPaneView;
import Labels.HeaderLabel;
import StartLib.CreateRegMenu;
import VMLib.VMachineModelPaneView;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MaintenanceRestockRepriceView extends BorderPane{
    public MaintenanceRestockRepriceView(Stage parentWin)
    {
        this.parentWin = parentWin;
        Label titleLabel = new HeaderLabel("Test",72);
        VMachineModelPaneView vMachineModelPaneView = new VMachineModelPaneView(parentWin);
        SetItemPaneView setItemPaneView = new SetItemPaneView(parentWin);
        // SetItemPaneController setItemPaneController = new SetItemPaneController(parentWin, setItemPaneView, vMachineModelPaneView);




        this.setCenter(vMachineModelPaneView);
        this.setRight(setItemPaneView);
        this.setStyle("-fx-base: " +BG_COLOR+ ";");

    }
    private Stage parentWin;
    private final String BG_COLOR = "#071952";
}
