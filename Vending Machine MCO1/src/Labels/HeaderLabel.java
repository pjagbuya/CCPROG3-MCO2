package Labels;



import org.xml.sax.ext.LexicalHandler;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HeaderLabel extends Label
{
    public HeaderLabel(String str, int size)
    {
        super(str);

        
        Font headerBoldLabel = Font.font("Georgia", FontWeight.BOLD, size);
        this.setFont(headerBoldLabel);
        this.setStyle("-fx-text-fill: " +COLOR+ ";");
    }
    public HeaderLabel(String str)
    {
        this(str, 16);

    }

    private static final String COLOR = "#97FEED";
}
