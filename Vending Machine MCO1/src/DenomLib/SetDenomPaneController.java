package DenomLib;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SetDenomPaneController {
    public SetDenomPaneController(SetDenomPaneView denomPaneView)
    {
        this.denomPaneView = denomPaneView;
        this.denomModel = new DenominationModel();
        

        for(int i = 0; i< 13; i++)
        {  
            // final modifier for variables help the changeListener determine what reference to getLabels/TextFields/TextFieldValues
            final int trackedIndex = i;
            this.subButtons = this.denomPaneView.getSubButtons();
            this.addButtons = this.denomPaneView.getAddButtons();
            this.textFields = this.denomPaneView.getTextFields();

            // Erase zero upon focus
            this.denomPaneView.setTxtFieldFocusListener(trackedIndex, (observable, oldValue, newValue) -> {

                textFields = this.denomPaneView.getTextFields();
                
                // When there is change of focus to occur, delete current entry values
                if (newValue) {

                    if(textFields[trackedIndex].getText().length()> 0)
                        textFields[trackedIndex].setText("");
                    
                    else if(Integer.parseInt(textFields[trackedIndex].getText()) == 0)
                    {
                        textFields[trackedIndex].setText("");
                    }
                        
         
                    
                }
                else
                {
                    // Make text field by default zero
                     if(textFields[trackedIndex].getText().isEmpty())
                    {
                        textFields[trackedIndex].setText("0");
                    }
                }

            });

            if(trackedIndex+1 == 13)
            {
                this.denomPaneView.setActionEventTxtField(i, e ->{

                   this.denomPaneView.requestFocus();


                });
            }
            else
            {
                this.denomPaneView.setActionEventTxtField(i, e ->{


                    textFields = this.denomPaneView.getTextFields();
                    
                    if(textFields[trackedIndex].getText().length()==0)
                    {
                        textFields[trackedIndex].setText("0");

                    }
                    
   
                    textFields[trackedIndex+1].requestFocus();

                });
            }




            this.denomPaneView.setTxtFieldFilter(trackedIndex, KeyEvent.KEY_TYPED, event->{
                if (!event.getCharacter().matches("\\d")) {  // "\\d" matches any digit, equivalent to [0-9]
                event.consume();  
                }
            });
 

            this.denomPaneView.setActionEventAddBtn(trackedIndex, event ->{
                textFields = this.denomPaneView.getTextFields();
                if(textFields[trackedIndex].getText().length() > 0)
                    textFields[trackedIndex].setText(Integer.parseInt(textFields[trackedIndex].getText())+1 +"");
                
            });
            
            this.denomPaneView.setActionEventSubBtn(trackedIndex, event ->{

                textFields = this.denomPaneView.getTextFields();
                int num;

                
                if(textFields[trackedIndex].getText().length() > 0)
                {
                    num = Integer.parseInt(textFields[trackedIndex].getText());
                    if(num > 0)
                        textFields[trackedIndex].setText( num-1 + "");
                }
                    
                
            });




                

        }

        


    }

    public void resetForm() {
        for(int i = 0; i< 13; i++)
        {  
            textFields[i].setText("0"); 
        }

    }
    private Button[] addButtons;
    private Button[] subButtons;
    private TextField[] textFields;
    private DenominationModel denomModel;
    private SetDenomPaneView denomPaneView;
}
