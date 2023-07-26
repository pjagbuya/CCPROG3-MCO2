package MaintenanceLib;



import Buttons.MenuButton;
import Labels.HeaderLabel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MaintSelectView 
{

    public MaintSelectView(Stage parentWin)
    {
        VBox mainCanvasVBox = new VBox();

        Label mainLabel = new HeaderLabel("Maintenance Features");
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
}
