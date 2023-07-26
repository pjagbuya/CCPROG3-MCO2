package DenomLib;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InteractibleBtn extends Button{
    public InteractibleBtn(String numText)
    {
        super(numText);
        String colorBg = "#071952";
        

        String colorLighter = "#35A29F";
        String colorLightest = "#97FEED";

        Font headerBoldLabel = Font.font("Helvetica", FontWeight.BOLD, 16);
        DropShadow shadow = new DropShadow();
        shadow.setRadius(100);
        shadow.setColor(Color.color(151/255.0, 254/255.0, 237/255.0));
        this.setText(numText);
        this.setFont(headerBoldLabel);

        // Normal style
        this.setStyle("-fx-min-width: 50px; -fx-min-height: 50px;"+
                      " -fx-text-fill: #97FEED;" +
                      "-fx-background-color: " + colorBg + "; " +
                      "-fx-border-color: " + colorLightest + ";" +
                      "-fx-border-width: 3px;" +
                      "-fx-border-style: solid;"+
                      "-fx-border-radius: 50%");
        


        
        // Adding the shadow when the mouse cursor is on
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            this.setEffect(shadow);

            this.setStyle("-fx-min-width: 50px; -fx-min-height: 50px; "+
                        " -fx-text-fill: #97FEED;" +
                        "-fx-background-color: " + colorBg + "; " +
                        "-fx-border-color: " + colorLightest + ";" +
                        "-fx-border-width: 6px;" +
                        "-fx-border-style: solid;"+
                        "-fx-border-radius: 50%");

        });

        // Removing the shadow when the mouse cursor is off
        this.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            this.setEffect(null);

            
            this.setStyle("-fx-min-width: 50px; -fx-min-height: 50px; "+
                        " -fx-text-fill: #97FEED;" +
                        "-fx-background-color: " + colorBg + "; " +
                        "-fx-border-color: " + colorLightest+ ";" +
                        "-fx-border-width: 3px;" +
                        "-fx-border-style: solid;"+
                        "-fx-border-radius: 50%");

        });

    }
}
