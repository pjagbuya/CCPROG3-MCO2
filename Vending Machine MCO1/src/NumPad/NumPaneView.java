package NumPad;



import Buttons.MinorButton;
import Buttons.NumButton;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class NumPaneView extends BorderPane
{

    public NumPaneView()
    {
        super();
        String colorBg = "#071952";
        HBox slotLabelPane = new HBox();
        Label slotLabel = new LabelToField("Slot Number", 24);
        GridPane layout = new GridPane();
        numField = new TextField();
        numButtons = new Button[10];
        clrButton = new NumButton("CLR", 20);

        delButton = new NumButton("DEL", 20);
        entrButton = new NumButton("ENTER", 24);

        entrButton.setPrefSize(235, BASELINE_OFFSET_SAME_AS_HEIGHT+20);
        numField.setPrefSize(235, 150);
        numField.setAlignment(Pos.CENTER);
        numField.setEditable(false);
        numField.setFont(new Font("Courier New", 72));

        layout.add(numField, 0, 0, 3, 1);
        for(int i = 0; i <= 9; i++)
        {
            numButtons[i] = new NumButton(i + "", 20);
            if(i == 9)
            {
                layout.add(clrButton, i%3, i/3 + 1);
                layout.add(numButtons[i], i%3+1, i/3 + 1);
                layout.add(delButton, i%3+2, i/3 + 1);
            }
            else
            {
                layout.add(numButtons[i], i%3, i/3+1);
            }
            

        }
        layout.setHgap(5);
        layout.setVgap(5);
        layout.add(entrButton, 0, 5, 3, 1);

        layout.setAlignment(Pos.CENTER);

        slotLabelPane.getChildren().add(slotLabel);
        slotLabelPane.setAlignment(Pos.CENTER);
        
        this.setTop(slotLabelPane);
        this.setCenter(layout);
        this.setMargin(layout, new Insets(10, 10, 10, 10));
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
