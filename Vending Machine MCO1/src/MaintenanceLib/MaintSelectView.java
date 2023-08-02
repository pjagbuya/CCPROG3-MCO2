package MaintenanceLib;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import Buttons.MenuButton;
import CustomSetup.GeneralEventHandler;
import DenomLib.SetDenomPaneView;
import ItemSelectLib.CreateMenuController;
import ItemSelectLib.SetItemPaneView;
import Labels.HeaderLabel;
import Labels.LabelToField;
import NumPad.DenomNumPadView;
import NumPad.DenomNumPaneController;
import VMLib.VMachineModelPaneView;
import VMSell.VMSellingOpPaneView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MaintSelectView
{

    public MaintSelectView(Stage parentWin)
    {   
        
        this.parentWin = parentWin;
        this.maintenanceTopBarViews = new ArrayList<MaintenanceTopBarView>();

        this.maintenanceReplaceView = new MaintenanceReplaceView(parentWin);
        this.maintenanceReplenishCollectView = new MaintenanceReplenishCollectView(parentWin);
        this.vMachineModelPaneView = new VMachineModelPaneView(parentWin);
        this.maintenanceRestockRepriceView = new MaintenanceRestockRepriceView(parentWin);
        this.setItemPaneView = new SetItemPaneView(parentWin);
        this.maintenanceOrderHisView = new MaintenanceOrderHisView(parentWin);
        this.maintenanceStockedInfoView = new MaintenanceStockedInfoView(parentWin);
        
        
        for(int i = 0; i < 5; i++)
        {
            maintenanceTopBarViews.add(new MaintenanceTopBarView(parentWin));
            maintenanceTopBarViews.get(i).removeFinishBtn();
        }
        // Build all necessary scenes to switch from this view
        
        
        setMainSelectScene();
        setRestockReprScene(maintenanceTopBarViews.get(0));
        setReplaceScene(maintenanceTopBarViews.get(1));
        setReplenishCollectScene(maintenanceTopBarViews.get(2));
        setOrderHisScene(maintenanceTopBarViews.get(3));
        setStockedInfoScene(maintenanceTopBarViews.get(4));


        // set the one to first show is the select screen
        switchDefaultSelectMaintMenu();

        initializeButtonHandlers();
    }
    public void updateReferedFields()
    {
        
    }
    public void setSetItemPaneView(SetItemPaneView setItemPaneView) {
        this.setItemPaneView = setItemPaneView;
    }
    public void setvMachineModelPaneView(VMachineModelPaneView vMachineModelPaneView) {
        this.vMachineModelPaneView = vMachineModelPaneView;
    }
 
    public void switchDefaultSelectMaintMenu()
    {
        
        this.parentWin.setScene(mainSelectScene);
        
    }
    public void switchToRestockRepriceMenu()
    {

        this.parentWin.setScene(restockReprScene);
 
 
    }

    
    public void switchToReplaceMenu() 
    {
        this.parentWin.setScene(replaceScene);

    }
    
    public void switchToReplenishCollectDenomMenu() 
    {
        this.parentWin.setScene(replenishCollectScene);
    }
    
    public void switchToOrderHistoryMenu() 
    {
        this.parentWin.setScene(orderHisScene);

    }
    
    public void switchToStockedInfoMenu() 
    {
        
        this.parentWin.setScene(stockedInfoScene);


    }

    public void initializeButtonHandlers() {
        buttonHandlers = new HashMap<>();
        
        // Register each button with its event handler
        buttonHandlers.put(restockReprItemBtn, new GeneralEventHandler());
        buttonHandlers.put(replaceItemBtn, new GeneralEventHandler());
        buttonHandlers.put(replCollectBtn, new GeneralEventHandler());
        buttonHandlers.put(orderHisBtn, new GeneralEventHandler());
        buttonHandlers.put(stockedInfoBtn, new GeneralEventHandler());
        buttonHandlers.put(exitBtn, new GeneralEventHandler());
    }

    public void addActionToButton(MenuButton button, EventHandler<ActionEvent> action) {
        if (!buttonHandlers.containsKey(button)) {
            return;
        }
        
        buttonHandlers.get(button).addHandler(action);
        button.setOnAction(buttonHandlers.get(button));
    }
    public Stage getParentWin() {
        return parentWin;
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
    public MenuButton getReplCollectBtn() {
        return replCollectBtn;
    }
    public MenuButton getRestockReprItemBtn() {
        return restockReprItemBtn;
    }
    public MenuButton getStockedInfoBtn() {
        return stockedInfoBtn;
    }
    public MaintenanceReplenishCollectView getMaintenanceReplenishCollectView() {
        return maintenanceReplenishCollectView;
    }
    public SetItemPaneView getSetItemPaneView() {
        return setItemPaneView;
    }
    public VMachineModelPaneView getvMachineModelPaneView() {
        return vMachineModelPaneView;
    }

    public Scene getMainSelectScene() {
        return mainSelectScene;
    }
    public double getMIN_HEIGHT() 
    {
        return MIN_HEIGHT;
    }
    public double getMIN_WIDTH() 
    {
        return MIN_WIDTH;
    }

    public void addActionRestockRepriceItemBtn(EventHandler<ActionEvent> eventHandler) 
    {
        this.restockReprItemBtn.setOnAction(eventHandler);
    }

    public void addActionReplaceItemBtn(EventHandler<ActionEvent> eventHandler) {
        replaceItemBtn.setOnAction(eventHandler);
    }

    public void addActionReplenishCollectBtn(EventHandler<ActionEvent> eventHandler) {
        replCollectBtn.setOnAction(eventHandler);
    }

    public void addActionOrderHisBtn(EventHandler<ActionEvent> eventHandler) {
        orderHisBtn.setOnAction(eventHandler);
    }

    public void addActionStockedInfoBtn(EventHandler<ActionEvent> eventHandler) {
        stockedInfoBtn.setOnAction(eventHandler);
    }

    public void addActionExitBtn(EventHandler<ActionEvent> eventHandler) {
        exitBtn.setOnAction(eventHandler);
    }

    public void addActionTopBarExitBtn(int trackedIndex, EventHandler<ActionEvent> eventHandler) 
    {
        this.maintenanceTopBarViews.get(trackedIndex).setExitBtnListener(eventHandler);
    }

    public void addMoreActToFinishBtnListener(EventHandler<ActionEvent> eventHandler) 
    {
        exitBtn.setOnAction(eventHandler);
    }

    private void setMainSelectScene() 
    {
        newRootPane();
        VBox mainCanvasVBox = new VBox();
        String colorBg = "#071952";
        Label mainLabel = new HeaderLabel("Maintenance Features", 72);

        clearScreen();
        mainCanvasVBox.setPadding(new Insets(30));
        mainCanvasVBox.setSpacing(10);   
        restockReprItemBtn = new MenuButton("Restock/Take/Reprice Item");
        replaceItemBtn = new MenuButton("Replace Item");
        replCollectBtn = new MenuButton("Replenish/Collect Reserves");

        orderHisBtn = new MenuButton("View Order History");
        stockedInfoBtn = new MenuButton("Get stocked Information");
        exitBtn = new MenuButton("Exit");
        mainCanvasVBox.getChildren().addAll(
                                            mainLabel,
                                            restockReprItemBtn,
                                            replaceItemBtn,
                                            replCollectBtn,
                                            orderHisBtn,
                                            stockedInfoBtn,
                                            exitBtn
                                            );
        mainCanvasVBox.setAlignment(Pos.CENTER);


        this.rootPane.setCenter(mainCanvasVBox);

        this.mainSelectScene = new Scene(this.rootPane, MIN_WIDTH, MIN_HEIGHT);

    }

    private void setRestockReprScene(MaintenanceTopBarView maintenanceTopBarView) 
    {
        
        String colorBg = "#071952";
        VBox mainCanvasVBox = new VBox();
        Label titleLabel = new HeaderLabel("Test",72);

        newRootPane();
        clearScreen();

        vMachineModelPaneView.setStyle("-fx-background-color: " +BG_COLOR+ ";");


        mainCanvasVBox.getChildren().add(titleLabel);

        this.rootPane.setTop(maintenanceTopBarView);
        this.rootPane.setCenter(this.vMachineModelPaneView);
        this.rootPane.setRight(this.setItemPaneView);

        this.restockReprScene = new Scene(this.rootPane, MIN_WIDTH, MIN_HEIGHT);

    }
    private void setReplaceScene(MaintenanceTopBarView maintenanceTopBarView) 
    {
        newRootPane();
        clearScreen();

        
        this.rootPane.setTop(maintenanceTopBarView);
        this.rootPane.setCenter(maintenanceReplaceView.getCenter());
        this.rootPane.setRight(maintenanceReplaceView.getRight());

        this.replaceScene = new Scene(this.rootPane, MIN_WIDTH, MIN_HEIGHT);
    }
    private void setReplenishCollectScene(MaintenanceTopBarView maintenanceTopBarView) 
    {
        newRootPane();
        clearScreen();
 
        this.rootPane.setTop(maintenanceTopBarView);
        this.rootPane.setCenter(this.maintenanceReplenishCollectView.getCenter());
        this.rootPane.setRight(this.maintenanceReplenishCollectView.getRight());
        this.replenishCollectScene = new Scene(this.rootPane, MIN_WIDTH, MIN_HEIGHT);
    }

    private void setOrderHisScene(MaintenanceTopBarView maintenanceTopBarView) 
    {
        
        Label orderHistoryLabel = new HeaderLabel("Order History", 24);
        newRootPane();

        clearScreen();

        maintenanceTopBarView.addToChildren(orderHistoryLabel);

        this.rootPane.setTop(maintenanceTopBarView);
        this.rootPane.setCenter(maintenanceOrderHisView);
        this.orderHisScene = new Scene(this.rootPane, MIN_WIDTH, MIN_HEIGHT);

    }
    private void setStockedInfoScene(MaintenanceTopBarView maintenanceTopBarView) 
    {
        newRootPane();
        clearScreen();

        this.rootPane.setCenter(maintenanceStockedInfoView.getCenter());
        this.rootPane.setTop(maintenanceTopBarView);
        this.stockedInfoScene = new Scene(this.rootPane, MIN_WIDTH, MIN_HEIGHT);
    }

    private void newRootPane()
    {
        String colorBg = "#071952";
        this.rootPane = new BorderPane();
        rootPane.setStyle("-fx-base:" +colorBg+ ";");

    }
    private void clearScreen()
    {
        this.rootPane.setCenter(null);
        this.rootPane.setTop(null);
        this.rootPane.setBottom(null);
        this.rootPane.setLeft(null);
        this.rootPane.setRight(null);
    }

    private MenuButton restockReprItemBtn;
    private MenuButton replaceItemBtn;
    private MenuButton replCollectBtn;
    private MenuButton orderHisBtn;
    private MenuButton stockedInfoBtn;
    private MenuButton exitBtn;

    private Stage parentWin;
    private Map<MenuButton, GeneralEventHandler> buttonHandlers;



    private BorderPane rootPane;
    private Scene mainSelectScene;
    private Scene orderHisScene;
    private Scene replenishCollectScene;
    private Scene replaceScene;
    private Scene restockReprScene;
    private Scene stockedInfoScene;

    private MaintenanceRestockRepriceView maintenanceRestockRepriceView;
    private MaintenanceStockedInfoView maintenanceStockedInfoView;
    private MaintenanceOrderHisView maintenanceOrderHisView;
    private MaintenanceReplenishCollectView maintenanceReplenishCollectView;
    private MaintenanceReplaceView maintenanceReplaceView;
    private VMachineModelPaneView vMachineModelPaneView;
    private SetItemPaneView setItemPaneView;
    private ArrayList<MaintenanceTopBarView> maintenanceTopBarViews;


    private final double MIN_HEIGHT = 800;
    private final double MIN_WIDTH = 1200;
    private final String BG_COLOR = "#071952";
}
