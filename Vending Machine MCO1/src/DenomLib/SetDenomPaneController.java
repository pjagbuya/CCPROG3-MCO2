package DenomLib;

import MaintenanceLib.MaintSelectView;
import MaintenanceLib.MaintenanceReplenishCollectView;
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
        this.denomSetSections = new DenomSetSection[13];
        this.denomPaneView = denomPaneView;

   
        denomSetSections = this.denomPaneView.getDenomSetSections();

        for(int i = 0; i< 13; i++)
        {  
            // tracks the position of which where to change
            int trackedIndex = i;


            // Erase zero upon focus
            this.denomPaneView.setTxtFieldFocusListener(trackedIndex, (observable, oldValue, newValue) -> {


                
                // When there is change of focus to occur, delete current entry values
                if (newValue) {

                    if(denomSetSections[trackedIndex].getTextField().getText().length()> 0)
                        denomSetSections[trackedIndex].getTextField().setText("");
                    
                    else if(Integer.parseInt(denomSetSections[trackedIndex].getTextField().getText()) == 0)
                    {
                        denomSetSections[trackedIndex].getTextField().setText("");
                    }
                        
         
                    
                }
                else
                {
                    // Make text field by default zero
                     if(denomSetSections[trackedIndex].getTextField().getText().isEmpty())
                    {
                        denomSetSections[trackedIndex].getTextField().setText("0");
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


                    if(denomSetSections[trackedIndex].getTextField().getText().length()==0)
                    {
                        denomSetSections[trackedIndex].getTextField().setText("0");

                    }
                    
                    if(trackedIndex < 12)
                        denomSetSections[trackedIndex+1].getTextField().requestFocus();

                });
            }




            this.denomPaneView.setTxtFieldFilter(trackedIndex, KeyEvent.KEY_TYPED, event->{
                if (!event.getCharacter().matches("\\d")) {  // "\\d" matches any digit, equivalent to [0-9]
                event.consume();  
                }
            });
 

            this.denomPaneView.setActionEventAddBtn(trackedIndex, event ->{

                if(denomSetSections[trackedIndex].getTextField().getText().length() > 0)
                    denomSetSections[trackedIndex].getTextField().setText(Integer.parseInt(denomSetSections[trackedIndex].getTextField().getText())+1 +"");
                
            });
            
            this.denomPaneView.setActionEventSubBtn(trackedIndex, event ->{

 
                int num;

                
                if(denomSetSections[trackedIndex].getTextField().getText().length() > 0)
                {
                    num = Integer.parseInt(denomSetSections[trackedIndex].getTextField().getText());
                    if(num > 0)
                        denomSetSections[trackedIndex].getTextField().setText( num-1 + "");
                }
                    
                
            });




                

        }


        


    }
    public SetDenomPaneController(SetDenomPaneView denomPaneView, MaintenanceReplenishCollectView maintenanceReplenishCollectView)
    {
        this.denomSetSections = new DenomSetSection[13];
        this.denomPaneView = denomPaneView;


        denomSetSections = this.denomPaneView.getDenomSetSections();

        for(int i = 0; i< 13; i++)
        {  
            // tracks the position of which where to change
            int trackedIndex = i;


            // Erase zero upon focus
            this.denomPaneView.setTxtFieldFocusListener(trackedIndex, (observable, oldValue, newValue) -> {


                
                // When there is change of focus to occur, delete current entry values
                if (newValue) {

                    if(denomSetSections[trackedIndex].getTextField().getText().length()> 0)
                        denomSetSections[trackedIndex].getTextField().setText("");
                    
                    else if(Integer.parseInt(denomSetSections[trackedIndex].getTextField().getText()) == 0)
                    {
                        denomSetSections[trackedIndex].getTextField().setText("");
                    }
                        
         
                    
                }
                else
                {
                    // Make text field by default zero
                     if(denomSetSections[trackedIndex].getTextField().getText().isEmpty())
                    {
                        denomSetSections[trackedIndex].getTextField().setText("0");
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


                    if(denomSetSections[trackedIndex].getTextField().getText().length()==0)
                    {
                        denomSetSections[trackedIndex].getTextField().setText("0");

                    }
                    
                    if(trackedIndex < 12)
                        denomSetSections[trackedIndex+1].getTextField().requestFocus();

                });
            }




            this.denomPaneView.setTxtFieldFilter(trackedIndex, KeyEvent.KEY_TYPED, event->{
                if (!event.getCharacter().matches("\\d")) {  // "\\d" matches any digit, equivalent to [0-9]
                event.consume();  
                }
            });
 

            this.denomPaneView.setActionEventAddBtn(trackedIndex, event ->{

                if(denomSetSections[trackedIndex].getTextField().getText().length() > 0)
                {
                    denomSetSections[trackedIndex].getTextField().setText(Integer.parseInt(denomSetSections[trackedIndex].getTextField().getText())+1 +"");
                    maintenanceReplenishCollectView.updateCountLabel(denomSetSections[trackedIndex].getMoneyNameLabel().getText());

                }
            });
            
            this.denomPaneView.setActionEventSubBtn(trackedIndex, event ->{

 
                int num;

                
                if(denomSetSections[trackedIndex].getTextField().getText().length() > 0)
                {
                    num = Integer.parseInt(denomSetSections[trackedIndex].getTextField().getText());
                    if(num > 0)
                    {
                       denomSetSections[trackedIndex].getTextField().setText( num-1 + "");
                       maintenanceReplenishCollectView.subCountLabel(denomSetSections[trackedIndex].getMoneyNameLabel().getText());

                    }
                        
                }
                    
                
            });      
        }
    }
    public void resetForm() {
        for(int i = 0; i< 13; i++)
        {  
            denomSetSections[i].getTextField().setText("0"); 
        }

    }
    private DenomSetSection[] denomSetSections;
    private SetDenomPaneView denomPaneView;
}
