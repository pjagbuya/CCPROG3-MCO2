package VMLib;

import CustomSetup.CustomImageView;
import Labels.LabelToField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ItemSlotPaneView extends StackPane 
{
    public ItemSlotPaneView(Image itemImage, 
                            String name, 
                            int indPane, 
                            VMachineModelPaneView bindedPane)
    {
        this.hiddenPane = new VBox();
        this.bindedPane = bindedPane;
        this.itemImage = itemImage;
        this.nameOfItem = name;
        this.slotItemNameLabel = new LabelToField(name);
        this.slotItemNameLabel.setWrapText(true);
        this.slotItemNameLabel.setMaxWidth(150);
        this.slotItemNameLabel.setAlignment(Pos.CENTER);
        this.slotNumLabel = new Label(indPane+1 + "");
        this.slotNumLabel.setPadding(new Insets(0, 0, 3, 0));
        this.currentImageView = new CustomImageView(itemImage, bindedPane, 0.3);


        setDesign(true);
        this.hiddenPane.getChildren().add(this);

    }
    public ItemSlotPaneView(int indPane, VMachineModelPaneView bindedPane)
    {

        this.bindedPane = bindedPane;
        this.nameOfItem = "";
        this.slotItemNameLabel = new LabelToField("");
        this.slotItemNameLabel.setWrapText(true);
        this.slotItemNameLabel.setMaxWidth(150);
        this.slotItemNameLabel.setAlignment(Pos.CENTER);
        this.slotNumLabel = new Label(indPane+1 + "");
        this.slotNumLabel.setPadding(new Insets(0, 0, 3, 0));
        this.currentImageView = null;
        setDesign(false);

    }

    public ImageView getCurrentImageView() 
    {
        return currentImageView;
    }
    public static int getItemBoxHeight() 
    {
        return ITEM_BOX_HEIGHT;
    }
    public static int getItemBoxWidth() 
    {
        return ITEM_BOX_WIDTH;
    }
    public String getNameOfItem() 
    {
        return nameOfItem;
    }
    public Label getSlotItemNameLabel() 
    {
        return slotItemNameLabel;
    }
    public Label getSlotNumLabel() 
    {
        return slotNumLabel;
    }
    public VBox getHiddenPane() {
        return hiddenPane;
    }
    public Image getItemImage() {
        return itemImage;
    }
    public void setCurrentImageView(ImageView currentImageView) 
    {
        this.currentImageView = currentImageView;
    }
    public void setNameOfItem(String nameOfItem) 
    {
        this.nameOfItem = nameOfItem;
    }
    public void setSlotItemNameLabel(String name)
    {
        this.slotItemNameLabel.setText(name);
    }

    public void setSlotNumLabel(int slotNum) 
    {
        this.slotNumLabel.setText(slotNum+"");
    }
    public VBox addComponentUnder(Node node)
    {
        this.hiddenPane.getChildren().add(node);

        return this.hiddenPane;
    }

    private void setDesign(boolean isItem)
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

        BackgroundFill bgFill1 = new BackgroundFill(
            linearGradient1,
            CornerRadii.EMPTY,
            Insets.EMPTY
        );

        BackgroundFill bgFill2 = new BackgroundFill(
            linearGradient2,
            CornerRadii.EMPTY,
            Insets.EMPTY
        );


        Font standardBoldLabel = Font.font("Arial", FontWeight.BOLD, 20);
        
        String itemBoxColor = "#00002F";
        String colorLightest = "#97FEED";

        Region itemBox;
        Region itemCover;
        Region labelRegion;
        Background itemCoverBg;
        
        itemBox = new Region();
        itemCoverBg = new Background(bgFill1, bgFill2);
        itemCover = new Region();
        labelRegion = new Region();

        this.slotNumLabel.setFont(standardBoldLabel);   
        
        itemBox.setPrefSize(ITEM_BOX_WIDTH, ITEM_BOX_HEIGHT);
        itemBox.setStyle("-fx-background-color: "+itemBoxColor+";");
        
        // Uses the previosly set Linear gradients above

        itemCover.setPrefSize(ITEM_BOX_WIDTH-20, ITEM_BOX_HEIGHT-30);
        itemCover.setBackground(itemCoverBg);

        
        labelRegion.setPrefSize(50, 30);
        labelRegion.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        labelRegion.setStyle("-fx-background-color: black;"
                            +"-fx-border-width: 2 2 0 2;"
                            +"-fx-border-color:"+colorLightest+";"); 
        itemCover.setBackground(itemCoverBg);
        StackPane.setAlignment(slotItemNameLabel, Pos.TOP_CENTER);
        StackPane.setAlignment(labelRegion, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(slotNumLabel, Pos.BOTTOM_CENTER);

        if(!isItem)
        {
            this.getChildren().addAll(itemBox, this.slotItemNameLabel, itemCover, labelRegion, slotNumLabel);
            this.prefHeightProperty().bind(bindedPane.heightProperty().multiply(0.3));
            this.prefWidthProperty().bind(bindedPane.widthProperty().multiply(0.3));
            

        }
       else
            this.getChildren().addAll(itemBox, this.currentImageView, this.slotItemNameLabel, itemCover, labelRegion, slotNumLabel);
        
    }



    private VBox hiddenPane;
    private String nameOfItem;
    private Label slotItemNameLabel;
    private Label slotNumLabel;
    private ImageView currentImageView;
    private Image itemImage;
    private VMachineModelPaneView bindedPane;
    private static final int ITEM_BOX_HEIGHT = 200;
    private static final int ITEM_BOX_WIDTH = 150;
}


