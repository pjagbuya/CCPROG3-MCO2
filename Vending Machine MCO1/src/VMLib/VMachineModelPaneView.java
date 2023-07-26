package VMLib;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import Labels.LabelToField;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
        // Code below forms together one gradient color
        LinearGradient linearGradient1 = new LinearGradient(
            0, 0, 1, 1,
            true, 
            CycleMethod.NO_CYCLE, 
   
            new Stop(0.1, Color.rgb(255, 255, 255, 0.15)),
            new Stop(1.0, Color.rgb(0, 0, 0, 0.25))
        );

        LinearGradient linearGradient2 = new LinearGradient(
            0, 0, 1, 1,
            true, 
            CycleMethod.NO_CYCLE, 

            new Stop(0, Color.rgb(255, 255, 255, 0)),
            new Stop(0.5, Color.rgb(255, 255, 255, 0.1)),
            new Stop(0.5, Color.rgb(255, 255, 255, 0)),
            new Stop(1, Color.rgb(255, 255, 255, 0))
        );

        bgFill1 = new BackgroundFill(
            linearGradient1,
            CornerRadii.EMPTY,
            Insets.EMPTY
        );

        bgFill2 = new BackgroundFill(
            linearGradient2,
            CornerRadii.EMPTY,
            Insets.EMPTY
        );
        itemCoverBg = new Background(bgFill1, bgFill2);
        itemNameLabels = new Label[MAX_ITEMS];
        itemContainerStackPane = new StackPane[MAX_ITEMS];
        currentImageViews = new ImageView[(int)Math.ceil(MAX_ITEMS/3.0)][3];
        stringNameImageViews = new String[(int)Math.ceil(MAX_ITEMS/3.0)][3];

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


        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setPadding(new Insets(5));
        this.setId("denomPane");
        this.prefWidthProperty().bind(parentWin.widthProperty().multiply(0.6));
        this.setContent(mainCanvasStackPane);

        this.setStyle("-fx-background-color: "+colorBg+";");
    }

    public void addItemToView(Image itemImage, String label, int indOfLabel)
    {


        boolean isDuplicate;
        int nextRow = -1;
        int nextColumn = -1;
        int indPanes;
        Region itemBox;
        Region itemCover;
        Region labelRegion;
        String itemBoxColor = "#00002F";
        String colorLightest = "#97FEED";
        Label slotNumLabel;
        Label slotItemNameLabel;
        Font standardBoldLabel = Font.font("Arial", FontWeight.BOLD, 20);
        

        

        indPanes = 0;
        isDuplicate =false;
        if(containsLabel(label))
        {

            isDuplicate = true;
            
        }   
        // Find the next available cell in the GridPane
        for (int i = 0; i < (int)Math.ceil(MAX_ITEMS/3.0); i++) {
            
            for (int j = 0; j < 3; indPanes++,j++) {
                if (indPanes < MAX_ITEMS && itemContainerStackPane[indPanes] == null) 
                {
                    
                    nextRow = i;
                    nextColumn = j; 

                    break;
                }
            }
            if (nextRow != -1 || nextColumn != -1 || isDuplicate) {
                break;
            }
        }
        if((nextRow != -1 && nextColumn != -1))
        {
            
        }


        // If there is an available cell, add the image to it
        if ((nextRow != -1 && nextColumn != -1) && !isDuplicate) 
        {

            slotItemNameLabel = new LabelToField(label);

            
            itemBox = new Region();
            itemBox.setPrefSize(ITEM_BOX_WIDTH, ITEM_BOX_HEIGHT);
            itemBox.setStyle("-fx-background-color: "+itemBoxColor+";");
            
            // Uses the previosly set Linear gradients above
            itemCoverBg = new Background(bgFill1, bgFill2);
            itemCover = new Region();
            itemCover.setPrefSize(ITEM_BOX_WIDTH-20, ITEM_BOX_HEIGHT-30);
            itemCover.setBackground(itemCoverBg);

            labelRegion = new Region();
            labelRegion.setPrefSize(50, 30);
            labelRegion.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            labelRegion.setStyle("-fx-background-color: black;"
                                +"-fx-border-width: 2 2 0 2;"
                                +"-fx-border-color:"+colorLightest+";");

            slotNumLabel = new Label(indPanes+1 + "");
            slotNumLabel.setPadding(new Insets(0, 0, 3, 0));
            slotNumLabel.setFont(standardBoldLabel);

            // Save the mapped label
            stringNameImageViews[nextRow][nextColumn] = label;

            itemCover.setBackground(itemCoverBg);
            itemContainerStackPane[indPanes] = new StackPane();
            currentImageViews[nextRow][nextColumn] = new ImageView(itemImage);
            currentImageViews[nextRow][nextColumn].setFitHeight(300);
            currentImageViews[nextRow][nextColumn].setFitWidth(300);
            currentImageViews[nextRow][nextColumn].setPreserveRatio(true);
            currentImageViews[nextRow][nextColumn].setSmooth(true);
            currentImageViews[nextRow][nextColumn].fitHeightProperty().bind(this.heightProperty().multiply(0.3));
            currentImageViews[nextRow][nextColumn].fitWidthProperty().bind(this.widthProperty().multiply(0.3));

            
            itemNameLabels[indPanes] = slotItemNameLabel;
            itemContainerStackPane[indPanes].getChildren().addAll(itemBox, currentImageViews[nextRow][nextColumn], itemNameLabels[indPanes], itemCover, labelRegion, slotNumLabel);
            
            StackPane.setAlignment(itemNameLabels[indPanes], Pos.TOP_CENTER);
            StackPane.setAlignment(labelRegion, Pos.BOTTOM_CENTER);
            StackPane.setAlignment(slotNumLabel, Pos.BOTTOM_CENTER);
            vendingMachineGridPane.add(itemContainerStackPane[indPanes], nextColumn, nextRow);
        }
    }

    public void removeItemToView(String label)
    {
        rearrangeItems();
        for(int i = 0;  i < MAX_ITEMS/3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(stringNameImageViews[i][j]!= null && stringNameImageViews[i][j].equalsIgnoreCase(label))
                {
                    // Name of image on map
                    stringNameImageViews[i][j] = null;

                    
                    // Container where all items reside
                    itemContainerStackPane[i*3+j].getChildren().clear();
                    itemContainerStackPane[i* 3 + j] = null;

                    // Image associated with the item
                    currentImageViews[i][j] = null;
                }

            }

        }


    }


    public void rearrangeItems() 
    {


        String itemBoxColor = "#00002F";
        String colorLightest = "#97FEED";
        
        StackPane newStackPane;

        Region itemBox;
        Region itemCover;
        Region labelRegion;
        
        Label slotNumLabel;
        
        Font standardBoldLabel = Font.font("Arial", FontWeight.BOLD, 20);      
        
        
        // two for loops to select the current position on 2D array
        for (int i = 0; i < MAX_ITEMS / 3; i++) {
            for (int j = 0; j < 3; j++) {

                // When the chosen position in 2D array is empty
                if (currentImageViews[i][j] == null) {
                    // Get to the next non empty via row major
                    for (int k = i; k < MAX_ITEMS / 3; k++) {
                        for (int l = (k == i ? j + 1 : 0); l < 3; l++) {


                            // Signifies we found a non-empty slot
                            if (currentImageViews[k][l] != null) {


                                itemNameLabels[i * 3 + j] = itemNameLabels[k * 3 + l];

                                // box bacground
                                itemBox = new Region();
                                itemBox.setPrefSize(ITEM_BOX_WIDTH, ITEM_BOX_WIDTH);
                                itemBox.setStyle("-fx-background-color: "+itemBoxColor+";");

                                // Set up graphics of box
                                itemCover = new Region();
                                itemCover.setPrefSize(ITEM_BOX_WIDTH-20, ITEM_BOX_HEIGHT-30);
                                itemCover.setBackground(itemCoverBg);

                                // Set up proper labeling
                                labelRegion = new Region();
                                labelRegion.setPrefSize(50, 30);
                                labelRegion.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                                labelRegion.setStyle("-fx-background-color: black;"
                                                    +"-fx-border-width: 2 2 0 2;"
                                                    +"-fx-border-color:"+colorLightest+";");

                                slotNumLabel = new Label(i*3+j+1 + "");
                                slotNumLabel.setPadding(new Insets(0, 0, 3, 0));
                                slotNumLabel.setFont(standardBoldLabel);

                                StackPane.setAlignment(itemNameLabels[i * 3 + j], Pos.TOP_CENTER);
                                StackPane.setAlignment(labelRegion, Pos.BOTTOM_CENTER);
                                StackPane.setAlignment(slotNumLabel, Pos.BOTTOM_CENTER);



                                // Move the content of the next non-empty slot to the current slot
                                currentImageViews[i][j] = currentImageViews[k][l];
                                stringNameImageViews[i][j] = stringNameImageViews[k][l];
    
                                // Create a new StackPane for the current slot
                                newStackPane = new StackPane();
                                newStackPane.getChildren().addAll(itemBox, currentImageViews[k][l], itemNameLabels[i * 3 + j], itemCover, labelRegion, slotNumLabel);
                                itemContainerStackPane[i * 3 + j] = newStackPane;
    
                                currentImageViews[k][l] = null;
                                stringNameImageViews[k][l] = null;
    
                                // Remove some of the targeted non-empty positions and clear them
                                if (itemContainerStackPane[k * 3 + l] != null) {
                                    vendingMachineGridPane.getChildren().remove(itemContainerStackPane[k * 3 + l]);
                                    itemContainerStackPane[k * 3 + l].getChildren().clear();
                                    itemContainerStackPane[k * 3 + l] = null;
                                }
                                
                                // Add the items 
                                vendingMachineGridPane.add(itemContainerStackPane[i * 3 + j], j, i);
    
                                // Exit the loop as the next non-empty slot has been found
                                k = MAX_ITEMS / 3;  // ends 'k' loop
                                break;              // ends this 'l' loop
                            }
                        }
                    }
                }
            }
        }



        
    }


    public StackPane getItemContainer(String label)
    {
        rearrangeItems();
        for(int i = 0;  i < MAX_ITEMS/3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(stringNameImageViews[i][j]!= null && stringNameImageViews[i][j].equalsIgnoreCase(label))
                {
                   return itemContainerStackPane[i * 3 + j];
                }

            }

        }

        return null;

    }
    private boolean containsLabel(String label)
    {
        for(int i = 0;  i < MAX_ITEMS/3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(stringNameImageViews[i][j]!= null && stringNameImageViews[i][j].equalsIgnoreCase(label))
                    return true;

            }

        }

        return false;
    }



    private LinkedHashMap<String, String> possibleItems;
    private ImageView[][] currentImageViews;
    private Label[] itemNameLabels;
    private String[][] stringNameImageViews;
    private StackPane[] itemContainerStackPane;
    private GridPane vendingMachineGridPane;
    private StackPane mainCanvasStackPane;
    private BackgroundFill bgFill1, bgFill2;
    private Background itemCoverBg;
    private static final int MAX_ITEMS = 100;
    private static final int ITEM_BOX_HEIGHT = 250;
    private static final int ITEM_BOX_WIDTH = 200;
    

}
