package CustomSetup;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public abstract class CustomButton extends Button
{
    public CustomButton(String text, String font, int size)
    {
        super(text);
        Font arial = new Font(font, size);
        DropShadow shadow = new DropShadow();
        this.defaultStyle = " -fx-text-fill: #97FEED; -fx-base: #071952; -fx-border-color: #0B666A;" +
                            "-fx-border-width: 3px;" +
                            "-fx-border-style: solid;";
        this.setText(text);
        this.setFont(arial);
        
        // Normal style
        this.setStyle(defaultStyle);
        


        
        // Adding the shadow when the mouse cursor is on
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            this.setEffect(shadow);
            setBorderColor("#97FEED");
            setBorderWidth(2);
        });

        // Removing the shadow when the mouse cursor is off
        this.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            this.setEffect(null);
            setBorderColor("#0B666A");
            setBorderWidth(1);

        });
    }

    public CustomButton(String text, int size)
    {
        this(text, "Arial", size);
    }
    public CustomButton(String text)
    {
        this(text, "Arial", 16);
    }

    public void setTextWeight(String type)
    {   

        String propertyCSS = "-fx-font-weight:";
        String replacement = propertyCSS+type+";";

        replaceProperty(propertyCSS, replacement);
        
    }
    public void setTextSize(int num)
    {
        String propertyCSS = "-fx-font-size:";
        String replacement = propertyCSS+num+"pt"+";";

        replaceProperty(propertyCSS, replacement);

    }
    public void setTextFamily(String family)
    {
        String propertyCSS = "-fx-font-family:";
        String replacement = propertyCSS+family+";";

        replaceProperty(propertyCSS, replacement);
        
    }   
    public void setBorderColor(String hexVal)
    {
        String propertyCSS = "-fx-border-color:";
        String replacement = propertyCSS+hexVal+";";

        replaceProperty(propertyCSS, replacement);
        
    }

    public void setBorderWidth(int num)
    {
        String propertyCSS = "-fx-border-width:";
        String replacement = propertyCSS+num+"px"+";";

        replaceProperty(propertyCSS, replacement);
        
    }



    private void replaceProperty(String propertyCSS, String replacement)
    {
        if(defaultStyle.indexOf(propertyCSS) != -1)
        {
            defaultStyle = defaultStyle.replace(defaultStyle.substring(defaultStyle.indexOf(propertyCSS), 
                                                                       defaultStyle.indexOf(";", defaultStyle.indexOf(propertyCSS))+1 ), 
                                                replacement  );
        }     
        else
            defaultStyle += replacement;


        this.setStyle(defaultStyle);
    }



    private String defaultStyle;
}
