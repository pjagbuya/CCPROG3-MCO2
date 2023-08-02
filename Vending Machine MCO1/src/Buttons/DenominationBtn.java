package Buttons;

import java.util.NoSuchElementException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
 

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
// Classes below are used to validate if something contains a number
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
/**
 * This button is set with the graphic for the denomination with some hover mechanisms
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class DenominationBtn extends Button
{
    public DenominationBtn(String numText)
    {
        super(numText);

        String colorBg = "#071952";

        String colorLighter = "#35A29F";
        String colorLightest = "#97FEED";

        Font headerBoldLabel = Font.font("Helvetica", FontWeight.BOLD, 11);
        
        DropShadow shadow = new DropShadow();

        String defaultStyle =  " -fx-text-fill: #97FEED;" +
                                "-fx-background-color: " + colorBg + "; " +
                                "-fx-border-color: " + colorLightest + ";" +
                                "-fx-border-width: 3px;" +
                                "-fx-border-style: solid;"+
                                "-fx-border-radius: 50%;" +
                                "-fx-background-radius: 50%;";




        shadow.setRadius(25);
        shadow.setColor(Color.color(151/255.0, 254/255.0, 237/255.0));
        this.setText(numText);
        this.setFont(headerBoldLabel);
        this.minWidth(60);
        this.minHeight(60);
        // Normal style
        this.setStyle(defaultStyle);
        


        
        // Adding the shadow when the mouse cursor is on
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            this.setEffect(shadow);

            
            this.setStyle( " -fx-text-fill: #97FEED;" +
                            "-fx-background-color: " + colorBg + "; " +
                            "-fx-border-color: " + colorLightest + ";" +
                            "-fx-border-width: 4px;" +
                            "-fx-border-style: solid;"+
                            "-fx-border-radius: 50%;" +
                            "-fx-background-radius: 50%;");

        });
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            this.setEffect(shadow);

            this.setStyle( " -fx-text-fill: #97FEED;" +
                        "-fx-background-color: " + colorLightest + "; " +
                        "-fx-border-color: " + colorLightest + ";" +
                        "-fx-border-width: 4px;" +
                        "-fx-border-style: solid;"+
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;");

        });
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
            this.setEffect(shadow);

            this.setStyle( " -fx-text-fill: #97FEED;" +
                        "-fx-background-color: " + colorBg + "; " +
                        "-fx-border-color: " + colorLightest + ";" +
                        "-fx-border-width: 4px;" +
                        "-fx-border-style: solid;"+
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;");

        });
        // Removing the shadow when the mouse cursor is off
        this.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            this.setEffect(null);

            
            this.setStyle(defaultStyle);

        });

    }


}
