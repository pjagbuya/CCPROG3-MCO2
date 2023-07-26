package NumPad;

import java.text.DecimalFormat;
import java.util.Iterator;

import Buttons.DenominationBtn;
import Buttons.NumButton;
import DenomLib.Money;
import Labels.LabelToField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class DenomNumPadView extends BorderPane
{
    public DenomNumPadView()
    {
        super();

        this.getChildren().clear();
        StackPane posDelPane;
        StackPane posClrPane;


        String colorBg = "#071952";
        HBox slotLabelPane = new HBox();
        Money moneyDict = new Money();
        
        Label slotLabel = new LabelToField("Total Inserted", 24);
        GridPane layout = new GridPane();
        DecimalFormat df = new DecimalFormat("0.00");
        Iterator<Double> iteratorValue = moneyDict.getStrToVal().values().iterator();
        int ind;
        int maxSize = moneyDict.getStrToVal().values().size();


        numField = new TextField();
        numButtons = new DenominationBtn[maxSize];
        clrButton = new NumButton("CLR", 18);

        delButton = new NumButton("UNDO", 18);
        entrButton = new NumButton("ENTER", 24);

        entrButton.setPrefSize(300, BASELINE_OFFSET_SAME_AS_HEIGHT+20);
        numField.setPrefSize(235, 150);
        numField.setAlignment(Pos.CENTER);
        numField.setEditable(false);
        numField.setFont(new Font("Courier New", 24));

        layout.add(numField, 0, 0, 3, 1);
        
        ind = 0;
        while(iteratorValue.hasNext())
        {
            
            numButtons[ind] = new DenominationBtn(df.format(iteratorValue.next()));
            ind++;
        }
        
        for(int i = 0; i < maxSize; i++)
        {
            posClrPane = new StackPane();
            posDelPane = new StackPane();
            posDelPane.getChildren().add(delButton);
            posClrPane.getChildren().add(clrButton);


            numButtons[i].setPrefSize(100, 50);
            if(i == maxSize-1)
            {
                layout.add(posClrPane, i%3, i/3 + 1);
                layout.add(numButtons[i], i%3+1, i/3 + 1);
                layout.add(posDelPane, i%3+2, i/3 + 1);
            }
            else
            {
                layout.add(numButtons[i], i%3, i/3+1);
            }
            

        }
        layout.setHgap(5);
        layout.setVgap(20);
        layout.add(entrButton, 0, maxSize/3+2, 3, 1);


        layout.setAlignment(Pos.CENTER);

        slotLabelPane.getChildren().add(slotLabel);
        slotLabelPane.setAlignment(Pos.CENTER);
        
        this.setTop(slotLabelPane);
        this.setCenter(layout);
        BorderPane.setMargin(layout, new Insets(10, 10, 10, 10));
        this.setStyle("-fx-background-color:"+colorBg+";");
    }


    public void setNumButtonAction(int trackedIndex, EventHandler<ActionEvent> eventHandler)
    {
        this.numButtons[trackedIndex].setOnAction(eventHandler);

    }
    public void setClrButtonAction(EventHandler<ActionEvent> eventHandler)
    {
        this.clrButton.setOnAction(eventHandler);

    }
    public void setDelButtonAction(EventHandler<ActionEvent> eventHandler)
    {
        this.delButton.setOnAction(eventHandler);

    }

    public void setEnterButtonAction(EventHandler<ActionEvent> eventHandler)
    {
        this.entrButton.setOnAction(eventHandler);

    }
    public Button getClrButton() 
    {
        return clrButton;
    }
    public Button getDelButton() 
    {
        return delButton;
    }
    public Button getEntrButton() 
    {
        return entrButton;
    }
    public Button[] getNumButtons() 
    {
        return numButtons;
    }   
    public TextField getNumField() 
    {
        return numField;
    }




    private TextField numField;
    private Button[] numButtons;
    private Button delButton;
    private Button clrButton;
    private Button entrButton;
}
