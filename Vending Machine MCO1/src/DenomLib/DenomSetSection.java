package DenomLib;

import java.text.DecimalFormat;

import Buttons.AddButton;
import Buttons.DenominationBtn;
import Buttons.SubButton;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DenomSetSection 
{
    public DenomSetSection(Stage parentWin, String moneyName, double moneyVal, DenominationBtn denomBtnGraphic)
    {
        String colorLighter = "#35A29F";
        DecimalFormat df = new DecimalFormat("0.00");
        Font stringNumLabelFont = Font.font("Courier New", FontWeight.BOLD, 14);
        Font standardBoldLabel = Font.font("Arial", FontWeight.BOLD, 16);
        Font headerBoldLabel = Font.font("Helvetica", FontWeight.BOLD, 18);

        
        HBox buttonPane;
        Region spacer;
        Label numLabel;

        spacer = new Region();
        textFieldStackPane = new StackPane();
        

        numLabel = new Label(0+"");

        this.moneyNameLabel = new Label(moneyName);
        this.moneyNameLabel.setFont(stringNumLabelFont);
        this.moneyNameLabel.setWrapText(true);


        buttonAndTextFieldPane = new VBox();
        buttonPane = new HBox();
        addButton = new AddButton(parentWin);
        subButton = new SubButton(parentWin);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.prefWidthProperty().bind(buttonPane.widthProperty().divide(7));
        buttonPane.getChildren().addAll(addButton, spacer, subButton);
      
        this.DenomGraphic = denomBtnGraphic;

        this.textField = new TextField();
        textField.setFont(standardBoldLabel);
        textField.setText("0");

        textField.setStyle("-fx-background-color: "+colorLighter+";" +
                                "-fx-alignment: center;");
        numLabel.setFont(standardBoldLabel);


        


        textFieldStackPane.getChildren().add(textField);



        buttonAndTextFieldPane.getChildren().addAll(buttonPane, textFieldStackPane);
        buttonAndTextFieldPane.prefHeight(50);
        buttonAndTextFieldPane.prefWidth(50);



    }


    public VBox getButtonAndTextFieldPane() {
        return buttonAndTextFieldPane;
    }
    public Button getAddButton() {
        return addButton;
    }
    public Button getSubButton() {
        return subButton;
    }
    public TextField getTextField() {
        return textField;
    }
    public Label getMoneyNameLabel() {
        return moneyNameLabel;
    }
    public Button getDenomGraphic() {
        return DenomGraphic;
    }

    public void setActionEventAddBtn(EventHandler<ActionEvent> eventHandler) 
    {

        addButton.setOnAction(eventHandler);

    }

    public void setActionEventSubBtn(EventHandler<ActionEvent> eventHandler) 
    {
 

        subButton.setOnAction(eventHandler);

    }
    public void setActionEventTxtField(EventHandler<ActionEvent> eventHandler)
    {

        textField.setOnAction(eventHandler);

    }

    public void setTxtFieldFilter( EventType<KeyEvent> eventType, EventHandler<KeyEvent> eventFilter)
    {

        textField.addEventFilter(eventType, eventFilter);
 
    }

    public void setTxtFieldFocusListener(ChangeListener<Boolean> changeListener)
    {
        textField.focusedProperty().addListener(changeListener);
    }

    private StackPane textFieldStackPane;
    private VBox buttonAndTextFieldPane;
    private TextField textField;
    private Label moneyNameLabel;
    private Button addButton;
    private Button subButton;
    private Button DenomGraphic;
}
