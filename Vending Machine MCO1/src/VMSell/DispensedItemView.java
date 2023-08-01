package VMSell;


import java.util.LinkedHashMap;

import Boxes.AlertBox;
import Buttons.MenuButton;
import DenomLib.DenomSummaryView;
import DenomLib.Denomination;
import ItemSelectLib.ItemSectionPane;
import Labels.SubLabel;
import Labels.HeaderLabel;
import Models.VM_Item;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DispensedItemView extends ScrollPane
{
    public DispensedItemView()
    {

        HBox expandableHBox = new HBox();
        this.dispensedSectionGridPane = new GridPane();
        this.denomSummaryView = new DenomSummaryView();
        this.denominationCount = new LinkedHashMap<String, Integer>();
        this.mainCanvasVBox = new VBox();
        this.itemSelectedStackPane = new StackPane();
        this.claimBtn = new MenuButton("Claim Items", 24);
        this.proceedOrderBtn = new MenuButton("Proceed To Order", 24);
        this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setPrefViewportHeight(200);
        for (Denomination denomination : Denomination.values()) 
        {
            denominationCount.put(denomination.getName(), 0);
    
        }

        expandableHBox.setPrefHeight(500);
        denomSummaryView.setPrefWidth(400);
        this.mainCanvasVBox.setPrefWidth(400);
        this.mainCanvasVBox.setAlignment(Pos.CENTER);
        this.mainCanvasVBox.getChildren().addAll(denomSummaryView, claimBtn);
    
        this.itemSelectedStackPane.maxHeight(100);
        dispensedSectionGridPane.add(this.itemSelectedStackPane, 0, 0, 1, 1);
        dispensedSectionGridPane.add(this.mainCanvasVBox, 1, 0, 1, 3);
        
        expandableHBox.getChildren().addAll(this.itemSelectedStackPane, dispensedSectionGridPane);
        expandableHBox.setSpacing(100);
        expandableHBox.setPadding(new Insets(0, 0, 70, 250));


        this.setContent(expandableHBox);
        
        this.setVisible(false);
        this.setManaged(false);
    }
    public void addToPaymentView(String numPushed)
    {
        String denom;
        denom = inputtedButton(numPushed);
        denominationCount.put(denom, denominationCount.get(denom)+1);
        denomSummaryView.updateCountLabel(denom);

    }
    public void subToPaymentView(String numPushed)
    {
        String denom;
        denom = inputtedButton(numPushed);
        denominationCount.put(denom, denominationCount.get(denom)-1);
        denomSummaryView.subCountLabel(denom);

    }
    public void addItemSelected(StackPane item, String priceString, String calString)
    {
        StackPane tempPane;
        VBox itemInfoVBox;

        Label priceLabel = new SubLabel(priceString);
        Label calLabel = new SubLabel(calString);

        itemInfoVBox = new VBox();
        itemInfoVBox.getChildren().addAll(priceLabel, calLabel);
        this.itemSelectedStackPane = item;
        this.itemSelectedStackPane.setPrefHeight(200);
        this.itemSelectedStackPane.setPrefWidth(200);
        this.dispensedSectionGridPane.add(this.itemSelectedStackPane, 0, 0, 1, 1);
        this.dispensedSectionGridPane.add(this.itemSelectedStackPane, 0, 1, 1, 1);
            


    }

    public void displayDispensed()
    {

        this.setVisible(true);
        this.setManaged(true);
    }

    public void hideDispensed()
    {
        this.setVisible(false);
        this.setManaged(false);
    }
    private String inputtedButton(String numPushed)
    {
        double value;
        String denom;
        
        if (numPushed.contains("B")) {
            value = 20.0;
        } else if (numPushed.contains("C")) {
            value = 20.001;
        } 
        else 
        {

            value = Double.parseDouble(numPushed);
        }

        denom = Denomination.fromValue(value);
        return denom;

    }
    public void reset()
    {
        for (Denomination denomination : Denomination.values()) 
        {
            denominationCount.put(denomination.getName(), 0);
    
        }
        this.denomSummaryView.reset();
    }

    public void setClaimBtnAction(EventHandler<ActionEvent> eventHandler)
    {
        this.claimBtn.setOnAction(eventHandler);
    }
        

    private GridPane dispensedSectionGridPane;
    private LinkedHashMap<String, Integer> denominationCount;
    private VBox mainCanvasVBox;
    private StackPane itemSelectedStackPane;
    private DenomSummaryView denomSummaryView;
    private DenomSummaryView changeSummaryView;
    private AlertBox alertBox;
    private Button claimBtn;
    private Button proceedOrderBtn;

    
}
