package MaintenanceLib;

public class MaintenanceController {
    public MaintenanceController(MaintSelectView maintSelectView, MaintenanceTopBarView maintenanceTopBarView)
    {
        this.maintSelectView = maintSelectView;

        maintSelectView.addActionRestockItemBtn(e->
        {
            this.maintSelectView.switchToRestockMenu();


        });

        maintenanceTopBarView.setExitBtnListener(e->
        {
            this.maintSelectView.switchDefaultSelectMaintMenu();
        });

    }

    private MaintSelectView maintSelectView;
    private MaintenanceTopBarView maintenanceTopBarView;
}
