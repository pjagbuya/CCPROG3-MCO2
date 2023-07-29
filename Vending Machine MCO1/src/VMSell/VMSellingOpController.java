package VMSell;

import CustomSetup.CustomTopBarView;
import MaintenanceLib.MaintSelectView;
import MaintenanceLib.MaintenanceController;
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
                MaintSelectView maintSelectView = new MaintSelectView(vmSellingTopBarView.getParentWin());

                System.out.print(maintSelectView.getExitBtn().getText());
                Scene maintenanceScene = new Scene(maintSelectView, maintSelectView.getMIN_WIDTH(), maintSelectView.getMIN_HEIGHT());

                // resetForm();
                this.vmSellingTopBarView.changeWindowScene(maintenanceScene);


                
        });
        
    }

    private CustomTopBarView vmSellingTopBarView;
    
}
