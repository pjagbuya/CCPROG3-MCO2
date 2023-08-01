package MaintenanceLib;

import Labels.HeaderLabel;
import Labels.SubLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MaintenanceStockedInfoView extends BorderPane{
    public MaintenanceStockedInfoView(Stage parentWin)
    {

        setupInitLabels();

    }
    public void resetInfo()
    {
        this.setupInitLabels();
    }
    public void addInfo(String itemName, 
                        String prevStock, 
                        String itemsSold, 
                        String itemsInStock, 
                        String profitCollected) 
    {
        this.addToItemName(itemName);
        this.addToPrevStock(prevStock);
        this.addToItemsSold(itemsSold);
        this.addToItemsInStock(itemsInStock);
        this.addToProfitCollected(profitCollected);
    }
    private void setupInitLabels()
    {
        int headerSize = 20;


        ScrollPane mainPane = new ScrollPane();
        StackPane setCenteredPane = new StackPane();

        Label lblItemName = new HeaderLabel("Item Name", headerSize);
        Label lblPrevStock = new HeaderLabel("Item Prev Stock", headerSize);
        Label lblItemsSold = new HeaderLabel("Items Sold", headerSize);
        Label lblItemsInStock = new HeaderLabel("Items in Stock", headerSize);
        Label lblProfitCollected = new HeaderLabel("Profit Collected", headerSize);


        this.gridPane = new GridPane();
        this.indItemPrevStockRow = 0;
        this.indItemSoldRow = 0;
        this.indItemInStockRow = 0;
        this.indProfitCollectedIndRow = 0;
 // Set the min, max, and pref width
        lblItemName.setMinWidth(MAX_WIDTH);
        lblItemName.setMaxWidth(MAX_WIDTH);
        lblItemName.setPrefWidth(MAX_WIDTH);

        lblPrevStock.setMinWidth(MAX_WIDTH);
        lblPrevStock.setMaxWidth(MAX_WIDTH);
        lblPrevStock.setPrefWidth(MAX_WIDTH);

        lblItemsSold.setMinWidth(MAX_WIDTH);
        lblItemsSold.setMaxWidth(MAX_WIDTH);
        lblItemsSold.setPrefWidth(MAX_WIDTH);

        lblItemsInStock.setMinWidth(MAX_WIDTH);
        lblItemsInStock.setMaxWidth(MAX_WIDTH);
        lblItemsInStock.setPrefWidth(MAX_WIDTH);

        lblProfitCollected.setMinWidth(MAX_WIDTH);
        lblProfitCollected.setMaxWidth(MAX_WIDTH);
        lblProfitCollected.setPrefWidth(MAX_WIDTH);
       
        lblItemName.setStyle(BORDERSTYLE);
        lblPrevStock.setStyle(BORDERSTYLE);
        lblItemsSold.setStyle(BORDERSTYLE);
        lblItemsInStock.setStyle(BORDERSTYLE);
        lblProfitCollected.setStyle(BORDERSTYLE);

        gridPane.setHgap(1);
        gridPane.setVgap(10);

        // Adding the labels to the gridPane
        gridPane.add(lblItemName, NAME_COL, 0);
        gridPane.add(lblPrevStock, PREV_STOCK_COL, 0);
        gridPane.add(lblItemsSold, ITEMSOLD_COL, 0);
        gridPane.add(lblItemsInStock, ITEMINSTOCK_COL, 0);
        gridPane.add(lblProfitCollected, PROFITCOLLECT_COL, 0);

        setCenteredPane.getChildren().add(gridPane);
        setCenteredPane.setAlignment(Pos.CENTER);
        mainPane.setContent(setCenteredPane);
        BorderPane.setAlignment(mainPane, Pos.CENTER);
        this.setCenter(mainPane);
    }
    private void addToItemName(String name)
    {
        Label tempLabel = new SubLabel(name, 18);
        tempLabel.setStyle(BORDERSTYLE);
        gridPane.add(tempLabel, NAME_COL, indItemNameRow);
        indItemNameRow += 1;
    }
    private void addToPrevStock(String prevStock) 
    {
        Label tempLabel = new SubLabel(prevStock, 18);
        tempLabel.setStyle(BORDERSTYLE);
        gridPane.add(tempLabel, PREV_STOCK_COL, indItemPrevStockRow);
        indItemPrevStockRow += 1;
    }
    
    private void addToItemsSold(String itemsSold) 
    {
        Label tempLabel = new SubLabel(itemsSold, 18);
        tempLabel.setStyle(BORDERSTYLE);
        gridPane.add(tempLabel, ITEMSOLD_COL, indItemSoldRow);
        indItemSoldRow += 1;
    }
    
    private void addToItemsInStock(String itemsInStock) {
        Label tempLabel = new SubLabel(itemsInStock, 18);
        tempLabel.setStyle(BORDERSTYLE);
        gridPane.add(tempLabel, ITEMINSTOCK_COL, indItemInStockRow);
        indItemInStockRow += 1;
    }
    
    private void addToProfitCollected(String profitCollected) {
        Label tempLabel = new SubLabel(profitCollected, 18);
        tempLabel.setStyle(BORDERSTYLE);
        gridPane.add(tempLabel, PROFITCOLLECT_COL, indProfitCollectedIndRow);
        indProfitCollectedIndRow += 1;
    }

    private static final int NAME_COL = 0;
    private static final int PREV_STOCK_COL = 1;
    private static final int ITEMSOLD_COL = 2;
    private static final int ITEMINSTOCK_COL = 3;
    private static final int PROFITCOLLECT_COL = 4;
    private static final String COLOR_FONT = "#97FEED";
    private static final String BORDERSTYLE = "-fx-border-color:"+COLOR_FONT+"; -fx-border-width: 1; -fx-padding: 10;";
       
    private int indItemNameRow;
    private int indItemPrevStockRow;
    private int indItemSoldRow;
    private int indItemInStockRow;
    private int indProfitCollectedIndRow;
    private GridPane gridPane;

    private final static double MAX_WIDTH = 220;



}
