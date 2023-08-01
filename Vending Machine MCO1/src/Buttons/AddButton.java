package Buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AddButton extends Button{
    public AddButton(int imgWidth, int imgHeight)
    {
        Image plusImg = new Image("Pics/add-button.png");

        
        ImageView plusImageView;


        plusImageView = new ImageView(plusImg);
        plusImageView.setFitWidth(imgWidth);

        plusImageView.setFitHeight(imgHeight);


        this.setGraphic(plusImageView);

    }
    public AddButton(Stage parentWin, int imgWidth, int imgHeight)
    {
        Image plusImg = new Image("Pics/add-button.png");

        
        ImageView plusImageView;


        plusImageView = new ImageView(plusImg);
        plusImageView.setFitWidth(imgWidth);

        plusImageView.setFitHeight(imgHeight);

        plusImageView.fitHeightProperty().bind(parentWin.heightProperty().multiply(0.04));
        plusImageView.fitWidthProperty().bind(parentWin.widthProperty().multiply(0.025));

        this.setGraphic(plusImageView);

    }
    public AddButton(Stage parentWin)
    {
        this(parentWin, 50,50);
    }
}
