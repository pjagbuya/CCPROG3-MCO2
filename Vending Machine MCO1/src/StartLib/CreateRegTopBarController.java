package StartLib;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import Boxes.AlertBox;
import Boxes.ConfirmBox;
import DenomLib.DenomSetSection;
import DenomLib.SetDenomPaneController;
import ItemSelectLib.ItemSectionPane;
import ItemSelectLib.CreateMenuController;
import ItemSelectLib.SetItemPaneView;
import VMLib.VMachineModelPaneView;
import VMSell.VMSellingOpPaneView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CreateRegTopBarController {
    

    public CreateRegTopBarController(AppController appController,
                                     SetDenomPaneController setDenomPaneController,
                                     VMachineModelPaneView vMachineModelPaneView,
                                     CreateMenuController createMenuController,
                                     SetItemPaneView setItemPaneView)

    {

        ConfirmBox confirmBox = new ConfirmBox();
        AlertBox alertBox = new AlertBox();


        this.createRegTopBarView= appController.getRegMenuTopBar();
        this.appView = appController.getAppView();
        this.appModel = appController.getAppModel();
        this.setItemPaneView = setItemPaneView;
        this.setDenomPaneController = setDenomPaneController;
        this.createMenuController = createMenuController;
        this.vMachineModelPaneView = vMachineModelPaneView;

        this.createRegTopBarView.setStageWidthPropertyListener((observable, oldValue, newValue) ->
        {
            // Recalculate margin values based on new window width (newValue)
            double leftRightMargin = newValue.doubleValue() * 0.125; // Just an example
            double topBottomMargin = newValue.doubleValue() * 0.01; // Just an example

            // Update the margins
            HBox.setMargin(this.createRegTopBarView.getNameLabel(), new Insets(topBottomMargin, leftRightMargin*0.2, topBottomMargin, leftRightMargin*2.2));
            HBox.setMargin(this.createRegTopBarView.getNameTextField(), new Insets(topBottomMargin, leftRightMargin*2.2, topBottomMargin, leftRightMargin*0.01));
        });


        this.createRegTopBarView.setExitBtnListener(e->{
            boolean isExit;

            isExit = confirmBox.display("Exit Out Of Vending Machine Maker", "Do you want to exit this and back to main menu?");
            if(!isExit)
                e.consume();
            else
            {
                setDenomPaneController.resetForm();
                createMenuController.resetForm();
                this.createRegTopBarView.changeWindowScene(this.appView.getStartMenu());

            }

        });

        this.createRegTopBarView.setFinishBtnListener(event->
        {
            int stockValue;
            boolean isFinish;
            LinkedHashMap<String, Integer> itemsInfo;
            LinkedHashMap<String, Integer> denomInfo;
            LinkedHashMap<String, Double> repriceInfo;
            ArrayList<String> orderOfSlots;
            ArrayList<ItemSectionPane> itemSectionPanes;
            DenomSetSection[] denomSetSections;
            orderOfSlots = new ArrayList<String>();
            denomSetSections = appController.getSetDenomPaneView().getDenomSetSections();
            itemSectionPanes = setItemPaneView.getItemSectionGridPanes();
            repriceInfo = new LinkedHashMap<String, Double>();
            itemsInfo = new LinkedHashMap<String, Integer>();
            denomInfo = new LinkedHashMap<String, Integer>();
            
            for (ItemSectionPane item : itemSectionPanes) {
                System.out.println(item.getStockField().getText());
                if (!item.getStockField().getText().isEmpty()) {
                    stockValue = Integer.parseInt(item.getStockField().getText());
                    if (stockValue > 0) {
                        repriceInfo.put(item.getItemNameLabel().getText(), item.getFinalPrice());
                        itemsInfo.put(item.getItemNameLabel().getText(), stockValue);
                    }
                }
            }
            
            System.out.println(repriceInfo);

            for(DenomSetSection denom : denomSetSections)
            {
                if(!denom.getTextField().getText().isEmpty())
                {

                    denomInfo.put(denom.getMoneyNameLabel().getText(), 
                                Integer.parseInt(denom.getTextField().getText()));
                }


            }
            for(Label label : vMachineModelPaneView.getItemNameLabels())
            {
                orderOfSlots.add(label.getText());
            }
            

            isFinish = this.appView.getCreateRegView().raiseConfirmBox("Proceed To Features", 
                                                                     "By finishing we will proceed to be testing to you the features of selling, Do you want to proceed?");
            if(!isFinish)
                event.consume();
            else
            { 
                if(!appModel.isVendingMachineSlotEmpty())
                {
                    System.out.println("Denoms" + denomInfo);
                    System.out.println("Items: " + itemsInfo);
                    appModel.addItemToVM(itemsInfo, orderOfSlots);
                    appModel.addReservesToVM(denomInfo);
                    appModel.repriceItem(repriceInfo);
                    this.appView.setVmSellingTopBarTitle(this.appModel.getVendingMachine().getName());
                    this.appView.getVmSellingOpPaneView().setMaxSlotVMView(appModel.getCurrSlotCap());
                    this.appView.getVmSellingOpPaneView().passVMDataToView(this.vMachineModelPaneView.getImageOrder(),
                                                                            orderOfSlots);
                    appView.changeToSellingScreen();
                    Platform.runLater(()->{
                        this.appView.getParentWin().setWidth(1200);
                        this.appView.getParentWin().setHeight(1000);
                        this.appView.getParentWin().centerOnScreen();
                    });
                }
                else
                {
                    alertBox.display("ERROR", "PLEASE FILL IN AT LEAST ONE ITEM FOR YOUR VENDING MACHINE");
                }



            }
            denomInfo.clear();
            itemsInfo.clear();


        });


    }
    private AppView appView;
    private AppModel appModel;
    private VMachineModelPaneView vMachineModelPaneView;
    private SetItemPaneView setItemPaneView;
    private SetDenomPaneController setDenomPaneController; 
    private CreateMenuController createMenuController;
    private CreateRegTopBarView createRegTopBarView;

}
