package StartLib;


import Buttons.MenuButton;
import CustomSetup.CustomTopBarView;
import MaintenanceLib.MaintSelectView;
import VMLib.VMachineModelPaneView;
import VMSell.VMSellingOpPaneView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppView extends BorderPane{
    public AppView(Stage parentWin)
    {

        this.parentWin = parentWin;
        this.startMenu = new Scene(this, 1200, 600);
        this.createRegMenu = new CreateRegMenu(parentWin);
        this.creationRegMenu = new Scene(this.createRegMenu, 1200, 600);
        
        this.vMachineModelPaneView = new VMachineModelPaneView(parentWin);
        this.setupVMPopUpView = new SetupVMPopUpView(parentWin, this.creationRegMenu);
        this.vmSellingOpPaneView = new VMSellingOpPaneView(parentWin);
        this.maintSelectView = new MaintSelectView(parentWin);



        
        this.sellingMenu = new Scene(this.vmSellingOpPaneView, 1200, 600);
        
        this.maintenanceMenu = this.maintSelectView.getMainSelectScene();
        changeToMainMenu();

    }
    public void changeToMainMenu()
    {
        String colorBg = "#071952";
        VBox btnSection = new VBox(20);

       

        


        btnSection.setAlignment(Pos.CENTER);
        btnSection.getChildren().addAll(BTN_CREATE, BTN_TESTF, BTN_EXIT);

        this.setCenter(btnSection);
        this.setStyle("-fx-base: " + colorBg+ ";");

        this.parentWin.setScene(this.startMenu);
        this.parentWin.setTitle("Main menu");
        this.parentWin.show();
    }


    public void changeToCreateRegScene()
    {

   

        this.parentWin.setScene(this.creationRegMenu);
    }
    public void changeToSellingScreen()
    {
 
        
        this.parentWin.setScene(this.sellingMenu);
    }

    public void changeToMaintenanceSelectScreen()
    {


        this.parentWin.setScene(this.maintenanceMenu);

    }    
    public void showPopUpView()
    {

        setupVMPopUpView.show();

    }

    public static MenuButton getBtnCreate() 
    {

        return BTN_CREATE;
    }
    public static MenuButton getBtnExit() 
    {
        return BTN_EXIT;
    }
    public static MenuButton getBtnTestf() 
    {
        return BTN_TESTF;
    }

    public Stage getParentWin() {
        return parentWin;
    }
    public CreateRegMenu getCreateRegMenu() {
        return createRegMenu;
    }
    public SetupVMPopUpView getSetupVMPopUpView() {
        return setupVMPopUpView;
    }public VMachineModelPaneView getvMachineModelPaneView() {
        return vMachineModelPaneView;
    }
    public VMSellingOpPaneView getVmSellingOpPaneView() {
        return vmSellingOpPaneView;
    }

    public MaintSelectView getMaintSelectView() {
        return maintSelectView;
    }

    public Scene getCreationRegMenu() {
        return creationRegMenu;
    }
    public Scene getMaintenanceMenu() {
        return maintenanceMenu;
    }
    public Scene getSellingMenu() {
        return sellingMenu;
    }
    public Scene getStartMenu() {
        return startMenu;
    }
    public void setBtnCreateRegAction(EventHandler<ActionEvent> eventHandler) 
    {
        BTN_CREATE.setOnAction(eventHandler);
    }

    public void setBtnExitAction(EventHandler<ActionEvent> eventHandler)
    {
        BTN_EXIT.setOnAction(eventHandler);
    }
    public void setBtnExitOnMaintSelectView(EventHandler<ActionEvent> eventHandler)
    {
        this.maintSelectView.addActionExitBtn(eventHandler);
    }

    private Stage parentWin;


    private VMachineModelPaneView vMachineModelPaneView;
    private MaintSelectView maintSelectView;
    private SetupVMPopUpView setupVMPopUpView;
    private CreateRegMenu createRegMenu;
    private VMSellingOpPaneView vmSellingOpPaneView;


    private Scene startMenu;
    private Scene maintenanceMenu;
    private Scene creationRegMenu;
    private Scene sellingMenu;

    private static final MenuButton BTN_CREATE = new MenuButton("Create a Vending Machine");
    private static final MenuButton BTN_TESTF = new MenuButton("Test Features of a Vending Machine");
    private static final MenuButton BTN_EXIT = new MenuButton("Exit");




}
