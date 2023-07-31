package StartLib;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
            boolean isFinish;
            LinkedHashMap<String, Integer> itemsInfo;
            LinkedHashMap<String, Integer> denomInfo;
            ArrayList<String> orderOfSlots;
            ArrayList<ItemSectionPane> itemSectionPanes;
            DenomSetSection[] denomSetSections;
            orderOfSlots = new ArrayList<String>();
            denomSetSections = appController.getSetDenomPaneView().getDenomSetSections();
            itemSectionPanes = setItemPaneView.getItemSectionGridPanes();
            itemsInfo = new LinkedHashMap<String, Integer>();
            denomInfo = new LinkedHashMap<String, Integer>();
            for(ItemSectionPane item : itemSectionPanes)
            {
                if(!item.getStockField().getText().isEmpty())
                {

                    itemsInfo.put(item.getItemNameLabel().getText(), 
                                Integer.parseInt(item.getStockField().getText()));
                }


            }


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
                appModel.addItemToVM(itemsInfo, orderOfSlots);

                appModel.addReservesToVM(denomInfo);
                this.appView.setVmSellingTopBarTitle(this.appModel.getVendingMachine().getName());
                this.appView.getVmSellingOpPaneView().setMaxSlotVMView(appModel.getCurrSlotCap());
                this.appView.getVmSellingOpPaneView().passVMDataToView(this.vMachineModelPaneView.getImageOrder(),
                                                                        orderOfSlots);
                appView.changeToSellingScreen();


            }


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
