package NumPad;

import Boxes.AlertBox;
import StartLib.AppModel;
import VMLib.VMachineModelPaneView;
import VMSell.DispensedItemView;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class NumPaneController 
{
    public NumPaneController(AppModel appModel,
                             NumPaneView numPaneView, 
                             VMachineModelPaneView vMachineModelPaneView,
                             DispensedItemView dispensedItemView)
    {
        this.numPaneView = numPaneView;
        this.vMachineModelPaneView = vMachineModelPaneView;
        this.dispensedItemView = dispensedItemView;
        this.appModel = appModel;
        TextField numField = this.numPaneView.getNumField();
        Button[] numButtons = this.numPaneView.getNumButtons();
        AlertBox alertBox = new AlertBox();
        


        for(int i = 0; i < 10; i++)
        {

            final int trackedIndex = i;
            
            this.numPaneView.setNumButtonAction(i, e->
            {
                if(numField.getText().length() < 3)
                    numField.setText(numField.getText() + numButtons[trackedIndex].getText());
            });
        }

        this.numPaneView.setClrButtonAction(e->
        {
            numField.setText("");
        });


        this.numPaneView.setDelButtonAction(e->
        {
            String text;
            text = numField.getText();
            numField.setText(text.substring(0, text.length()-1));
        });
        this.numPaneView.setEnterButtonAction(e->{
            int num;
            StackPane item;
            if(!numField.getText().isEmpty())
            {
                num = Integer.parseInt(numField.getText());
                this.appModel.addToOrder(num-1, 1);
                
                item = this.vMachineModelPaneView.getItemContainer(this.appModel.findSlotNameInVM(num-1));
                this.dispensedItemView.addItemSelected(item);
            }
        });
    }
    private AppModel appModel;
    private VMachineModelPaneView vMachineModelPaneView;
    private DispensedItemView dispensedItemView;
    private NumPaneView numPaneView;
    
}
