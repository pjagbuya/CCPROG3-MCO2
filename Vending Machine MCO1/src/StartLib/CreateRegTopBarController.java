package StartLib;

import Boxes.ConfirmBox;
import DenomLib.SetDenomPaneController;
import ItemSelectLib.SetItemPaneController;
import VMSell.VMSellingOpPaneView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CreateRegTopBarController {
    

    public CreateRegTopBarController(CreateRegTopBarView createRegTopBarView, 
                                     SetDenomPaneController setDenomPaneController, 
                                     SetItemPaneController setItemPaneController,
                                     Scene targetPrevScene)
    {

        ConfirmBox confirmBox = new ConfirmBox();
        VMSellingOpPaneView vmSellingOpPaneView;

        this.createRegTopBarView= createRegTopBarView;
        vmSellingOpPaneView = new VMSellingOpPaneView(this.createRegTopBarView.getParentWin());
        this.createRegTopBarView.setStageWidthPropertyListener((observable, oldValue, newValue) ->
        {
            // Recalculate margin values based on new window width (newValue)
            double leftRightMargin = newValue.doubleValue() * 0.125; // Just an example
            double topBottomMargin = newValue.doubleValue() * 0.01; // Just an example

            // Update the margins
            HBox.setMargin(this.createRegTopBarView.getNameLabel(), new Insets(topBottomMargin, leftRightMargin*0.2, topBottomMargin, leftRightMargin*2.2));
            HBox.setMargin(this.createRegTopBarView.getNameTextField(), new Insets(topBottomMargin, leftRightMargin*2.2, topBottomMargin, leftRightMargin*0.01));
        });


        this.createRegTopBarView.setExitBtnListener(e->{
            boolean isExit;

            isExit = confirmBox.display("Exit Out Of Vending Machine Maker", "Do you want to exit this and back to main menu?");
            if(!isExit)
                e.consume();
            else
            {
                setDenomPaneController.resetForm();
                setItemPaneController.resetForm();
                this.createRegTopBarView.changeWindowScene(targetPrevScene);

            }

        });



    }
    private SetDenomPaneController setDenomPaneController; 
    private SetItemPaneController setItemPaneController;
    private CreateRegTopBarView createRegTopBarView;

}
