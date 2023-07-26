package StartLib;


import Buttons.MenuButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppView extends BorderPane{
    public AppView(Stage parentWin)
    {
        String colorBg = "#071952";
        VBox btnSection = new VBox(20);

        this.parentWin = parentWin;
        btnSection.setAlignment(Pos.CENTER);
        btnSection.getChildren().addAll(BTN_CREATE, BTN_TESTF, BTN_EXIT);

        this.setCenter(btnSection);
        this.setStyle("-fx-base: " + colorBg+ ";");
    }

    public static MenuButton getBtnCreate() {
        return BTN_CREATE;
    }
    public static MenuButton getBtnExit() {
        return BTN_EXIT;
    }
    public static MenuButton getBtnTestf() {
        return BTN_TESTF;
    }

    public void setBtnCreateRegAction(EventHandler<ActionEvent> eventHandler) {
        BTN_CREATE.setOnAction(eventHandler);
    }

    public void setBtnExitAction(EventHandler<ActionEvent> eventHandler)
    {
        BTN_EXIT.setOnAction(eventHandler);
    }
    public Stage getParentWin() {
        return parentWin;
    }

    private Stage parentWin;
    private static final MenuButton BTN_CREATE = new MenuButton("Create a Vending Machine");
    private static final MenuButton BTN_TESTF = new MenuButton("Test Features of a Vending Machine");
    private static final MenuButton BTN_EXIT = new MenuButton("Exit");




}
