package ItemSelectLib;



import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import Buttons.AddButton;
import Buttons.MenuButton;
import Buttons.SubButton;
import CustomSetup.CustomImageView;
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

public class SetItemPaneView extends ScrollPane{


    public SetItemPaneView(Stage parentWin)
    {

        
        String colorBg = "#071952";



        String colorLightest = "#97FEED";
        
        ArrayList<String> tempListURL;
        ArrayList<String> tempListItemName;

        
        this.parentWin = parentWin;
        

        createNewBtn = new MenuButton("Create New Item", 14);
        createNewBtn.setAlignment(Pos.CENTER);

        
        tempListURL = new ArrayList<String>();
        tempListItemName = new ArrayList<>();
        for(PresetItem presetItem : PresetItem.values())
        {
            tempListItemName.add(presetItem.name());
            tempListURL.add(presetItem.getImagePath());
        }

  

        maxItems = tempListURL.size();

        mainCanvasVBox = new VBox();
        hiddenExpandableVBox = new VBox();

        this.itemSectionGridPanes = new ArrayList<ItemSectionPane>();


        mainCanvasVBox.getChildren().add(createNewBtn);
        // hiddenExpandableVBox.prefWidthProperty().bind(this.widthProperty());
        // hiddenExpandableVBox.setMaxWidth(this.getWidth()-10);

        mainCanvasVBox.getChildren().add(hiddenExpandableVBox);
        // mainCanvasVBox.setPrefWidth(243.2);
        // mainCanvasVBox.prefWidthProperty().bind(this.widthProperty());
        // mainCanvasVBox.setMaxWidth(this.getWidth()-10);
        // mainCanvasVBox.setSpacing(10);

        for(int i = 0; i < maxItems; i++)
        {

            URL resourceUrl = getClass().getResource(tempListURL.get(i));
            if (resourceUrl != null) {
                    
                createNewItem(i, resourceUrl, tempListItemName.get(i), "20", false);

            }

            

        }
        

        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setPadding(new Insets(5));
        this.setId("denomPane");
        // this.setPrefHeight(639);
        
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


    public void createNewItem(int i, URL resourceUrl, String itemName, String calories, boolean isNewItem)
    {
 
        ItemSectionPane itemSectionPane = new ItemSectionPane(this, itemName, calories, resourceUrl);

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
        createNewItem(maxItems, resourceUrl, itemName, calories, true);
        maxItems += 1;
        
    }

    public void removeAddedItems()
    {
        this.hiddenExpandableVBox.getChildren().clear();
    }
    
    public VBox getMainCanvasVBox() {
        return mainCanvasVBox;
    }
    public Button getCreateNewBtn() {
        return createNewBtn;
    }

    public int getMaxItems() {
        return maxItems;
    }

    public ArrayList<ItemSectionPane> getItemSectionGridPanes() 
    {
        return itemSectionGridPanes;
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
    private int maxItems;
    private Stage parentWin;

}
