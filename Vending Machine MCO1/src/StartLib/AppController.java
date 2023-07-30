package StartLib;

import Boxes.ConfirmBox;
import DenomLib.SetDenomPaneController;
import ItemSelectLib.SetItemPaneController;
import MaintenanceLib.MaintenanceController;
import MaintenanceLib.MaintenanceRestockRepriceView;
import NumPad.DenomNumPaneController;
import NumPad.NumPaneController;
import VMLib.VMachineModelPaneController;
import VMSell.VMSellingOpController;
import VMSell.VMSellingOpPaneView;
import VMSell.VMSellingTopBarView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppController {
    public AppController (AppView appView)
    {
        

        this.appView = appView;
        this.regMenu = this.appView.getCreateRegMenu();
        this.regMenuTopBar = this.regMenu.getCreateRegTopBarView();
        this.setUpVMPopUpView = this.appView.getSetupVMPopUpView();
        this.primaryWindow = this.appView.getParentWin();

        

        setupShowPopUpCreateBtn();

        this.appView.setBtnExitAction(e->
        {
            closeProgram(this.primaryWindow);
        });

        this.primaryWindow.setOnCloseRequest(e-> {
            e.consume();            // This line ensures the request of closing the program isnt proceeded
            closeProgram(this.primaryWindow);
        });


        setBehaviorCreateRegMenu();

    }

    private void setupShowPopUpCreateBtn()
    {


        VMSellingOpPaneView vmSellingOpPaneView = this.appView.getVmSellingOpPaneView();
        VMSellingTopBarView vmSellingTopBarView = vmSellingOpPaneView.getVmSellingTopBarView();
        ConfirmBox confirmBox = new ConfirmBox();
        
        
        SetItemPaneController setItemPaneController = new SetItemPaneController(primaryWindow, regMenu.getRightSetItemPane(), regMenu.getvMachineModelPaneView());
        SetDenomPaneController setDenomPaneController = new SetDenomPaneController(this.regMenu.getLeftDenominationsPane());
        VMachineModelPaneController vMachineModelPaneController = new VMachineModelPaneController(this.regMenu.getRightSetItemPane(), 
                                                                                                    this.regMenu.getvMachineModelPaneView());
        CreateRegTopBarController createRegTopBarController = new CreateRegTopBarController(this.regMenu.getCreateRegTopBarView(), 
                                                                                            setDenomPaneController, setItemPaneController,
                                                                                            this.appView.getStartMenu());
        DenomNumPaneController denomNumPaneController = new DenomNumPaneController(vmSellingOpPaneView.getDenomNumPadView());
        
        NumPaneController numPaneController = new NumPaneController(vmSellingOpPaneView.getNumPaneView());
        
        
        MaintenanceController maintenanceController = new MaintenanceController(appView.getMaintSelectView());
        
        
        
        SetupVMPopUpController setupVMPopUpController = new SetupVMPopUpController(this.appView.getSetupVMPopUpView());
               
        // Upon creation of vending machine initiate all necessary controllers depending on certain trigger finish btns
        this.appView.setBtnCreateRegAction(e->
        {
appView.showPopUpView();

            // Access topbar of popup and add controllers to components of popupview
            this.setUpVMPopUpView.setHidTopBarOnFinishBtn(event->{
                if(!this.appView.getSetupVMPopUpView().getNameField().getText().isEmpty())
                {

                    setupVMPopUpController.resetForm();
                    appView.changeToCreateRegScene();
                }


            });

            this.regMenuTopBar.setFinishBtnListener(event->
            {
                boolean isFinish;

                isFinish = this.regMenu.raiseConfirmBox("Proceed To Features", "By finishing we will proceed to be testing to you the features of selling, Do you want to proceed?");
                if(!isFinish)
                    e.consume();
                else
                {

                    appView.changeToSellingScreen();
        
                }
                


            });

            vmSellingTopBarView.setFinishBtnListener(event->{

                appView.changeToMaintenanceSelectScreen();
                

                
            });




        });

        this.appView.setBtnExitOnMaintSelectView(event ->
        {
            boolean isToSellScreen;

 
            isToSellScreen = confirmBox.display("Alert", "Would you like to go back to sell screen? Pressing no puts you to main menu");
            if(isToSellScreen)
            {
                this.appView.changeToSellingScreen();
            }
            else
            {
                this.appView.changeToMainMenu();
            }
        });



    }
    private void setBehaviorCreateRegMenu()
    {
        
    }
    private void closeProgram(Stage window)
    {   ConfirmBox boxMessage = new ConfirmBox();
        boolean answer = boxMessage.display("Warning", "Are you sure you want to exit?");


        if(answer)
            this.primaryWindow.close();
    }


    public CreateRegMenu changeToRegMenu() 
    {
        return regMenu;
    }
    private CreateRegMenu regMenu;
    private CreateRegTopBarView regMenuTopBar;
    private SetupVMPopUpView setUpVMPopUpView;
    private Scene mainMenuScene;
    private Stage primaryWindow;
    private AppView appView;
}
