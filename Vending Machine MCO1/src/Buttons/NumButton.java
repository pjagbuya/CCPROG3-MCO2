package Buttons;

import CustomSetup.CustomButton;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class NumButton extends CustomButton
{
    public NumButton(String text, int size)
    {
        super(text, "Courier New", size);
        this.setPrefSize(75, 75);
        DropShadow shadow = new DropShadow();
          // Adding the shadow when the mouse cursor is on
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            this.setEffect(shadow);
            setBorderColor("#97FEED");
;
        });

        // Removing the shadow when the mouse cursor is off
        this.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            this.setEffect(null);
            setBorderColor("#0B666A");

        });      

    }
    
    public NumButton(String text)
    {
        this(text, 10);

    }


}
