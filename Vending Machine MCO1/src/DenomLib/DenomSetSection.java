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
/**
 * This class represents a section of denomination that contains add and sub buttons
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class DenomSetSection 
{
    /**
     * This constructor accepts what window it is residing in, the money name, and the parent 
     * @param parentWin stage or window of this application
     * @param moneyName the name of the money to be set
     * @param moneyVal the value of said money
     * @param denomBtnGraphic the image graphic to be displayed
     */
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

    /**
     * Method gets the buttons at the particular pane
     * @return the container for that pane
     */
    public VBox getButtonAndTextFieldPane() {
        return buttonAndTextFieldPane;
    }
    /**
     * Method gets add button
     * @return the add button of this
     */
    public Button getAddButton() {
        return addButton;
    }
    /**
     * Method gets the sub button reference
     * @return gets the reference sub button of this
     */
    public Button getSubButton() {
        return subButton;
    }

    /**
     * Gets text field reference of this
     * @return text field reference of this
     */
    public TextField getTextField() {
        return textField;
    }

    /**
     * Get the money name label reference
     * @return 
     */
    public Label getMoneyNameLabel() {
        return moneyNameLabel;
    }
    /**
     * Gets the graphic reference associated with button
     * @return the reference of the button graphic
     */
    public Button getDenomGraphic() {
        return DenomGraphic;
    }


    /**
     * Sets the action event handler for the "Add" button.
     *
     * @param eventHandler the event handler to be set for the "Add" button
     */
    public void setActionEventAddBtn(EventHandler<ActionEvent> eventHandler) {
        addButton.setOnAction(eventHandler);
    }

    /**
     * Sets the action event handler for the "Subtract" button.
     *
     * @param eventHandler the event handler to be set for the "Subtract" button
     */
    public void setActionEventSubBtn(EventHandler<ActionEvent> eventHandler) {
        subButton.setOnAction(eventHandler);
    }

    /**
     * Sets the action event handler for the text field.
     *
     * @param eventHandler the event handler to be set for the text field
     */
    public void setActionEventTxtField(EventHandler<ActionEvent> eventHandler) {
        textField.setOnAction(eventHandler);
    }

    /**
     * Sets an event filter for the text field.
     *
     * @param eventType   the type of event to filter (e.g., KeyEvent.KEY_TYPED)
     * @param eventFilter the event filter to be set for the text field
     */
    public void setTxtFieldFilter(EventType<KeyEvent> eventType, EventHandler<KeyEvent> eventFilter) {
        textField.addEventFilter(eventType, eventFilter);
    }

    /**
     * Sets a focus listener for the text field.
     *
     * @param changeListener the focus change listener to be set for the text field
     */
    public void setTxtFieldFocusListener(ChangeListener<Boolean> changeListener) {
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
