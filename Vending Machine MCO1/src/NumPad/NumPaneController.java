package NumPad;

import Boxes.AlertBoxRep;
import StartLib.AppModel;
import StartLib.AppController;
import VMLib.VMachineModelPaneView;
import VMSell.DispensedItemView;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
/**
 * This class controller sets the numpad where the user gets to press and type the slot nums and this class helps display it
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
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
            resetForm();
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
 
            String calories;
            String price;
            StackPane item;
            AlertBoxRep alertBox;
            TextField numFieldInp = this.numPaneView.getNumField();
            String msg;

            if(!numFieldInp.getText().isEmpty())
                msg = this.appModel.addToRegOrder(Integer.parseInt(numFieldInp.getText()),
                                              1);
            else
                msg = "NO INPUTTED SLOT";

            if(msg == null)
            {
                num = Integer.parseInt(numField.getText());
                this.appModel.findSlotNameInVM(num);
                calories = this.appModel.findSlotCaloriesInVM(num);
                price = this.appModel.findSlotPriceInVM(num);
                
                item = this.vMachineModelPaneView.getItemContainerCopy(this.appModel.findSlotNameInVM(num));
                this.dispensedItemView.addItemSelected(item, price, calories);
            }
            else if(msg != null)
            {
                alertBox = new AlertBoxRep("ALERT", msg);
            }
            
        });
    }
    public void resetForm()
    {
        TextField numField = this.numPaneView.getNumField();

        numField.setText("");

        
    }
    private AppModel appModel;
    private VMachineModelPaneView vMachineModelPaneView;
    private DispensedItemView dispensedItemView;
    private NumPaneView numPaneView;
    
}
