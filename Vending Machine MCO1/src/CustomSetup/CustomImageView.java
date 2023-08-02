package CustomSetup;

import ItemSelectLib.ItemSectionPane;
import VMLib.ItemSlotPaneView;
import VMLib.VMachineModelPaneView;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * This abstract class is the custom image view that most items graphics follow the rules in
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class CustomImageView extends ImageView
{
    public CustomImageView(Image image, ItemSectionPane itemSectionPane)
    {
        super(image);


        this.fitHeightProperty().bind(Bindings.min(itemSectionPane.heightProperty().multiply(0.4), 70));
        this.fitWidthProperty().bind(Bindings.min(itemSectionPane.widthProperty().multiply(0.4), 70));


    }

    public CustomImageView(Image image, VMachineModelPaneView vMachineModelPaneView, double sizeFactor)
    {
        super(image);
        this.setFitHeight(300);
        this.setFitWidth(300);
        this.setPreserveRatio(true);
        this.setSmooth(true);
        this.fitHeightProperty().bind(vMachineModelPaneView.heightProperty().multiply(sizeFactor));
        this.fitWidthProperty().bind(vMachineModelPaneView.widthProperty().multiply(sizeFactor));


    }
    
}
