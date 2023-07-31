package VMLib;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import ItemSelectLib.ItemSectionPane;
import ItemSelectLib.PresetItem;
import Labels.LabelToField;
import Models.VM_Regular;
import Models.VM_Slot;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VMachineModelPaneView extends ScrollPane
{
    public VMachineModelPaneView(Stage parentWin, int maxSlots)
    {
        
        String colorBg = "#071952";
        ColumnConstraints columnConstr = new ColumnConstraints();
        RowConstraints rowConstr = new RowConstraints();
        this.occupiedNum = 0;
        this.maxSlots = maxSlots;

        itemNameLabels = new ArrayList<Label>();
        itemContainerStackPane = new ArrayList<StackPane>();
        imageOrder = new ArrayList<Image>();
        mainCanvasStackPane = new StackPane();
        vendingMachineGridPane = new GridPane();


        vendingMachineGridPane.setPrefSize(ScrollPane.USE_COMPUTED_SIZE, ScrollPane.USE_COMPUTED_SIZE);
        vendingMachineGridPane.setMaxSize(ScrollPane.USE_COMPUTED_SIZE, ScrollPane.USE_COMPUTED_SIZE);
        vendingMachineGridPane.setPadding(new Insets(20, 10, 5, 10));
        
        vendingMachineGridPane.setAlignment(Pos.CENTER);
        vendingMachineGridPane.setHgap(30);
        vendingMachineGridPane.setVgap(20);
        mainCanvasStackPane.getChildren().addAll(vendingMachineGridPane);
        mainCanvasStackPane.setAlignment(Pos.CENTER);
        mainCanvasStackPane.prefWidthProperty().bind(this.widthProperty());


        rowConstr.setMaxHeight(300);
        columnConstr.setMaxWidth(200);
        
        vendingMachineGridPane.getColumnConstraints().add(columnConstr);
        vendingMachineGridPane.getRowConstraints().add(rowConstr);

        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setPadding(new Insets(5));
        this.prefWidthProperty().bind(parentWin.widthProperty().multiply(0.5));
        
        this.setContent(mainCanvasStackPane);

        this.setStyle("-fx-background-color: "+colorBg+";");
    }
    public VMachineModelPaneView(Stage parentWin)
    {
        this(parentWin, 8);
        
 
    }
    public void addItemToView(Image itemImage, String label)
    {

        int indPanes;
        StackPane newStackPane;
        indPanes = itemContainerStackPane.size();

        
        if(!containsLabel(label))
        {
            if(itemImage != null && !label.isEmpty())
            {
                newStackPane = new ItemSlotPaneView(itemImage, label, indPanes, this);   
                
                occupiedNum++;
                itemContainerStackPane.add(newStackPane);
                
                itemNameLabels.add(((ItemSlotPaneView) newStackPane).getSlotItemNameLabel());
                imageOrder.add(((ItemSlotPaneView)newStackPane).getItemImage());
                vendingMachineGridPane.add(itemContainerStackPane.get(indPanes), indPanes%3
                                                                            , indPanes/3);
            }


        }


    }

    public void addItemWithComp(String label, Node node, Image itemImage)
    {
        int indPanes;
        StackPane newStackPane;
        VBox parentPane;
        indPanes = itemContainerStackPane.size();

        
        if(!containsLabel(label))
        {

            newStackPane = new ItemSlotPaneView(itemImage, label, indPanes, this);   
            itemContainerStackPane.add(newStackPane);
            parentPane = ((ItemSlotPaneView)newStackPane).addComponentUnder(node);

  
            itemNameLabels.add(((ItemSlotPaneView) newStackPane).getSlotItemNameLabel());


            
            vendingMachineGridPane.add(parentPane, indPanes%3
                                                 , indPanes/3);

            

        }
    }

    public void removeItemToView(String label)
    {

        for(StackPane itemContainer : itemContainerStackPane)
        {
             if(((ItemSlotPaneView)itemContainer).getNameOfItem().equalsIgnoreCase(label))
            {
                itemNameLabels.remove( ((ItemSlotPaneView)itemContainer).getSlotItemNameLabel() );

                vendingMachineGridPane.getChildren().remove(itemContainer);
                
            }
        }
        
        if(getItemContainer(label) != null)
        {
            occupiedNum--;
            itemContainerStackPane.remove(getItemContainer(label));
            

        }
            
        rearrangeItems();
        setUpEmptyContainers();
    }



    public void rearrangeItems() 
    {

        int i = 0;
        vendingMachineGridPane.getChildren().clear();
        itemNameLabels.clear();
        imageOrder.clear();
        for(StackPane itemSlot : itemContainerStackPane)
        {
            
            ((ItemSlotPaneView)itemSlot).setSlotNumLabel(i+1);
            imageOrder.add(((ItemSlotPaneView)itemSlot).getItemImage());
            itemNameLabels.add( ((ItemSlotPaneView)itemSlot).getSlotItemNameLabel() );
            vendingMachineGridPane.add(itemSlot, i%3
                                               , i/3);


            i++;

        }
        


        
    }

    public void setUpVendMachView(ArrayList<Image> imageOrder, 
                                  ArrayList<String> itemNames)
    {

        
        for(int i = 0; i < maxSlots; i++)
        {
            
            try
            {
                System.out.println("Image size: " +  imageOrder.size());
                System.out.println("Size: " +  itemNames.get(i));
                addItemToView(imageOrder.get(i), itemNames.get(i));
                
            }
            catch(IndexOutOfBoundsException e )
            {
                break;
                
            }
            catch(NullPointerException e2)
            {
                break;
            }

            
        }
        setUpEmptyContainers();


    }
    public void setUpEmptyContainers()
    {
        StackPane newStackPane;
        for(int i = 0; i < maxSlots; i++)
        {
            if(i>= occupiedNum)
            {

                newStackPane = new ItemSlotPaneView(i, this);  
                addItemToView(null, "");
                vendingMachineGridPane.add(newStackPane, i%3
                                                       , i/3);
            }
            
        }
        
    }
    public void setMaxSlotVMView(int max)
    {
        this.maxSlots = max;

    }
    public void removeEmptyContainers()
    {
        StackPane newStackPane;
        for(int i = 0; i < maxSlots; i++)
        {
            if(i>= occupiedNum)
            {
                newStackPane = new ItemSlotPaneView(i+1, this);  
                addItemToView(null, "");
                vendingMachineGridPane.add(newStackPane, i%3
                                                       , i/3);
            }
            
        }
        
    }
    public StackPane getItemContainer(String label)
    {

        int i;
        i = 0;
        for(StackPane itemSlot : itemContainerStackPane)
        {
            if(((ItemSlotPaneView)itemSlot).getSlotItemNameLabel().getText().equalsIgnoreCase(label))
                return new ItemSlotPaneView(((ItemSlotPaneView)itemSlot).getItemImage(), 
                                            ((ItemSlotPaneView)itemSlot).getSlotItemNameLabel().getText(), 
                                            i+1, this);

            i++;

        }

        return null;

    }

    private boolean containsLabel(String label)
    {
        for(StackPane itemSlot : itemContainerStackPane)
        {
            if(((ItemSlotPaneView)itemSlot).getSlotItemNameLabel().getText().equalsIgnoreCase(label))
                return true;

        }

        return false;
    }

    public ArrayList<Label> getItemNameLabels() 
    {
        return itemNameLabels;
    }
    public ArrayList<Image> getImageOrder() {
        return imageOrder;
    }
    public void setMaxSlots(int maxSlots) {
        this.maxSlots = maxSlots;
    }
    private int maxSlots;
    private int occupiedNum;
    private ArrayList<Image> imageOrder;
    private ArrayList<Label> itemNameLabels;
    private ArrayList<StackPane> itemContainerStackPane;
    private ArrayList<StackPane> emptyContainers;
    private GridPane vendingMachineGridPane;
    private StackPane mainCanvasStackPane;


    

}
