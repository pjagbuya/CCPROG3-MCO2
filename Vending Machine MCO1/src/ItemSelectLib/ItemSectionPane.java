package ItemSelectLib;

import java.net.URL;

import Buttons.AddButton;
import Buttons.SubButton;
import CustomSetup.CustomImageView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * This item sections sets a particular prompting field for the user to reprice, add or subtract stocks
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class ItemSectionPane extends GridPane
{
    public ItemSectionPane(ScrollPane parentPane, String name, String price, String stock, String calories, URL resourceUrl)
    {

        // Pre-defined design fonts
        Font stringItemNameLabelFont = Font.font("Tahoma", FontWeight.BOLD, 16);
        Font standardBoldLabel = Font.font("Arial", FontWeight.BOLD, 12);
        RowConstraints rowConstraints = new RowConstraints();
        Label tempPriceLabel;
        Label tempStockLabel;

        if(!price.isEmpty())
            this.finalPrice = Double.parseDouble(price);
        else
            this.finalPrice = 20.00;


        // Label setups
        itemNameLabel = new Label(name);
        tempPriceLabel = new Label("Price");
        tempStockLabel = new Label("Stock");
        calLabel = new Label("Calories: " + calories);

        // Label font setup
        calLabel.setFont(standardBoldLabel);
        itemNameLabel.setFont(stringItemNameLabelFont);
        tempPriceLabel.setFont(standardBoldLabel);
        tempStockLabel.setFont(standardBoldLabel);


        // Setting of priceFields
        priceField = new TextField();
        priceField.setPromptText("Set Price (Php)");
        stockField = new TextField();
        priceField.setText(price);
        priceField.setPrefWidth(100);
        stockField.setPromptText("Set Stock");
        stockField.setText(stock);
        stockField.setPrefWidth(100);

        

        //Image setup
        picAndLabelVBoxPane = new VBox();
        image = new Image(resourceUrl.toExternalForm(), true);
        imageView = new CustomImageView(image, this);
        
        // Button setups
        addAndSubBtnHBoxPane = new HBox();
        addButton = new AddButton( 25,25);
        subButton = new SubButton( 25,25);
        addAndSubBtnHBoxPane.prefWidthProperty().bind(this.widthProperty().multiply(0.5));
        // adjustable width
        // HBox.setHgrow(addButton, Priority.ALWAYS);
        // HBox.setHgrow(subButton, Priority.ALWAYS);

        addAndSubBtnHBoxPane.getChildren().addAll(addButton, subButton);
        picAndLabelVBoxPane.getChildren().addAll(imageView, itemNameLabel, calLabel);
        picAndLabelVBoxPane.prefWidthProperty().bind(parentPane.widthProperty().divide(3));



         //This GridPane where all elements are added and some property setups
        itemNameLabel.setWrapText(true);
        GridPane.setVgrow(itemNameLabel, Priority.ALWAYS);
        this.setHgap(10);
        this.setVgap(10);
        this.add(picAndLabelVBoxPane,0,1,1, 5);

        this.add(tempPriceLabel,1,1);
        this.add(priceField,1,2);
        this.add(tempStockLabel,1, 3);
        this.add(stockField,1,4);
        this.add(addAndSubBtnHBoxPane,1, 5);




        this.setPadding(new Insets(10, 5, 0, 10));
        // rowConstraints.setPercentHeight(100 / 5);
        // this.getRowConstraints().add(rowConstraints);

        GridPane.setHalignment(picAndLabelVBoxPane, HPos.CENTER);


    }

    public ItemSectionPane(ScrollPane parentPane, String name, URL resourceURL)
    {
        this(parentPane, name, "", "", "20", resourceURL);

    }
    public ItemSectionPane(ScrollPane parentPane, String name, String calories, URL resourceURL)
    {
        this(parentPane, name, "", "", calories, resourceURL);

    }
    public void setVisible()
    {
        this.setVisible(true);
        this.setManaged(true); 
    }
    public void setInvisible()
    {
        this.setVisible(false);
        this.setManaged(false);  
    }
    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
    public void setItemPriceFieldText(double num)
    {
        this.priceField.setText(num +"");

    }
    public Label getCalLabel() 
    {
        return calLabel;
    }
    public Image getImage() 
    {
        return image;
    }
    public Label getItemNameLabel() 
    {
        return itemNameLabel;
    }
    public TextField getPriceField() 
    {
        return priceField;
    }
    public TextField getStockField() 
    {

        return stockField;
    }
    public double getFinalPrice() {
        return finalPrice;
    }

    public void setActionEventSubBtn(EventHandler<ActionEvent> eventHandler) 
    {

        subButton.setOnAction(eventHandler);

    }
    public void setActionEventAddBtn(EventHandler<ActionEvent> eventHandler)
    {

        addButton.setOnAction(eventHandler);

    }

    public void setPriceTxtFieldListener(ChangeListener<String> changeListener)
    {
        priceField.textProperty().addListener(changeListener);
    }
    public void setStockTxtFieldListener(ChangeListener<String> changeListener)
    {
        stockField.textProperty().addListener(changeListener);
    }

    public void addPriceTxtFieldsEventFilter(EventType<KeyEvent> eventType, EventHandler<KeyEvent> eventFilter)
    {
        this.priceField.addEventFilter(eventType, eventFilter);

    }

    public void addStockTxtFieldsEventFilter(EventType<KeyEvent> eventType, EventHandler<KeyEvent> eventFilter)
    {
        this.stockField.addEventFilter(eventType, eventFilter);
    }

    private VBox picAndLabelVBoxPane;
    private HBox addAndSubBtnHBoxPane;
    private Button addButton;
    private Button subButton;
    private Label itemNameLabel;
    private Label calLabel;
    private TextField priceField;
    private TextField stockField;
    private Image image;
    private ImageView imageView;
    private double finalPrice;
    
    
    
}
