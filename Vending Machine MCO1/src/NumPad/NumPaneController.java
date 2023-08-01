package NumPad;

import Boxes.AlertBoxRep;
import StartLib.AppModel;
import StartLib.AppController;
import VMLib.VMachineModelPaneView;
import VMSell.DispensedItemView;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class NumPaneController 
{
    public NumPaneController(AppController appController,
                             NumPaneView numPaneView, 
                             VMachineModelPaneView vMachineModelPaneView,
                             DispensedItemView dispensedItemView)
    {
        this.numPaneView = numPaneView;
        this.vMachineModelPaneView = vMachineModelPaneView;
        this.dispensedItemView = dispensedItemView;
        this.appModel = appController.getAppModel();
        
        TextField numField = this.numPaneView.getNumField();
        Button[] numButtons = this.numPaneView.getNumButtons();

        


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
            if(text.length()-1 >= 0)
                numField.setText(text.substring(0, text.length()-1));

        });
        this.numPaneView.setEnterButtonAction(e->{
            int num;
            String name;
            StackPane item;
            AlertBoxRep alertBox;
            TextField numFieldInp = this.numPaneView.getNumField();
            String msg = this.appModel.addToRegOrder(Integer.parseInt(numFieldInp.getText()),
                                    1);

            if(msg == null)
            {
                num = Integer.parseInt(numField.getText());
                this.appModel.addToRegOrder(num, 1);
                this.appModel.findSlotNameInVM(num);
                
                item = this.vMachineModelPaneView.getItemContainerCopy(this.appModel.findSlotNameInVM(num-1));
                this.dispensedItemView.addItemSelected(item);
            }
            else if(msg != null)
            {
                alertBox = new AlertBoxRep("ALERT", msg);
            }
            
        });
    }
    private AppModel appModel;
    private VMachineModelPaneView vMachineModelPaneView;
    private DispensedItemView dispensedItemView;
    private NumPaneView numPaneView;
    
}
