package VMLib;

import ItemSelectLib.SetItemPaneView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class VMachineModelPaneController {

     public VMachineModelPaneController(SetItemPaneView setItemPaneView, VMachineModelPaneView vMachineModelPaneView)
     {
         this.setItemPaneView = setItemPaneView;
         this.vMachineModelPaneView = vMachineModelPaneView;
         for (int i = 0; i < setItemPaneView.getMaxItems(); i++)
         { 
             TextField[] stockFields;
             Image[] images;

             stockFields = this.setItemPaneView.getStockFields();
             images = setItemPaneView.getImages();
             if(stockFields[i].getText().length() > 0 && Integer.parseInt(stockFields[i].getText()) > 0);
         }
     }
     private SetItemPaneView setItemPaneView;
     private VMachineModelPaneView vMachineModelPaneView;
 }
