package VMSell;

import CustomSetup.CustomTopBarView;
import Labels.LabelToField;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class VMSellingTopBarView extends CustomTopBarView{

    public VMSellingTopBarView(Stage parentWin, Scene targetScene)
    {

        super(parentWin, targetScene);
        HBox spacer1 = new HBox();
        HBox spacer2 = new HBox();
        Label vmName = new LabelToField("Placeholder Name (Selling)", 20);
        
        HBox addedNode = new HBox();

        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        addedNode.getChildren().addAll(spacer1, vmName, spacer2);

        this.addToChildren(addedNode);
        this.getFinishBtn().setText("Go To Maintenance");

    }
}
