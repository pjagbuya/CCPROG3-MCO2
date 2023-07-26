package StartLib;


import Buttons.*;
import Labels.LabelToField;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SetupVMPopUpView extends Stage
{
    public SetupVMPopUpView(Stage parentWin, Scene targetChangeScene)
    {

        String colorBg = "#071952";
        String colorLightest = "#97FEED";

        Font headerBoldLabel = Font.font("Helvetica", FontWeight.BOLD, 12);
        
    
        Label vmLabel = new LabelToField("Vending Mahchines");
        Label addNewLabel = new LabelToField("Add a vending machine");
        Label nameLabel = new LabelToField("VM Name: ");
        Label slotCapLabel = new LabelToField("Slot Capacity: ");
        Label itemCapLabel = new LabelToField("Item Capacity: ");
        Label vmTypeLabel = new LabelToField("Type of Vending Machine: ");

        VBox mainPane = new VBox();         // Main start pane
        VBox vmMadePane = new VBox();       // Content to list of newly added vending machines
        VBox emptyPane = new VBox();        // Spacer for middle
        VBox hiddenPane = new VBox(40);       // hidden pane to show type of vending machine

      
        HBox slotNodesHBox = new HBox(10);        // slot cap related section
        HBox itemNodesHBox = new HBox(10);        // item cap related section
        HBox nameLabelAndTextHBox = new HBox(10); // Name and TextBox section
        HBox typeSelectHBox = new HBox(10);           // type of vending machine selection
        HBox spacer = new HBox();
    


        slotAddBtn = new AddButton(parentWin, 25, 25);
        slotSubBtn = new SubButton(parentWin, 25, 25);
        
        itemAddBtn = new AddButton(parentWin, 25, 25);
        itemSubBtn = new SubButton(parentWin, 25, 25);     


        /* hiddenPane for selecting the type of Vending Machine */
        setupVMHidTopBar = new SetupVMTopBarView(parentWin, targetChangeScene);
        nameField = new TextField();
        nameLabelAndTextHBox.getChildren().addAll(nameLabel, nameField);
        setupVMHidTopBar.addToChildren(spacer);

        regRadButton = new RadioButton("REGULAR");
        spRadButton = new RadioButton("SPECIAL");
        radButtons = new ToggleGroup();
        regRadButton.setToggleGroup(radButtons);
        spRadButton.setToggleGroup(radButtons);

        regRadButton.setFont(headerBoldLabel);
        regRadButton.setStyle("-fx-text-fill: "+colorLightest+";");
        spRadButton.setFont(headerBoldLabel);
        spRadButton.setStyle("-fx-text-fill: "+colorLightest+";");
        regRadButton.setSelected(true);

        typeSelectHBox.getChildren().addAll(vmTypeLabel, regRadButton, spRadButton);

        slotCapField = new TextField("8");
        itemCapField = new TextField("10");

        slotNodesHBox.getChildren().addAll(slotCapLabel, slotAddBtn, slotCapField, slotSubBtn);
        itemNodesHBox.getChildren().addAll(itemCapLabel,itemAddBtn, itemCapField, itemSubBtn);


        /*Type of Vending Machine Scene */

        /*Main Pane Scene */

        VBox.setVgrow(emptyPane, Priority.ALWAYS);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        setupVMTopBar = new SetupVMTopBarView(parentWin, null);
        setupVMTopBar.removeFinishBtn();





        vmMadePane.getChildren().add(vmLabel);



        bigAddButton = new AddButton(parentWin,30, 30);
        bigAddButton.setPrefWidth(Double.MAX_VALUE);
        bigAddButton.prefHeightProperty().bind(parentWin.heightProperty().divide(10));
        bigAddButton.setText("Add Vending Machine");
        setupVMTopBar.prefHeight(200);



        //Main pane
        mainPane.setStyle("-fx-background-color: " + colorBg +";");
        mainPane.getChildren().addAll(setupVMTopBar);


        mainPane.setPrefHeight(600);
        mainPane.setPrefWidth(600);

        mainPane.getChildren().addAll(vmMadePane, emptyPane,addNewLabel, bigAddButton);
        mainPane.setPadding(new Insets(20));


        // hidden pane for type select of vending machine setup
        hiddenPane.setStyle("-fx-background-color: " + colorBg +";");

        hiddenPane.getChildren().addAll(setupVMHidTopBar, nameLabelAndTextHBox, typeSelectHBox, slotNodesHBox, itemNodesHBox);
        hiddenPane.setPadding(new Insets(20));


        vmMainScene = new Scene(mainPane, 600, 600);

        
        vmTypeScene = new Scene(hiddenPane, 600, 600);

        

        this.prevScene = vmMainScene;
        
        this.initModality(Modality.APPLICATION_MODAL);
        this.setScene(vmMainScene);
        this.setTitle("Vending Machine Select");


    }



    public Button getItemAddBtn() {
        return itemAddBtn;
    }

    public Button getItemSubBtn() {
        return itemSubBtn;
    }
    public TextField getItemCapField() {
        return itemCapField;
    }
    public Button getSlotAddBtn() {
        return slotAddBtn;
    }
    public Button getSlotSubBtn() {
        return slotSubBtn;
    }
    public TextField getSlotCapField() {
        return slotCapField;
    }
    public TextField getNameField() {
        return nameField;
    }

    public Scene getPrevScene() {
        return prevScene;
    }
    public ToggleGroup getRadButtons() {
        return radButtons;
    }
    public RadioButton getRegRadButton() {
        return regRadButton;
    }
    public RadioButton getSpRadButton() {
        return spRadButton;
    }
    
    public Button getBigAddButton() {
        return bigAddButton;
    }
    public Scene getVmMainScene() {
        return vmMainScene;
    }
    public Scene getVmTypeScene() {
        return vmTypeScene;
    }
    
    public SetupVMTopBarView getSetupVMHidTopBar() {
        return setupVMHidTopBar;
    }
    public SetupVMTopBarView getSetupVMTopBar() {
        return setupVMTopBar;
    }

    public void setBigAddButtonAction(EventHandler<ActionEvent> eventHandler)
    {
        bigAddButton.setOnAction(eventHandler);
    }


    public void setItemAddBtnAction(EventHandler<ActionEvent> eventHandler)
    {   
        this.itemAddBtn.setOnAction(eventHandler);
    }
    public void setSlotAddBtnAction(EventHandler<ActionEvent> eventHandler)
    {
        
        this.slotAddBtn.setOnAction(eventHandler);
    }



    public void setItemSubBtnAction(EventHandler<ActionEvent> eventHandler)
    {
        this.itemSubBtn.setOnAction(eventHandler);

    }
    public void setSlotSubBtnAction(EventHandler<ActionEvent> eventHandler)
    {

        this.slotSubBtn.setOnAction(eventHandler);
    }

    public void addTxtFieldsEventFilter(EventType<KeyEvent> eventType, EventHandler<KeyEvent> eventFilter)
    {
        this.itemCapField.addEventFilter(eventType, eventFilter);
        this.slotCapField.addEventFilter(eventType, eventFilter);
    }


    public void setItemFieldFocusListener(ChangeListener<Boolean> changeListener)
    {

        this.itemCapField.focusedProperty().addListener(changeListener);
    }

    public void setSlotFieldFocusListener(ChangeListener<Boolean> changeListener)
    {
        this.slotCapField.focusedProperty().addListener(changeListener);

    }   
    public void setItemTextChangeListener(ChangeListener<String> changeListener)
    {
        this.itemCapField.textProperty().addListener(changeListener);

    }
    public void setSlotTextChangeListener(ChangeListener<String> changeListener)
    {
        this.slotCapField.textProperty().addListener(changeListener);
    }

    private SetupVMTopBarView setupVMTopBar;
    private SetupVMTopBarView setupVMHidTopBar;
    private Scene vmMainScene;
    private Scene vmTypeScene;
    private Scene prevScene;

    private TextField slotCapField;
    private TextField itemCapField;
    private TextField nameField;

    private Button bigAddButton;
    private Button slotAddBtn;
    private Button itemAddBtn;
    private Button slotSubBtn;
    private Button itemSubBtn;



    private RadioButton regRadButton;
    private RadioButton spRadButton;
    private ToggleGroup radButtons;



}
