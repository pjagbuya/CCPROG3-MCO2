package CustomSetup;

import ItemSelectLib.ItemSectionPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.binding.Bindings;
public class CustomImageView extends ImageView
{
    public CustomImageView(Image image, ItemSectionPane itemSectionPane)
    {
        super(image);


        this.fitHeightProperty().bind(Bindings.min(itemSectionPane.heightProperty().multiply(0.4), 70));
        this.fitWidthProperty().bind(Bindings.min(itemSectionPane.widthProperty().multiply(0.4), 70));


    }
    
}
