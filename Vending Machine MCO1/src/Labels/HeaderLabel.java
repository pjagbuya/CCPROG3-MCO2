package Labels;





import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
/**
 * This class is label with custom sized and fixed font family
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
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
