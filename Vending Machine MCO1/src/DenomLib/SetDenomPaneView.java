package DenomLib;

import Buttons.AddButton;
import Buttons.DenominationBtn;
import Buttons.SubButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;

import java.sql.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.LinkedHashMap;



public class SetDenomPaneView extends ScrollPane
{


    


    public SetDenomPaneView(Stage parentWin)
    {
 
        RowConstraints rowConstraints = new RowConstraints();

        
        
        DecimalFormat df = new DecimalFormat("0.00");

        Iterator<String> iteratorLabel;
        Iterator<Double> iteratorValue;



        
        String colorBg = "#071952";
        String colorLighter = "#35A29F";
        String colorLightest = "#97FEED";

        Label titleOfPane;
        Label numLabel;
        Label tempLabel;
        String tempString;
        double tempVal;
        HBox buttonPane;
        VBox buttonAndTextFieldPane;
        StackPane textFieldAndLabelStackPane;
        Region spacer;
        int currInd;


        Font stringNumLabelFont = Font.font("Courier New", FontWeight.BOLD, 14);
        Font standardBoldLabel = Font.font("Arial", FontWeight.BOLD, 16);
        Font headerBoldLabel = Font.font("Helvetica", FontWeight.BOLD, 18);


        

        labelTextData = new Money();
        
        iteratorLabel = labelTextData.getDenominations().keySet().iterator();
        iteratorValue = labelTextData.getStrToVal().values().iterator();


        leftPaneGrid = new GridPane();
        labels = new Label[13];
        denomBtns = new DenominationBtn[13];
        textFields = new TextField[13];

        addButtons = new Button[13];
        subButtons = new Button[13];
        titleOfPane = new Label("Set Denominations");
        titleOfPane.setFont(headerBoldLabel);
        titleOfPane.setAlignment(Pos.CENTER);
        titleOfPane.setStyle("-fx-text-fill:"+ colorLightest + ";");
        
        currInd = 0;

        leftPaneGrid.add(titleOfPane,0,0);
        GridPane.setColumnSpan(titleOfPane, 2);
        for(int i = 0; i < 13; i++)
        {

            spacer = new Region();
            textFieldAndLabelStackPane = new StackPane();

            numLabel = new Label(0+"");

            
            
            tempString = iteratorLabel.next();
            tempVal = iteratorValue.next();
            tempLabel = new Label(tempString);
            tempLabel.setFont(stringNumLabelFont);
            tempLabel.setWrapText(true);


            buttonAndTextFieldPane = new VBox();
            buttonPane = new HBox();
            addButtons[i] = new AddButton(parentWin);
            subButtons[i] = new SubButton(parentWin);
            

            HBox.setHgrow(spacer, Priority.ALWAYS);
            spacer.prefWidthProperty().bind(buttonPane.widthProperty().divide(7));
            buttonPane.getChildren().addAll(addButtons[i], spacer, subButtons[i]);
            

            labels[i] = tempLabel;
            denomBtns[i] = new DenominationBtn(df.format(tempVal));
            textFields[i] = new TextField();
            textFields[i].setFont(standardBoldLabel);
            textFields[i].setText("0");

            textFields[i].setStyle("-fx-background-color: "+colorLighter+";" +
                                   "-fx-alignment: center;");
            numLabel.setFont(standardBoldLabel);


            


            textFieldAndLabelStackPane.getChildren().add(textFields[i]);

            buttonAndTextFieldPane.getChildren().addAll(buttonPane, textFieldAndLabelStackPane);
            buttonAndTextFieldPane.prefHeight(50);
            buttonAndTextFieldPane.prefWidth(50);


            leftPaneGrid.add(denomBtns[i], 0,currInd+1);
            leftPaneGrid.add(buttonAndTextFieldPane, 1,currInd+1);
            leftPaneGrid.add(tempLabel, 0, currInd+2);
            currInd += 3;

        }
        for (int i = 0; i < currInd; i++) {
            rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            rowConstraints.setFillHeight(true);
            rowConstraints.setMinHeight(Region.USE_PREF_SIZE);
            
            leftPaneGrid.getRowConstraints().add(rowConstraints);
        }

        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setFillHeight(true);
        rowConstraints.setMinHeight(Region.USE_PREF_SIZE);        

        leftPaneGrid.setPadding(new Insets(20, 5, 0, 10));
        leftPaneGrid.setHgap(5); // Horizontal gap
        leftPaneGrid.setVgap(10); // Vertical gap
        leftPaneGrid.setStyle("-fx-background-color: " + colorBg + ";" );


        leftPaneGrid.prefWidthProperty().bind(this.widthProperty().subtract(20));
        

        this.setId("denomPane");
        this.prefWidthProperty().bind(parentWin.widthProperty().divide(5));
        this.setContent(leftPaneGrid);

        this.setStyle("-fx-background-color: "+colorBg+";");

    }
    public void setActionEventAddBtn(int ind, EventHandler<ActionEvent> eventHandler) 
    {

        addButtons[ind].setOnAction(eventHandler);

    }

    public void setActionEventSubBtn(int ind, EventHandler<ActionEvent> eventHandler) 
    {
 

        subButtons[ind].setOnAction(eventHandler);

    }
    public void setActionEventTxtField(int ind, EventHandler<ActionEvent> eventHandler)
    {

        textFields[ind].setOnAction(eventHandler);

    }

    public void setTxtFieldFilter(int ind, EventType<KeyEvent> eventType, EventHandler<KeyEvent> eventFilter)
    {

        textFields[ind].addEventFilter(eventType, eventFilter);
 
    }

    public void setTxtFieldFocusListener(int ind, ChangeListener<Boolean> changeListener)
    {
        textFields[ind].focusedProperty().addListener(changeListener);
    }





    public Label[] getLabels() {
        return labels;
    }
    public GridPane getLeftPaneGrid() {
        return leftPaneGrid;
    }

    public TextField[] getTextFields() {
        return textFields;
    }
    public Button[] getAddButtons() {
        return addButtons;
    }
    public Button[] getSubButtons() {
        return subButtons;
    }


    private GridPane leftPaneGrid;
    private Label[] labels;
    private Button[] addButtons, subButtons;
    private DenominationBtn[] denomBtns;
    private TextField[] textFields;


    private Money labelTextData;
}
