package VMSell;

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

        DenomNumPaneController denomNumPaneController;
        NumPaneController numPaneController;
        

        this.vmSellingTopBarView = vmSellingTopBarView;
        this.vmSellingOpPaneView = vmSellingOpPaneView;


        
        
        denomNumPaneController = new DenomNumPaneController(appController,
                                                            this.vmSellingOpPaneView.getDenomNumPadView(),
                                                            this.vmSellingOpPaneView.getDispensedItemView());
        numPaneController = new NumPaneController(appController,
                                                  vmSellingOpPaneView.getNumPaneView(),
                                                  this.vmSellingOpPaneView .getvMachineModelPaneView(),
                                                  this.vmSellingOpPaneView .getDispensedItemView());
        


        this.vmSellingTopBarView.setExitBtnListener(e->
        {
            this.vmSellingTopBarView.changeWindowScene(targetExit);
        });


        this.vmSellingTopBarView.setFinishBtnListener(e->
        {

            this.vmSellingTopBarView.changeWindowScene(targetMaint);
                
        });
        
    }

    private VMSellingOpPaneView vmSellingOpPaneView;
    private VMSellingTopBarView vmSellingTopBarView;
    
}
