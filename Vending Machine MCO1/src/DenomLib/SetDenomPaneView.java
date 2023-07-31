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
        String colorLightest = "#97FEED";
        Label titleOfPane;
        String tempString;
        double tempVal;

        int currInd;
        
        Font headerBoldLabel = Font.font("Helvetica", FontWeight.BOLD, 18);
        DenominationBtn tempDenomBtn;
        labelTextData = new LinkedHashMap<String, Double>();

        for(Denomination denoms : Denomination.values())
        {
            labelTextData.put(denoms.getName(), denoms.getValue());
        }
        
        
        this.denomSetSections = new DenomSetSection[13];
        
        
        iteratorLabel = labelTextData.keySet().iterator();
        iteratorValue = labelTextData.values().iterator();


        leftPaneGrid = new GridPane();

        titleOfPane = new Label("Set Denominations");
        titleOfPane.setFont(headerBoldLabel);
        titleOfPane.setAlignment(Pos.CENTER);
        titleOfPane.setStyle("-fx-text-fill:"+ colorLightest + ";");
        
        currInd = 0;

        leftPaneGrid.add(titleOfPane,0,0);
        GridPane.setColumnSpan(titleOfPane, 2);
        for(int i = 0; i < 13; i++)
        {

 
            tempString = iteratorLabel.next();
            tempVal = iteratorValue.next();

            if(tempVal == 20 && i%2 == 1)
            {
                tempDenomBtn = new DenominationBtn(df.format(tempVal) + " Bill");
            }
            else if(tempVal == 20 && i%2 == 0)
                tempDenomBtn = new DenominationBtn(df.format(tempVal) + " Coin");
            else
                tempDenomBtn = new DenominationBtn(df.format(tempVal));
            denomSetSections[i] = new DenomSetSection(parentWin, tempString, tempVal, tempDenomBtn);


            leftPaneGrid.add(denomSetSections[i].getDenomGraphic(), 0,currInd+1);
            leftPaneGrid.add(denomSetSections[i].getButtonAndTextFieldPane(), 1,currInd+1);
            leftPaneGrid.add(denomSetSections[i].getMoneyNameLabel(), 0, currInd+2);
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

        denomSetSections[ind].setActionEventAddBtn(eventHandler);

    }

    public void setActionEventSubBtn(int ind, EventHandler<ActionEvent> eventHandler) 
    {
 

        denomSetSections[ind].setActionEventSubBtn(eventHandler);

    }
    public void setActionEventTxtField(int ind, EventHandler<ActionEvent> eventHandler)
    {

        denomSetSections[ind].setActionEventTxtField(eventHandler);

    }

    public void setTxtFieldFilter(int ind, EventType<KeyEvent> eventType, EventHandler<KeyEvent> eventFilter)
    {

        denomSetSections[ind].setTxtFieldFilter(eventType, eventFilter);
 
    }

    public void setTxtFieldFocusListener(int ind, ChangeListener<Boolean> changeListener)
    {
        denomSetSections[ind].setTxtFieldFocusListener(changeListener);
    }





    public DenomSetSection[] getDenomSetSections() 
    {
        return denomSetSections;
    }


    private GridPane leftPaneGrid;
    private DenomSetSection[] denomSetSections;

    private LinkedHashMap<String, Double> labelTextData;
}
