package StartLib;

import Boxes.ConfirmBox;
import DenomLib.SetDenomPaneController;
import DenomLib.SetDenomPaneView;
import ItemSelectLib.CreateMenuController;
import ItemSelectLib.SetItemPaneView;
import MaintenanceLib.MaintSelectView;
import MaintenanceLib.MaintenanceController;
import MaintenanceLib.MaintenanceRestockRepriceView;
import NumPad.DenomNumPaneController;
import NumPad.NumPaneController;

import VMSell.VMSellingOpController;
import VMSell.VMSellingOpPaneView;
import VMSell.VMSellingTopBarView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppController {
    public AppController (AppView appView, AppModel appModel)
    {
        ConfirmBox confirmBox = new ConfirmBox();

        VMSellingOpPaneView vmSellingOpPaneView;
        VMSellingTopBarView vmSellingTopBarView;
    


        // VMachineModelPaneController vMachineModelPaneController;

        MaintenanceController maintenanceController;
        SetupVMPopUpController setupVMPopUpController;
           

        this.appView = appView;
        this.appModel = appModel;
        this.regMenu = this.appView.getCreateRegView();
        this.maintSelectView = this.appView.getMaintSelectView();
        this.regMenuTopBar = this.regMenu.getCreateRegTopBarView();
        this.setItemPaneView = this.regMenu.getRightSetItemPane();
        this.setDenomPaneView = this.regMenu.getLeftDenominationsPane();
        this.setUpVMPopUpView = this.appView.getSetupVMPopUpView();
        this.primaryWindow = this.appView.getParentWin();

        vmSellingOpPaneView = this.appView.getVmSellingOpPaneView();
        vmSellingTopBarView = vmSellingOpPaneView.getVmSellingTopBarView();

        // Handles control for all components inthe Selling screen
        this.vmSellingOpController = new VMSellingOpController(this,
                                                          vmSellingTopBarView, vmSellingOpPaneView, 
                                                          this.appView.getSellOrMaintMenu(), this.appView.getMaintenanceMenu());
        
        // Handles control for creation of Vending machine menu
        this.createMenuController = new CreateMenuController( this, this.setItemPaneView, 
                                                         this.regMenu.getvMachineModelPaneView(), 
                                                         this.setDenomPaneView, this.regMenuTopBar);

        // vMachineModelPaneController = new VMachineModelPaneController(this.regMenu.getRightSetItemPane(), 
        //                                                               this.regMenu.getvMachineModelPaneView());


        this.maintenanceController = new MaintenanceController(appView.getMaintSelectView());
        this.setupVMPopUpController = new SetupVMPopUpController(this.appView.getSetupVMPopUpView(), 
                                                                this.regMenuTopBar, this.regMenu, 
                                                                this.setItemPaneView, this.appModel);
           

        setupShowPopUpCreateBtn();
        setUpSellOrMaintBtns();

        this.appView.setBtnToMaintenanceAction(
            e->
            {
                this.appView.changeToMaintenanceSelectScreen();
            }
        );
        this.appView.setBtnExitAction(e->
        {
            closeProgram(this.primaryWindow);
        });

        this.primaryWindow.setOnCloseRequest(e-> {
            e.consume();            // This line ensures the request of closing the program isnt proceeded
            closeProgram(this.primaryWindow);
        });






        // ON exit of maintenance menu go back to this menu
        this.appView.setBtnExitOnMaintSelectView(event ->
        {
            this.appView.changeToSellOrMaintView();

        });

    }


    public void resetForm()
    {
        vmSellingOpController.resetForm();
        maintenanceController.resetForm();
        createMenuController.resetForm();
        this.appModel.restartModel();


    }
    public void createMenuResetresetForm()
    {

        createMenuController.resetForm();



    }
    private void setupShowPopUpCreateBtn()
    {
        ConfirmBox confirmBox = new ConfirmBox();
        // Upon creation of vending machine initiate all necessary controllers depending on certain trigger finish btns
        this.appView.setBtnCreateRegAction(e->
        {
            appView.showPopUpView();

        });


    }
    private void setUpSellOrMaintBtns()
    {
        ConfirmBox confirmBox = new ConfirmBox();
        this.appView.setBtnToExitOutSellOrMaintMenu(e->
        {
            boolean isToExit;


 
            isToExit = confirmBox.display("Alert", "This will put you back to main menu, are you sure?");
            if(isToExit)
            {
                this.appView.changeToMainMenu();
            }
            else
            {
                e.consume();
            }

        }
            
        );
        this.appView.setBtnSellAction(e->{
            this.appView.changeToSellingScreen();
        });
        this.appView.setBtnToMaint(e->
        {
            this.maintSelectView.setSetItemPaneView(this.setItemPaneView);
            this.maintSelectView.setvMachineModelPaneView(this.appView.getVmSellingOpPaneView().getvMachineModelPaneView());
            this.appView.changeToMaintenanceSelectScreen();
        });

    }

    private void closeProgram(Stage window)
    {   ConfirmBox boxMessage = new ConfirmBox();
        boolean answer = boxMessage.display("Warning", "Are you sure you want to exit?");


        if(answer)
            this.primaryWindow.close();
    }

    public Stage getPrimaryWindow() {
        return primaryWindow;
    }
    public AppModel getAppModel() {
        return appModel;
    }
    public AppView getAppView() {
        return appView;
    }
    public SetDenomPaneView getSetDenomPaneView() {
        return setDenomPaneView;
    }
    public CreateRegTopBarView getRegMenuTopBar() {
        return regMenuTopBar;
    }
    public CreateRegMenu changeToRegMenu() 
    {
        return regMenu;
    }
    
    private MaintenanceController maintenanceController;
    private SetupVMPopUpController setupVMPopUpController;
    private VMSellingOpController vmSellingOpController;    
    private CreateMenuController createMenuController;
    private CreateRegMenu regMenu;
    private CreateRegTopBarView regMenuTopBar;
    private SetItemPaneView setItemPaneView;
    private SetDenomPaneView setDenomPaneView;
    private SetupVMPopUpView setUpVMPopUpView;
    private MaintSelectView maintSelectView;
    private Stage primaryWindow;
    private AppModel appModel;
    private AppView appView;
}
