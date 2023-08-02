package VMSell;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import Boxes.AlertBox;
import Boxes.PopUpBox;
import Buttons.MenuButton;
import DenomLib.DenomSummaryView;
import DenomLib.Denomination;
import ItemSelectLib.ItemSectionPane;
import Labels.SubLabel;
import Labels.HeaderLabel;
import Models.DenominationItem;
import Models.Money;
import Models.VM_Item;
import VMLib.ItemSlotPaneView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * This class is a dispensed item view that shows any of the things the user wishes to see dispensed and readies the popup to dispense
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class DispensedItemView extends ScrollPane
{
    
    public DispensedItemView()
    {

        HBox expandableHBox = new HBox();
        this.denomLabel =  new SubLabel("Inserted Amounts");

        this.dispensedSectionGridPane = new GridPane();
        this.denomSummaryView = new DenomSummaryView();

        this.changeSummaryView = new DenomSummaryView();
        this.mainCanvasVBox = new VBox();
        this.itemSelectedStackPane = new StackPane();

        this.proceedOrderBtn = new MenuButton("Proceed To Order", 24);
        this.cancelOrderBtn = new MenuButton("Cancel Order", 24);
        this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setPrefViewportHeight(200);

        expandableHBox.setPrefHeight(500);
        denomSummaryView.setPrefWidth(400);
        this.mainCanvasVBox.setPrefWidth(400);
        this.mainCanvasVBox.setAlignment(Pos.CENTER);
        this.mainCanvasVBox.getChildren().addAll(denomLabel, denomSummaryView, changeSummaryView);
    
        this.itemSelectedStackPane.maxHeight(100);
        dispensedSectionGridPane.add(this.itemSelectedStackPane, 0, 0, 1, 1);
        dispensedSectionGridPane.add(this.proceedOrderBtn, 2, 0, 1, 1);
        dispensedSectionGridPane.add(this.cancelOrderBtn, 2, 1, 1, 1);
        dispensedSectionGridPane.add(this.mainCanvasVBox, 1, 0, 1, 3);
        
        expandableHBox.getChildren().addAll(this.itemSelectedStackPane, dispensedSectionGridPane);
        expandableHBox.setSpacing(100);
        expandableHBox.setPadding(new Insets(0, 0, 70, 200));
        displayCurrentPayment();

        this.setContent(expandableHBox);

    }
    public void addToPaymentView(String numPushed)
    {

        denomSummaryView.updateCountLabel(numPushed);

    }
    public void subToPaymentView(String numPushed)
    {

        denomSummaryView.subCountLabel(numPushed);

    }
    public void addItemSelected(StackPane item, String priceString, String calString)
    {

        VBox itemInfoVBox;

        Label priceLabel = new SubLabel("Price: Php "+df.format(Double.parseDouble(priceString)));
        Label calLabel = new SubLabel("Calories: " + calString + " cal");

        itemInfoVBox = new VBox();
        itemInfoVBox.getChildren().addAll(priceLabel, calLabel);
        this.itemSelectedStackPane = item;
        this.itemSelectedStackPane.setPrefHeight(200);
        this.itemSelectedStackPane.setPrefWidth(200);
        removeChildAt(0, 0);
        removeChildAt(0, 1);
        this.dispensedSectionGridPane.add(this.itemSelectedStackPane, 0, 0, 1, 1);
        this.dispensedSectionGridPane.add(itemInfoVBox, 0, 1, 1, 1);
            

    }
    public void displayCurrentPayment()
    {
        this.denomLabel.setText("Current Payment");
        this.denomSummaryView.setManaged(true);
        this.denomSummaryView.setVisible(true);
        this.changeSummaryView.setManaged(false);
        this.changeSummaryView.setVisible(false);

    }
    public void displayCurrentChange(double total)
    {
        this.denomLabel.setText("Your Change! Php" + df.format(total));
        this.denomSummaryView.setManaged(false);
        this.denomSummaryView.setVisible(false);
        this.changeSummaryView.setManaged(true);
        this.changeSummaryView.setVisible(true);

    }
    public void displayChange(Money change, double total)
    {
        LinkedHashMap<String, ArrayList<DenominationItem>> denominationItems;
        LinkedHashMap<String, Integer> changeCount;
        int i;
        changeCount = new LinkedHashMap<String, Integer>();
        denominationItems = change.getDenominations();
        i = 0;
        for(Map.Entry<String, ArrayList<DenominationItem>> denomEntry : denominationItems.entrySet())
        {
            changeCount.put(denomEntry.getKey(), denomEntry.getValue().size());
            
        }
        
        changeSummaryView.changeDenomSummaryView(changeCount);
        displayCurrentChange(total);
        
    }
    public void performDispense()
    {
        ItemSlotPaneView itemSlotPaneView;
        if(this.itemSelectedStackPane instanceof ItemSlotPaneView)
        {
            itemSlotPaneView = ((ItemSlotPaneView)this.itemSelectedStackPane);
            PopUpBox popUpBox = new PopUpBox(itemSlotPaneView);
            removeChildAt(0, 0);
            removeChildAt(0, 1);
            popUpBox.showAndWait();
            this.itemSelectedStackPane = null;
            
        }
        

    }

    public void reset()
    {

        displayCurrentPayment();
        removeChildAt(0, 0);
        removeChildAt(0, 1);
        this.denomSummaryView.reset();
        this.changeSummaryView.reset();

    }
    public LinkedHashMap<String, Integer> getPaymentDenominationCount() {
        return this.denomSummaryView.getDenominationCount();
    }
    public LinkedHashMap<String, Integer> getChangeDenominationCount()
    {
        return this.changeSummaryView.getDenominationCount();
    }

    public int getSlotNumLabelOfSelected()
    {
        Label numLabel;
       if(this.itemSelectedStackPane instanceof ItemSlotPaneView)
       {
            numLabel = ((ItemSlotPaneView)this.itemSelectedStackPane).getSlotNumLabel();
            return Integer.parseInt(numLabel.getText());
       }
       return -1;
    }

     public void setProceedBtnAction(EventHandler<ActionEvent> eventHandler)
    {
        this.proceedOrderBtn.setOnAction(eventHandler);
    }
     public void setCancelBtnAction(EventHandler<ActionEvent> eventHandler)
    {
        this.cancelOrderBtn.setOnAction(eventHandler);
    }
    private void removeChildAt(int colTarget, int rowTarget)
    {
        Integer colIndex;
        Integer rowIndex;
        ObservableList<Node> children = this.dispensedSectionGridPane.getChildren();
        Iterator<Node> iter = children.iterator();
        Node selected;
        while (iter.hasNext()) {
            selected = iter.next();
            colIndex = GridPane.getColumnIndex(selected);
            rowIndex = GridPane.getRowIndex(selected);
            if(colIndex == null) 
                colIndex = 0;
            if(rowIndex == null) 
                rowIndex = 0;
    
            if ((colIndex == colTarget && rowIndex == rowTarget) )
            {
                iter.remove();
            }
        }
    }         
    private static DecimalFormat df = new DecimalFormat("0.00");
    private Label denomLabel;
    private GridPane dispensedSectionGridPane;

    private VBox mainCanvasVBox;
    private StackPane itemSelectedStackPane;
    private DenomSummaryView denomSummaryView;
    private DenomSummaryView changeSummaryView;
    private AlertBox alertBox;

    private Button proceedOrderBtn;
    private Button cancelOrderBtn;

    
}
