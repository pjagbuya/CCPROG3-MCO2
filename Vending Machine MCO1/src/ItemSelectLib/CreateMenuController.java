package ItemSelectLib;

import java.text.DecimalFormat;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import Boxes.AlertBox;
import Boxes.ItemNameBox;
import DenomLib.DenomSetSection;
import DenomLib.SetDenomPaneController;
import DenomLib.SetDenomPaneView;
import StartLib.AppController;
import StartLib.AppModel;
import StartLib.AppView;
import StartLib.CreateRegTopBarController;
import StartLib.CreateRegTopBarView;
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

public class CreateMenuController {

    public CreateMenuController(AppController appController, 
                                SetItemPaneView setItemPaneView, 
                                VMachineModelPaneView vMachineModelPaneView,
                                SetDenomPaneView setDenomPaneView,
                                CreateRegTopBarView createRegTopBarView)
    {
        

        ItemNameBox itemNameBox = new ItemNameBox();
        SetDenomPaneController setDenomPaneController = new SetDenomPaneController(setDenomPaneView);

        CreateRegTopBarController createRegTopBarController = new CreateRegTopBarController(appController, setDenomPaneController,vMachineModelPaneView, this, setItemPaneView);
        
        // The collection of general controllers and scenes
        this.appModel = appController.getAppModel();
        this.appView = appController.getAppView();

        // The seperated view of the Create Reg Menu
        this.createRegTopBarView = createRegTopBarView;
        this.setItemPaneView = setItemPaneView;
        this.vMachineModelPaneView = vMachineModelPaneView;
        this.setDenomPaneView = setDenomPaneView;

        // Item Section Panes
        this.itemSectionPanes = this.setItemPaneView.getItemSectionGridPanes();
        this.denomSetSections = setDenomPaneView.getDenomSetSections();
        this.addedItemLabel = new ArrayList<Label>();
        this.alertBox = new AlertBox();
        
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
            String msg;

            if(itemNameBox.getNameTextField().getText().isEmpty() || 
             itemNameBox.getCalorieTextField().getText().isEmpty()
            )
                alertBox.display("ERROR", "Please enter a name and calories or close the window if you don't want to create");
            else
            {
                msg = this.appModel.createNewItem(itemNameBox.getNameTextField().getText(), 
                                                  Integer.parseInt(itemNameBox.getCalorieTextField().getText()));       

                if(msg == null || msg.isEmpty())
                {
                    this.setItemPaneView.createNewItem(itemNameBox.getNameTextField().getText(), 
                                                       itemNameBox.getCalorieTextField().getText());
                
                    setupSetItemSectionListeners();
                }
                else
                {
                    alertBox.display("ERROR", msg);
                }

                itemNameBox.getNameTextField().setText("");
                itemNameBox.getCalorieTextField().setText("");
                itemNameBox.getWindow().close();
                


            }
            

        });
        setupSetItemSectionListeners();

    }
    public void setupSetItemSectionListeners()
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
            
            AlertBox alertBox = new AlertBox();


            itemSectionPane = itemSectionPanes.get(i);
            stockField = itemSectionPane.getStockField();
            priceField = itemSectionPane.getPriceField();
            itemNameLabel = itemSectionPane.getItemNameLabel();
            image = itemSectionPane.getImage();


            itemSectionPane.setActionEventAddBtn(e->
            {
                String msg;
                double finalPrice = itemSectionPane.getFinalPrice();
                int maxCap = this.appModel.getCurrItemCap();

                if(priceField.getText().length()==0)
                {
                    priceField.setText(df.format(finalPrice));
                }

                if(stockField.getText().length()==0 )
                {
                    stockField.setText("1");
                    

                    this.vMachineModelPaneView.addItemToView(image, itemNameLabel.getText());
                    addedItemLabel.add(itemNameLabel);


                }
                else
                {
                    if(Integer.parseInt(stockField.getText())+1 <= maxCap)
                    {
                        stockField.setText(Integer.parseInt(stockField.getText())+1+"");
                        addedItemLabel.add(itemNameLabel);
                        this.vMachineModelPaneView.addItemToView(image, itemNameLabel.getText());
                    }
                    else
                    {
                        stockField.setText(maxCap + "");
                        alertBox.display("ERROR", "Your at full item capacity, your given item cap: " + maxCap);
                    }


                      
                }

            });


            itemSectionPane.setActionEventSubBtn( e->
            {
                int stock;
                double finalPrice = itemSectionPane.getFinalPrice();
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
                        if(!priceField.getText().isEmpty())
                        {
                            itemSectionPane.setFinalPrice(finalPrice);

                        }
                            
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
                double finalPrice = itemSectionPane.getFinalPrice();
                int maxCap = this.appModel.getCurrItemCap();
                if (newValue.matches("\\d*(\\.\\d*)?")) {

                    try{
                        doubleNum = Double.parseDouble(priceField.getText());
                    } 
                    catch(NumberFormatException e)
                    {
                        doubleNum = -1;
                    }

                    if(!oldValue.equalsIgnoreCase(newValue) && newValue.length() != 0)
                    {
                        itemSectionPane.setFinalPrice(Double.parseDouble(newValue));

                    }
                    if(doubleNum > 0 && priceField.getText().length() != 0 && 
                        (stockField.getText().length() != 0) &&
                        (!priceField.getText().equalsIgnoreCase("0") &&
                        (!stockField.getText().equalsIgnoreCase("0") ))) 
                    {
                        
                    if(Integer.parseInt(stockField.getText()) <= maxCap)
                    {
                        addedItemLabel.add(itemNameLabel);
                        this.vMachineModelPaneView.addItemToView(image, itemNameLabel.getText());
                    }
                    else
                    {
                        stockField.setText(maxCap + "");
                        alertBox.display("ERROR", "Your at full item capacity, your given item cap: " + maxCap);
                    }
                        
 
                    }
                    else if((doubleNum == 0 || 
                            (stockField.getText().length() == 0) || 
                            stockField.getText().equalsIgnoreCase("0")))
                    {
                        this.vMachineModelPaneView.removeItemToView(itemNameLabel.getText());
                        this.vMachineModelPaneView.rearrangeItems();
                        if(addedItemLabel.contains(itemNameLabel))
                            addedItemLabel.remove(itemNameLabel);
                    }
                    
                }
                else
                {
                    priceField.setText("");
                }


            });

            itemSectionPane.setStockTxtFieldListener((observable, oldValue, newValue) -> {

                double doubleNum;
                double finalPrice = itemSectionPane.getFinalPrice();
                int maxCap = this.appModel.getCurrItemCap();
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

                            
                        if(Integer.parseInt(newValue) <= maxCap)
                        {
                            addedItemLabel.add(itemNameLabel);
                            this.vMachineModelPaneView.addItemToView(image, itemNameLabel.getText());
                        }
                        else
                        {
                            stockField.setText(maxCap + "");
                            alertBox.display("ERROR", "Your at full item capacity, your given item cap: " + maxCap);
                        }

                    }
                    else if((doubleNum <= 0 || 
                            (stockField.getText().length() == 0) ||
                            stockField.getText().equalsIgnoreCase("0")))
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
        
        this.setItemPaneView.resetToDefault();
        this.vMachineModelPaneView.setUpVendMachView(null, null);


    }
    private CreateRegTopBarView createRegTopBarView;
    private ArrayList<Label> addedItemLabel;
    private ArrayList<ItemSectionPane> itemSectionPanes;   
    private DenomSetSection[] denomSetSections;
    private AlertBox alertBox;
    private AppView appView;
    private AppModel appModel;

    private final SetDenomPaneView setDenomPaneView;
    private final SetItemPaneView setItemPaneView;
    private final VMachineModelPaneView vMachineModelPaneView;
}   
