package VMSell;

import CustomSetup.CustomTopBarView;
import MaintenanceLib.MaintSelectView;
import MaintenanceLib.MaintenanceController;
import MaintenanceLib.MaintenanceTopBarView;
import javafx.scene.Scene;

public class VMSellingOpController 
{
    public VMSellingOpController(CustomTopBarView vmSellingTopBarView)
    {


        this.vmSellingTopBarView = vmSellingTopBarView;

        this.vmSellingTopBarView.setExitBtnListener(e->
        {
            
        });


        this.vmSellingTopBarView.setFinishBtnListener(e->
        {


                
        });
        
    }

    private CustomTopBarView vmSellingTopBarView;
    
}
