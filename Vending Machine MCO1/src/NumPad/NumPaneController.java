package NumPad;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NumPaneController 
{
    public NumPaneController(NumPaneView numPaneView)
    {
        this.numPaneView = numPaneView;

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
            numField.setText(text.substring(0, text.length()-1));
        });
    }

    private NumPaneView numPaneView;
    
}
