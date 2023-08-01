package Buttons;

import java.util.NoSuchElementException;

import CustomSetup.CustomButton;
import javafx.scene.text.Font;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.image.Image;

import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class MenuButton extends CustomButton {
    private String selfLabel;

    public MenuButton(String s, int size) 
    {

        

        super(s);  // This calls the constructor of the superclass, Button, passing the label.
        super.setTextSize(size);


    }
    public MenuButton(String s) 
    {

        

        this(s, 20);


    }

}