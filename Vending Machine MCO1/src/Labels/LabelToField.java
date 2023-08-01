package Labels;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LabelToField extends Label{
    
    public LabelToField(String str, int size)
    {
        super(str);

        
        Font headerBoldLabel = Font.font("Helvetica", FontWeight.BOLD, size);
        this.setFont(headerBoldLabel);
        this.setStyle("-fx-text-fill: " +COLOR+ ";");
    }
    public LabelToField(String str)
    {
        this(str, 16);

    }

    private static final String COLOR = "#97FEED";

}
