package MaintenanceLib;

import Boxes.ConfirmBox;
import DenomLib.SetDenomPaneController;
import ItemSelectLib.CreateMenuController;

public class MaintenanceController {
    public MaintenanceController(MaintSelectView maintSelectView)
    {
        ConfirmBox confirmBox = new ConfirmBox();
        MaintenanceReplenishCollectView maintenanceReplenishCollectView = maintSelectView.getMaintenanceReplenishCollectView();
        
        SetDenomPaneController setDenomPaneController = new SetDenomPaneController(maintSelectView.getMaintenanceReplenishCollectView().getRightSetDenomPane(),
                                                                                   maintenanceReplenishCollectView);

        this.maintSelectView = maintSelectView;
        

        maintSelectView.addActionRestockRepriceItemBtn(e->
        {

            
            this.maintSelectView.switchToRestockRepriceMenu();


        });

        maintSelectView.addActionReplenishCollectBtn(e->
        {
            this.maintSelectView.switchToReplenishCollectDenomMenu();
        });

        maintSelectView.addActionStockedInfoBtn(e->
        {
            this.maintSelectView.switchToStockedInfoMenu();
        });

        

        maintSelectView.addActionReplaceItemBtn(e->
        {
            this.maintSelectView.switchToReplaceMenu();
        }
        );

        this.maintSelectView.addActionOrderHisBtn(e->
        {
            this.maintSelectView.switchToOrderHistoryMenu();
        });

        this.maintSelectView.addActionStockedInfoBtn(e->
        {
            this.maintSelectView.switchToStockedInfoMenu();
        });


        
        for(int i = 0; i < 5; i++)
        {
            this.maintSelectView.addActionTopBarExitBtn(i, e->
            {

                this.maintSelectView.switchDefaultSelectMaintMenu();

            });
        }






    }

    private MaintSelectView maintSelectView;

}
