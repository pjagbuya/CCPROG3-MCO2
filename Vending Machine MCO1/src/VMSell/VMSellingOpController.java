package VMSell;

import CustomSetup.CustomTopBarView;
import MaintenanceLib.MaintSelectView;
import MaintenanceLib.MaintenanceController;
import MaintenanceLib.MaintenanceTopBarView;
import javafx.scene.Scene;

public class VMSellingOpController 
{
    public VMSellingOpController(VMSellingTopBarView vmSellingTopBarView,
                                 Scene targetExit,
                                 Scene targetMaint)
    {


        this.vmSellingTopBarView = vmSellingTopBarView;

        this.vmSellingTopBarView.setExitBtnListener(e->
        {
            this.vmSellingTopBarView.changeWindowScene(targetExit);
        });


        this.vmSellingTopBarView.setFinishBtnListener(e->
        {

            this.vmSellingTopBarView.changeWindowScene(targetMaint);
                
        });
        
    }

    private VMSellingTopBarView vmSellingTopBarView;
    
}
