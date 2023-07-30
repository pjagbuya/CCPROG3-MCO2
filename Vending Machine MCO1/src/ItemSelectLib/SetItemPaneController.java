package ItemSelectLib;

import java.text.DecimalFormat;
import javafx.util.Duration;
import java.util.ArrayList;

import Boxes.AlertBox;
import Boxes.ItemNameBox;
import VMLib.VMachineModelPaneView;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SetItemPaneController {

    public SetItemPaneController(Stage parentWin, SetItemPaneView setItemPaneView, VMachineModelPaneView vMachineModelPaneView)
    {
        
        
        

        int[] itemNewInd;
        
        ChangeListener<String> textChangeListener;

        AlertBox alertBox = new AlertBox();
        ItemNameBox itemNameBox = new ItemNameBox();

        this.setItemPaneView = setItemPaneView;
        this.vMachineModelPaneView = vMachineModelPaneView;
        this.itemSectionPanes = this.setItemPaneView.getItemSectionGridPanes();
        this.parentWin = parentWin;
        this.addedItemLabel = new ArrayList<Label>();

        this.setItemPaneView.setActionEventCreateBtn(e->
        {
            itemNameBox.display("Name Setup", "Name of your item");

        });

        itemNameBox.setCalTxtFieldFilter(KeyEvent.KEY_TYPED, event->{
            if (!event.getCharacter().matches("\\d")) {  // "\\d" matches any digit, equivalent to [0-9]
            event.consume();  
            }
        });
        itemNameBox.addActionEventOkBtn(e->
        {
            if(itemNameBox.getNameTextField().getText().isEmpty() || 
             itemNameBox.getCalorieTextField().getText().isEmpty()
            )
                alertBox.display("ERROR", "Please enter a name and calories or close the window if you don't want to create");
            else
            {
                this.setItemPaneView.createNewItem(itemNameBox.getNameTextField().getText(), itemNameBox.getCalorieTextField().getText());
                setupListeners();
                itemNameBox.getNameTextField().setText("");
                itemNameBox.getCalorieTextField().setText("");
                itemNameBox.getWindow().close();
            }
            

        });
        setupListeners();

    }
    public void setupListeners()
    {
        DecimalFormat df = new DecimalFormat("0.00");
        

 
        
        for(int i = 0; i < this.itemSectionPanes.size(); i++)
        {

            TextField stockField;
            TextField priceField;
            ItemSectionPane itemSectionPane;
            Label itemNameLabel;
            Image image;
            int trackedIndex = i;


            itemSectionPane = itemSectionPanes.get(i);
            stockField = itemSectionPane.getStockField();
            priceField = itemSectionPane.getPriceField();
            itemNameLabel = itemSectionPane.getItemNameLabel();
            image = itemSectionPane.getImage();


            itemSectionPane.setActionEventAddBtn(e->
            {


                if(stockField.getText().length()==0 )
                {
                    stockField.setText("1");
                    if(priceField.getText().length()==0)
                    priceField.setText(df.format(DEFAULT_PRICE));
                    this.vMachineModelPaneView.addItemToView(image, itemNameLabel.getText());
                    addedItemLabel.add(itemNameLabel);
                }
                else
                {
                    stockField.setText(Integer.parseInt(stockField.getText())+1+"");
                    addedItemLabel.add(itemNameLabel);
                    this.vMachineModelPaneView.addItemToView(image, itemNameLabel.getText());
                }
                

            });


            itemSectionPane.setActionEventSubBtn( e->
            {
                int stock;
                try
                {
                    stock = Integer.parseInt(stockField.getText());
                }
                catch(NumberFormatException err)
                {
                    stock = -1;
                }

                if(stockField.getText().length()>0 && stock > 0)
                {
                    stockField.setText(stock-1 +"");
 
                    
                    if(stock-1 == 0)
                    {
                        priceField.setText("");
                        this.vMachineModelPaneView.removeItemToView(itemNameLabel.getText());
                        this.vMachineModelPaneView.rearrangeItems();
                        if(addedItemLabel.contains(itemNameLabel))
                            addedItemLabel.remove(itemNameLabel);
                    }

                }


                

            });
        
            
            itemSectionPane.setPriceTxtFieldListener((observable, oldValue, newValue) -> {

                double doubleNum;

                if (newValue.matches("\\d*(\\.\\d*)?")) {

                    try{
                        doubleNum = Double.parseDouble(priceField.getText());
                    } 
                    catch(NumberFormatException e)
                    {
                        doubleNum = -1;
                    }
    
                    if(doubleNum > 0 && priceField.getText().length() != 0 && 
                        (stockField.getText().length() != 0) &&
                        (!priceField.getText().equalsIgnoreCase("0") &&
                        (!stockField.getText().equalsIgnoreCase("0") ))) 
                    {
                        

                        this.vMachineModelPaneView.addItemToView(image, itemNameLabel.getText());
                        addedItemLabel.add(itemNameLabel);
                    }
                    else if((doubleNum == 0 || priceField.getText().length() == 0) &&
                                (stockField.getText().length() == 0))
                    {
                        this.vMachineModelPaneView.removeItemToView(itemNameLabel.getText());
                        this.vMachineModelPaneView.rearrangeItems();
                        if(addedItemLabel.contains(itemNameLabel))
                            addedItemLabel.remove(itemNameLabel);
                    }
                    
                }
                else
                {
                    priceField.setText(oldValue);
                }


            });

            itemSectionPane.setStockTxtFieldListener((observable, oldValue, newValue) -> {

                double doubleNum;

                if (newValue.matches("\\d*")) {
                    try{
                        doubleNum = Double.parseDouble(priceField.getText());
                    } 
                    catch(NumberFormatException e)
                    {
                        doubleNum = -1;
                    }
                    
    
                    if(doubleNum > 0 && priceField.getText().length() != 0 && 
                        (stockField.getText().length() != 0))
                    {
                        
                        // priceFields[trackedIndex].setText(df.format(doubleNum));
                        this.vMachineModelPaneView.addItemToView(image, itemNameLabel.getText());
                        addedItemLabel.add(itemNameLabel);
                    }
                    else if((doubleNum <= 0 || priceField.getText().length() == 0) &&
                                (stockField.getText().length() == 0))
                    {
                        this.vMachineModelPaneView.removeItemToView(itemNameLabel.getText());
                        this.vMachineModelPaneView.rearrangeItems();
                        if(addedItemLabel.contains(itemNameLabel))
                            addedItemLabel.remove(itemNameLabel);
                    }
                    
                }    


            });


            // Prevent other characters int the stock field
            itemSectionPane.addStockTxtFieldsEventFilter( KeyEvent.KEY_TYPED, event-> {
                if(!event.getCharacter().matches("\\d*"))
                {
                    event.consume();
                }
            });


        }
    }
    public void resetForm() 
    {   TextField stockField;
        TextField priceField;
        ItemSectionPane itemSectionPane;
        Label itemNameLabel;
        this.setItemPaneView.removeAddedItems();
        for(int i = 0; i < this.setItemPaneView.getMaxItems(); i++)
        {
            itemSectionPane = itemSectionPanes.get(i);
            stockField = itemSectionPane.getStockField();
            priceField = itemSectionPane.getPriceField();
            itemNameLabel = itemSectionPane.getItemNameLabel();
            stockField.setText("");
            priceField.setText("");
            this.vMachineModelPaneView.removeItemToView(itemNameLabel.getText());
        }


    }
    private ArrayList<Label> addedItemLabel;
    private static final double DEFAULT_PRICE = 20.00;
    private ArrayList<ItemSectionPane> itemSectionPanes;    
    private Stage parentWin;
    private final SetItemPaneView setItemPaneView;
    private final VMachineModelPaneView vMachineModelPaneView;
}   
