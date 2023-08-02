package Boxes;



import Buttons.MinorButton;
import Labels.LabelToField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * This class simulates prompting the user for a new item and calories it is associated with
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class ItemNameBox 
{
    public ItemNameBox()
    {
        this.window = new Stage();
        this.okButton = new MinorButton("OK");
        this.nameTextField = new TextField();
        this.calorieTextField = new TextField();

    }

    public void display(String title, String message)
    {


        String colorBg = "#071952";
        Font standardBoldLabel = Font.font("Arial", FontWeight.BOLD, 16);
        
        Label labePromptName = new LabelToField(message, 14);
        Label labelPromptCalories = new LabelToField("How many claories?");

        // Buttons
        window.setTitle(title);
        window.setMinWidth(600);


        nameTextField.setMaxWidth(400);
        calorieTextField.setMaxWidth(400);

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labePromptName, nameTextField, labelPromptCalories, calorieTextField, okButton);

        layout.setStyle("-fx-base: "+colorBg+";");
        layout.setPadding(new Insets(50));
        Scene scene = new Scene(layout);


        window.setScene(scene);
        window.showAndWait();



    }

    public void setCalTxtFieldFilter(EventType<KeyEvent> eventType, EventHandler<KeyEvent> eventFilter)
    {

        this.calorieTextField.addEventFilter(eventType, eventFilter);
 
    }
    public void addActionEventOkBtn(EventHandler<ActionEvent> eventHandler) 
    {
        this.okButton.setOnAction(eventHandler);
    }
    public TextField getNameTextField() 
    {
        return nameTextField;
    }
    public TextField getCalorieTextField() {
        return calorieTextField;
    }
    public Stage getWindow() {
        return window;
    }
    public Button getOkButton() {
        return okButton;
    }


    private Stage window;
    private TextField nameTextField;
    private TextField calorieTextField;
    private Button okButton;

}
