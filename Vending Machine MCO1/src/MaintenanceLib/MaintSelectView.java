package MaintenanceLib;



import Buttons.MenuButton;
import ItemSelectLib.SetItemPaneController;
import ItemSelectLib.SetItemPaneView;
import Labels.HeaderLabel;
import Labels.LabelToField;
import NumPad.DenomNumPadView;
import NumPad.DenomNumPaneController;
import VMLib.VMachineModelPaneView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MaintSelectView extends BorderPane
{

    public MaintSelectView(Stage parentWin)
    {
        this.parentWin = parentWin;
        switchDefaultSelectMaintMenu();
    }
    public void switchDefaultSelectMaintMenu()
    {
        this.getChildren().clear();
        VBox mainCanvasVBox = new VBox();
        String colorBg = "#071952";
        Label mainLabel = new HeaderLabel("Maintenance Features", 72);
        mainCanvasVBox.setPadding(new Insets(30));
        mainCanvasVBox.setSpacing(10);   
        restockItemBtn = new MenuButton("Restock Item");
        repriceItemBtn = new MenuButton("Reprice Item");
        replaceItemBtn = new MenuButton("Replace Item");
        replenishDenomBtn = new MenuButton("Replenish Reserves");
        orderHisBtn = new MenuButton("View Order History");
        stockedInfoBtn = new MenuButton("Get stocked Information");
        exitBtn = new MenuButton("Exit");
        mainCanvasVBox.getChildren().addAll(
                                            mainLabel,
                                            restockItemBtn,
                                            repriceItemBtn,
                                            replaceItemBtn,
                                            replenishDenomBtn,
                                            orderHisBtn,
                                            stockedInfoBtn,
                                            exitBtn
                                            );
        mainCanvasVBox.setAlignment(Pos.CENTER);

        this.setCenter(mainCanvasVBox);
        this.setStyle("-fx-background-color:" +colorBg+ ";");
    }
    public void switchToRestockMenu()
    {
        Scene targetScene = new Scene(savedState, MIN_WIDTH, MIN_HEIGHT);
        VBox mainCanvasVBox = new VBox();
        Label titleLabel = new HeaderLabel("Test",72);
        VMachineModelPaneView vMachineModelPaneView = new VMachineModelPaneView(this.parentWin);
        SetItemPaneView setItemPaneView = new SetItemPaneView(parentWin);
        SetItemPaneController setItemPaneController = new SetItemPaneController(parentWin, setItemPaneView, vMachineModelPaneView);
        MaintenanceTopBarView maintenanceTopBarView = new MaintenanceTopBarView(parentWin, targetScene);
        
        mainCanvasVBox.getChildren().add(titleLabel);
        this.getChildren().clear();
        this.setTop(setItemPaneView);
        this.setCenter(vMachineModelPaneView);
        this.setRight(setItemPaneView);
    }
    public void switchToRepriceMenu() 
    {
        // Create necessary views and controllers for the reprice menu
        // (similar to what you did in switchToRestockMenu)
        // ...
    
        // Add any additional components or customization specific to the reprice menu
        // ...
    
        // Set the new components for the reprice menu
        // (similar to what you did in switchToRestockMenu)
        // ...
    }
    
    public void switchToReplaceMenu() {
        // Create necessary views and controllers for the replace menu
        // ...
    
        // Add any additional components or customization specific to the replace menu
        // ...
    
        // Set the new components for the replace menu
        // ...
    }
    
    public void switchToReplenishDenomMenu() {
        DenomNumPadView denomNumPadView = new DenomNumPadView();
        DenomNumPaneController denomNumPaneController = new DenomNumPaneController(denomNumPadView);



        this.getChildren().clear();


    }
    
    public void switchToOrderHistoryMenu() {
        // Create necessary views and controllers for the order history menu
        // ...
    
        // Add any additional components or customization specific to the order history menu
        // ...
    
        // Set the new components for the order history menu
        // ...
    }
    
    public void switchToStockedInfoMenu() {
        // Create necessary views and controllers for the stocked info menu
        // ...
    
        // Add any additional components or customization specific to the stocked info menu
        // ...
    
        // Set the new components for the stocked info menu
        // ...
    }

    
    
    

    public MenuButton getExitBtn() {
        return exitBtn;
    }
    public MenuButton getOrderHisBtn() {
        return orderHisBtn;
    }
    public MenuButton getReplaceItemBtn() {
        return replaceItemBtn;
    }
    public MenuButton getReplenishDenomBtn() {
        return replenishDenomBtn;
    }
    public MenuButton getRepriceItemBtn() {
        return repriceItemBtn;
    }
    public MenuButton getRestockItemBtn() {
        return restockItemBtn;
    }
    public MenuButton getStockedInfoBtn() {
        return stockedInfoBtn;
    }


    public double getMIN_HEIGHT() 
    {
        return MIN_HEIGHT;
    }
    public double getMIN_WIDTH() 
    {
        return MIN_WIDTH;
    }

    public void addActionRestockItemBtn(EventHandler<ActionEvent> eventHandler) 
    {
        restockItemBtn.setOnAction(eventHandler);
    }
    
    public void addActionRepriceItemBtn(EventHandler<ActionEvent> eventHandler) 
    {
        repriceItemBtn.setOnAction(eventHandler);
    }
    
    public void addActionReplaceItemBtn(EventHandler<ActionEvent> eventHandler) 
    {
        replaceItemBtn.setOnAction(eventHandler);
    }
    
    public void addActionReplenishDenomBtn(EventHandler<ActionEvent> eventHandler) 
    {
        replenishDenomBtn.setOnAction(eventHandler);
    }
    
    public void addActionOrderHisBtn(EventHandler<ActionEvent> eventHandler) 
    {
        orderHisBtn.setOnAction(eventHandler);
    }
    
    public void addActionStockedInfoBtn(EventHandler<ActionEvent> eventHandler) 
    {
        stockedInfoBtn.setOnAction(eventHandler);
    }
    
    public void addActionExitBtn(EventHandler<ActionEvent> eventHandler) 
    {
        exitBtn.setOnAction(eventHandler);
    }


    private MenuButton restockItemBtn;
    private MenuButton repriceItemBtn;
    private MenuButton replaceItemBtn;
    private MenuButton replenishDenomBtn;
    private MenuButton orderHisBtn;
    private MenuButton stockedInfoBtn;
    private MenuButton exitBtn;
    private BorderPane savedState;
    private Stage parentWin;
    private final double MIN_HEIGHT = 800;
    private final double MIN_WIDTH = 1200;
}
