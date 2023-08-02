package Buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
/**
 * This is a sub sign button graphic
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class SubButton extends Button{
    public SubButton(int imgWidth, int imgHeight)
    {
        Image subImg = new Image("Pics/minus-button.png");

        
        ImageView subImageView;

        
        subImageView = new ImageView(subImg);
        subImageView.setFitWidth(imgWidth);
        subImageView.setFitHeight(imgHeight);
        this.setGraphic(subImageView);

    }
    public SubButton(Stage parentWin, int imgWidth, int imgHeight)
    {
        Image subImg = new Image("Pics/minus-button.png");

        
        ImageView subImageView;

        
        subImageView = new ImageView(subImg);
        subImageView.setFitWidth(imgWidth);
        subImageView.setFitHeight(imgHeight);
        subImageView.fitHeightProperty().bind(parentWin.heightProperty().multiply(0.04));
        subImageView.fitWidthProperty().bind(parentWin.widthProperty().multiply(0.025));
        this.setGraphic(subImageView);

    }
    public SubButton(Stage parentWin)
    {
        this(parentWin, 50,50);
    }
}
