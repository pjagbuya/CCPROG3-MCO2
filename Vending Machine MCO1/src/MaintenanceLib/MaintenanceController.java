package MaintenanceLib;

import java.util.LinkedHashMap;
import java.util.Map;

import Models.DenominationItem;
import java.util.ArrayList;
import Boxes.ConfirmBox;
import DenomLib.SetDenomPaneController;
import ItemSelectLib.CreateMenuController;
import StartLib.AppController;
import StartLib.AppModel;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MaintenanceController {
    public MaintenanceController(AppController appController, MaintSelectView maintSelectView)
    {
        ConfirmBox confirmBox = new ConfirmBox();
        this.appModel = appController.getAppModel();
        this.parentWin = appController.getPrimaryWindow();
        this.maintenanceReplenishCollectView = maintSelectView.getMaintenanceReplenishCollectView();
        this.setDenomPaneController = new SetDenomPaneController(maintSelectView.getMaintenanceReplenishCollectView().getRightSetDenomPane(),
                                                               maintenanceReplenishCollectView, this.appModel);




        this.maintSelectView = maintSelectView;




        maintSelectView.addActionRestockRepriceItemBtn(e->
        {

            
            this.maintSelectView.switchToRestockRepriceMenu();


        });

        maintSelectView.addActionReplenishCollectBtn(e->
        {
            
  
            LinkedHashMap<String, ArrayList<DenominationItem>> rawDenom;
            LinkedHashMap<String, Integer> denomCount;
            rawDenom = this.appModel.getCashReserves();
            denomCount = getDenomCountVersh(rawDenom);
            this.maintSelectView.switchToReplenishCollectDenomMenu();
            this.maintSelectView.getMaintenanceReplenishCollectView().updateDenomSetSection(denomCount);
            this.maintSelectView.getMaintenanceReplenishCollectView().getRightSetDenomPane().disableTextFields();

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
            switch(i)
            {
                case 0:
                    this.maintSelectView.addActionTopBarExitBtn(i, e->
                    {

                        this.maintSelectView.switchDefaultSelectMaintMenu();

                    });
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }

        }






    }
    public void resetForm()
    {
        this.setDenomPaneController.resetForm();
        this.maintSelectView.getMaintenanceReplenishCollectView().resetCountLabels();
    }
    private LinkedHashMap<String, Integer> getDenomCountVersh(LinkedHashMap<String, ArrayList<DenominationItem>> denomInfo)
    {
        LinkedHashMap<String, Integer> tempMap;
        tempMap = new LinkedHashMap<String, Integer>();
        for(Map.Entry<String, ArrayList<DenominationItem>> denom : denomInfo.entrySet())
        {
            tempMap.put(denom.getKey(), denom.getValue().size());

        }
        return tempMap;
    }

    private SetDenomPaneController setDenomPaneController;
    private MaintenanceReplenishCollectView maintenanceReplenishCollectView;
    private Stage parentWin;
    private AppModel appModel;
    private MaintSelectView maintSelectView;

}
