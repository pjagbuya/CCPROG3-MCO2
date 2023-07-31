package VMLib;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import Labels.LabelToField;
//import javafx.embed.swing.SwingFXUtils;
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

public class VMachineModelPaneView extends ScrollPane{
    public VMachineModelPaneView(Stage parentWin)
    {
        
        String colorBg = "#071952";
        ColumnConstraints columnConstr = new ColumnConstraints();
        RowConstraints rowConstr = new RowConstraints();

        itemNameLabels = new Label[MAX_ITEMS];
        itemContainerStackPane = new ArrayList<StackPane>();

        mainCanvasStackPane = new StackPane();
        vendingMachineGridPane = new GridPane();

        possibleItems = new LinkedHashMap<String, String>();
		possibleItems.put("CHEESE", "/Pics/cheese.png");
		possibleItems.put("COCOA", "/Pics/cocoa-bean.png");
		possibleItems.put("CREAM", "/Pics/cream.png");
		possibleItems.put("EGG", "/Pics/egg.png");
		possibleItems.put("KANGKONG", "/Pics/kangkong.png");
		possibleItems.put("MILK", "/Pics/milk.png");
		possibleItems.put("SALT", "/Pics/salt.png");
		possibleItems.put("SUGAR", "/Pics/sugar.png");
		possibleItems.put("CORNSTARCH", "/Pics/cornstarch.png");
		possibleItems.put("TOFU", "/Pics/tofu.png");
		possibleItems.put("CHICKEN", "/Pics/chicken-leg.png");
		possibleItems.put("BBQ", "/Pics/bbq.png");
		possibleItems.put("FLOUR", "/Pics/flour.png");
        possibleItems.put("SOY SAUCE", "/Pics/soy-sauce.png");
        possibleItems.put("CHILI", "/Pics/chili.png");
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
        columnConstr.setMaxWidth(300);
        
        vendingMachineGridPane.getColumnConstraints().add(columnConstr);
        vendingMachineGridPane.getRowConstraints().add(rowConstr);

        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setPadding(new Insets(5));
        this.prefWidthProperty().bind(parentWin.widthProperty().multiply(0.6));
        this.setContent(mainCanvasStackPane);

        this.setStyle("-fx-background-color: "+colorBg+";");
    }

    public void addItemToView(Image itemImage, String label)
    {

        int indPanes;
        StackPane newStackPane;
        indPanes = itemContainerStackPane.size();

        
        if(!containsLabel(label))
        {

            newStackPane = new ItemSlotPaneView(itemImage, label, indPanes, this);   
            itemContainerStackPane.add(newStackPane);
            
            if(itemContainerStackPane.isEmpty())
            {
                itemNameLabels[indPanes] = ((ItemSlotPaneView) newStackPane).getSlotItemNameLabel();

            }
            else
            {
                itemNameLabels[indPanes] =  ((ItemSlotPaneView) newStackPane).getSlotItemNameLabel();
            }

            
            vendingMachineGridPane.add(itemContainerStackPane.get(indPanes), indPanes%3
                                                                           , indPanes/3);
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

            if(itemContainerStackPane.isEmpty())
            {
                itemNameLabels[indPanes] = ((ItemSlotPaneView) newStackPane).getSlotItemNameLabel();

            }
            else
            {
                itemNameLabels[indPanes] =  ((ItemSlotPaneView) newStackPane).getSlotItemNameLabel();
            }

            
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
                vendingMachineGridPane.getChildren().remove(itemContainer);

            }
        }
        
        if(getItemContainer(label) != null)
        {
            itemContainerStackPane.remove(getItemContainer(label));

        }
            
        rearrangeItems();

    }



    public void rearrangeItems() 
    {

        int i = 0;
        vendingMachineGridPane.getChildren().clear();
        for(StackPane itemSlot : itemContainerStackPane)
        {
            
            ((ItemSlotPaneView)itemSlot).setSlotNumLabel(i+1);
            vendingMachineGridPane.add(itemSlot, i%3
                                               , i/3);


            i++;

        }


        
    }


    public StackPane getItemContainer(String label)
    {
        rearrangeItems();

        for(StackPane itemSlot : itemContainerStackPane)
        {
            if(((ItemSlotPaneView)itemSlot).getSlotItemNameLabel().getText().equalsIgnoreCase(label))
                return itemSlot;

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



    private LinkedHashMap<String, String> possibleItems;
    private Label[] itemNameLabels;
    private ArrayList<StackPane> itemContainerStackPane;
    private GridPane vendingMachineGridPane;
    private StackPane mainCanvasStackPane;

    private static final int MAX_ITEMS = 100;

    

}
