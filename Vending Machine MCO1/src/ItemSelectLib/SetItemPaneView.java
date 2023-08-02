package ItemSelectLib;



import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import Buttons.AddButton;
import Buttons.MenuButton;
import Buttons.SubButton;
import CustomSetup.CustomImageView;
import Models.VM_Regular;
import Models.VM_Slot;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
/**
 * This is is the item pane view for all the available items the user can set from
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class SetItemPaneView extends ScrollPane{


    public SetItemPaneView(Stage parentWin)
    {
        DecimalFormat df = new DecimalFormat("0.00");
        URL resourceUrl;
        int i;
        String colorBg = "#071952";
        String colorLightest = "#97FEED";


            
        i = 0;



        this.parentWin = parentWin;
        this.mainCanvasVBox = new VBox();
        this.hiddenExpandableVBox = new VBox();

        this.itemSectionGridPanes = new ArrayList<ItemSectionPane>();
        this.itemIndependencyList  = new LinkedHashMap<String, Integer>();
        this.createNewBtn = new MenuButton("Create New Item", 14);
        createNewBtn.setAlignment(Pos.CENTER);

        this.mainCanvasVBox.getChildren().add(createNewBtn);


        this.mainCanvasVBox.getChildren().add(hiddenExpandableVBox);
        
        i = 0;
        for (PresetItem presetItem : PresetItem.values()) {

            resourceUrl = getClass().getResource(presetItem.getImagePath());
            itemIndependencyList.put(presetItem.name(), presetItem.getIsIndependent());

            createNewItem(i, resourceUrl, presetItem.name(), 
                            df.format(presetItem.getPrice()), "0",
                            presetItem.getCalories() + "",
                            false);
            i++;

        }
        

        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setPadding(new Insets(5));
        
        this.prefWidthProperty().bind(parentWin.widthProperty().divide(5));
        this.setContent(mainCanvasVBox);
        mainCanvasVBox.prefWidthProperty().bind(parentWin.widthProperty().divide(5));
        hiddenExpandableVBox.prefWidthProperty().bind(mainCanvasVBox.widthProperty());
        mainCanvasVBox.setMaxWidth(243.2);
        hiddenExpandableVBox.setMaxWidth(243.2);
        this.setStyle("-fx-background-color: "+colorBg+";"
                    +"-fx-border-color: " + colorLightest + ";"
                    +"-fx-border-width: " + "0 0 0 4" + ";");

 
    }
    public void updateIndepndencyList(String name)
    {
        itemIndependencyList.put(name, 1);

    }

    public void setUpItemsView(boolean isSpecialVM) 
    {

        
        for (ItemSectionPane itemPane : itemSectionGridPanes) 
        {
  
            if (isSpecialVM || itemIndependencyList.get(itemPane.getItemNameLabel().getText())==1) 
            {

                itemPane.setVisible();
            }
            else
            {
                itemPane.setInvisible();
            }
        }
    }
    public void createNewItem(int i, URL resourceUrl,
                              String itemName,    
                              String price,
                              String stock,
                              String calories, 
                              boolean isNewItem)
    {
 
        ItemSectionPane itemSectionPane = new ItemSectionPane(this, itemName, price, stock, calories, resourceUrl);
        
        //GridPane where all elements are added
        itemSectionGridPanes.add(itemSectionPane);

        if(isNewItem)
            hiddenExpandableVBox.getChildren().add(0, itemSectionGridPanes.get(i));
        else
        
            mainCanvasVBox.getChildren().add(itemSectionGridPanes.get(i));

    }

    public void createNewItem(String itemName, String calories)
    {
        URL resourceUrl = getClass().getResource("/Pics/default.png");
        createNewItem(itemSectionGridPanes.size(), resourceUrl, itemName,"", "0", calories, true);

        
    }

    public void removeAddedItems()
    {
        this.hiddenExpandableVBox.getChildren().clear();
    }
 
    public void resetStockTxtFields()
    {

        itemIndependencyList.clear();




        removeAddedItems();

        for (ItemSectionPane item: itemSectionGridPanes) {

            item.getStockField().setText("0");

        }

    }

    public VBox getMainCanvasVBox() {
        return mainCanvasVBox;
    }
    public Button getCreateNewBtn() {
        return createNewBtn;
    }

    public ArrayList<ItemSectionPane> getItemSectionGridPanes() 
    {
        return itemSectionGridPanes;
    }


    public int getMaxItems()
    {
        return itemSectionGridPanes.size();
    }

    public void setActionEventCreateBtn(EventHandler<ActionEvent> eventHandler)
    {
        createNewBtn.setOnAction(eventHandler);
    }
    public void addEventHandlerOnMouseClick(EventHandler<MouseEvent> eventHandler, int trackedIndex)
    {
        this.itemSectionGridPanes.get(trackedIndex).addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

    }
    




    private VBox mainCanvasVBox;
    private VBox hiddenExpandableVBox;
    private Button createNewBtn;
    private ArrayList<ItemSectionPane> itemSectionGridPanes;
    private LinkedHashMap<String, Integer> itemIndependencyList;
    private Stage parentWin;

}
