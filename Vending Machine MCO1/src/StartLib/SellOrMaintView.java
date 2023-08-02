package StartLib;
import Buttons.MenuButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * This class shows the sell or maintenance button option that the user will go through if he/she exits one of the vending features
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class SellOrMaintView extends BorderPane
{
    public SellOrMaintView(Stage parentWin)
    {
        
        String colorBg = "#071952";
        VBox btnSection = new VBox(20);

       this.parentWin = parentWin;

        // Create buttons
        toSellBtn = new MenuButton("Selling Features");
        toMaintBtn = new MenuButton("Maintainance Features");
        exitBtn = new MenuButton("Exit");


        btnSection.setAlignment(Pos.CENTER);
        btnSection.getChildren().addAll(this.toSellBtn, this.toMaintBtn, this.exitBtn);

        this.setCenter(btnSection);
        this.setStyle("-fx-base: " + colorBg+ ";");

    }
    public void setToSellBtnAction(EventHandler<ActionEvent> eventHandler) 
    {
        toSellBtn.setOnAction(eventHandler);
    }

    public void setToMaintBtnAction(EventHandler<ActionEvent> eventHandler) 
    {
        toMaintBtn.setOnAction(eventHandler);
    }

    public void setExitBtnAction(EventHandler<ActionEvent> eventHandler)
    {
        exitBtn.setOnAction(eventHandler);
    }
    private Stage parentWin;
    private Button toSellBtn;
    private Button toMaintBtn;
    private Button exitBtn;
    
}
