package VMSell;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class DispensedItemView extends HBox
{
    public DispensedItemView()
    {
        GridPane dispensedSectionGridPane = new GridPane();

        this.setVisible(false);
    }

    public void displayDispensed()
    {
        this.setVisible(true);
    }
    
}
