package VMSell;

import Boxes.AlertBox;
import Boxes.AlertBoxRep;
import Boxes.ConfirmBox;
import CustomSetup.CustomTopBarView;
import MaintenanceLib.MaintSelectView;
import MaintenanceLib.MaintenanceController;
import MaintenanceLib.MaintenanceTopBarView;
import NumPad.DenomNumPadView;
import NumPad.DenomNumPaneController;
import NumPad.NumPaneController;
import NumPad.NumPaneView;
import StartLib.AppController;
import StartLib.AppModel;
import VMLib.VMachineModelPaneView;
import javafx.scene.Scene;

public class VMSellingOpController 
{
    public VMSellingOpController(AppController appController,
                                 VMSellingTopBarView vmSellingTopBarView,
                                 VMSellingOpPaneView vmSellingOpPaneView,
                                 Scene targetExit,
                                 Scene targetMaint)
    {


        
        

        this.vmSellingTopBarView = vmSellingTopBarView;
        this.vmSellingOpPaneView = vmSellingOpPaneView;
        this.dispensedItemView = this.vmSellingOpPaneView.getDispensedItemView();
        this.numPaneView = vmSellingOpPaneView.getNumPaneView();
        this.denomNumPadView = this.vmSellingOpPaneView.getDenomNumPadView();
        this.vMachineModelPaneView = this.vmSellingOpPaneView.getvMachineModelPaneView();
        this.appModel = appController.getAppModel();
        
        
        this.denomNumPaneController = new DenomNumPaneController(appController,
                                                            this.denomNumPadView ,
                                                            this.dispensedItemView);
        this.numPaneController = new NumPaneController(appController,
                                                  this.numPaneView,
                                                  this.vMachineModelPaneView,
                                                  this.dispensedItemView);
        


        this.vmSellingTopBarView.setExitBtnListener(e->
        {
            boolean isExit;

            ConfirmBox confirmBox;
            confirmBox = new ConfirmBox();
            if(confirmBox.display("WARNING", "You are leaving without sending out you Vending Machine, Are you sure?"))
            {

                resetForm();
                appController.resetForm();
                this.vmSellingTopBarView.changeWindowScene(targetExit);
            }
            else
            {
                e.consume();
            }
        });


        this.vmSellingTopBarView.setFinishBtnListener(e->
        {
            
            resetForm();
            this.vmSellingTopBarView.changeWindowScene(targetMaint);
                
        });

        this.dispensedItemView.setProceedBtnAction(e->
        {
            AlertBox alertBox;
            String msg;
            String itemName;
            int chosenSlot = -1;
            int stock;
            msg = this.appModel.proceedTransaction();
            if(!this.numPaneView.getNumField().getText().isEmpty())
                chosenSlot = this.dispensedItemView.getSlotNumLabelOfSelected();

            if(msg == null && chosenSlot != -1)
            {
                stock = Integer.parseInt(this.appModel.findSlotStockInVM(chosenSlot));
                itemName = this.appModel.findSlotNameInVM(chosenSlot);
                this.dispensedItemView.performDispense();
                System.out.println("CHANGE: " + this.appModel.getChangeAfterPayment().getDenominations());
                this.dispensedItemView.displayChange(this.appModel.getChangeAfterPayment(), 
                                                     this.appModel.getTotalChangeAfterPayment());
                this.vMachineModelPaneView.validateLabels(itemName, stock);

            }
            else if(msg != null)
            {
                alertBox = new AlertBoxRep("ALERT", msg);
            
            }
            else
            {
                alertBox = new AlertBoxRep("ALERT", msg);
            }
            this.denomNumPadView.getClrButton().fire();
            this.numPaneView.getClrButton().fire();                
            this.appModel.resetSellVar();

        });

        this.dispensedItemView.setCancelBtnAction(e->
        {
            this.appModel.discontinueTransaction();
            this.dispensedItemView.displayCurrentPayment();
            resetForm();
        });
        
    }
    public void resetForm() 
    {
        this.dispensedItemView.reset();
        this.denomNumPaneController.resetForm();
        this.numPaneController.resetForm();
    }





    private AppModel appModel;
    private NumPaneView numPaneView;
    private DenomNumPadView denomNumPadView;
    private NumPaneController numPaneController;
    private DenomNumPaneController denomNumPaneController;
    private DispensedItemView dispensedItemView;
    private VMSellingOpPaneView vmSellingOpPaneView;
    private VMSellingTopBarView vmSellingTopBarView;
    private VMachineModelPaneView vMachineModelPaneView;
    
}
