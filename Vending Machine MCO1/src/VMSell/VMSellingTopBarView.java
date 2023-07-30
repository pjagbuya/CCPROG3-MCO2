package VMSell;

import CustomSetup.CustomTopBarView;
import Labels.LabelToField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class VMSellingTopBarView extends CustomTopBarView{

    public VMSellingTopBarView(Stage parentWin)
    {

        super(parentWin);
        HBox spacer1 = new HBox();
        HBox spacer2 = new HBox();
        Label vmName = new LabelToField("Placeholder Name (Selling)", 20);
        
        HBox addedNode = new HBox();



        addedNode.getChildren().addAll(vmName);
        addedNode.setAlignment(Pos.CENTER);
        addedNode.setPadding(new Insets(0,0,0,20));
        HBox.setMargin(addedNode, new Insets(0,0,0,20));
        this.addToChildren(addedNode);
        this.getFinishBtn().setText("Go To Maintenance");

    }
}
