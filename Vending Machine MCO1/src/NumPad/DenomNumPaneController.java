package NumPad;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Models.Money;
import StartLib.AppController;
import VMSell.DispensedItemView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DenomNumPaneController 
{
    
    public DenomNumPaneController(AppController appController,
                                  DenomNumPadView denomNumPadView, 
                                  DispensedItemView dispensedItemView)
    {
        this.denomNumPadView = denomNumPadView;
        this.dispensedItemView = dispensedItemView;
        Money moneyDict = new Money();                          //Put logic to model
        moneyInputHistory = new ArrayList<Double>();
        this.moneyNameHistory = new ArrayList<String>();
        DecimalFormat df = new DecimalFormat("0.00");
        Button[] denomBtns = denomNumPadView.getNumButtons();
        TextField numField = denomNumPadView.getNumField();
        Stage stage = appController.getPrimaryWindow();

        for(int i = 0; i < 13; i++)
        {
            final int trackedIndex = i;

            this.denomNumPadView.setNumButtonAction(i, e->{
                this.dispensedItemView.displayDispensed();
                // stage.setWidth(1200);
                // stage.setHeight(1200);
                stage.centerOnScreen();
                double num;
                double inputtedBtnVal;

                String text = numField.getText();
                String denomNumText = denomBtns[trackedIndex].getText();
                if(denomNumText.contains(String.valueOf('B')) )

                    inputtedBtnVal = 20.00;
                else if (denomNumText.contains(String.valueOf('C')) )
                    inputtedBtnVal = 20.001;

                else
                    inputtedBtnVal = Double.parseDouble(denomNumText);


                try
                {
                    
                    num = Double.parseDouble(text);
                }
                catch(NumberFormatException err)
                {
                    if(text.isEmpty())
                        num = 0;
                    else
                        num = -1;
                }

                if(num != -1)
                {
                    num += inputtedBtnVal;
                    moneyInputHistory.add(inputtedBtnVal);
                    moneyNameHistory.add(denomNumText);
                    numField.setText(df.format(num));
                    this.dispensedItemView.addToPaymentView(denomNumText);

                }   

                
            });
        }

        this.denomNumPadView.setClrButtonAction(e->
        {
            numField.setText("");
        });

        this.denomNumPadView.setDelButtonAction(e->
        {
            double num;
            double previousInputVal;
            String previousInputName;
        
            
            String text = numField.getText();

            if(!moneyInputHistory.isEmpty() && !moneyNameHistory.isEmpty())
            {
                previousInputVal = moneyInputHistory.get(moneyInputHistory.size()-1);
                previousInputName = moneyNameHistory.get(moneyNameHistory.size()-1);
            }
            else
            {
                return;
            }
                
            try
            {
                num = Double.parseDouble(text);
            }
            catch(NumberFormatException err)
            {
                num = -1;
            }


            if(num != -1 && previousInputVal != -1)
            {
                this.dispensedItemView.subToPaymentView(previousInputName);
                num -= previousInputVal;

                moneyInputHistory.remove(moneyInputHistory.size()-1);
                moneyNameHistory.remove(moneyNameHistory.size()-1);
                numField.setText(df.format(num));
            }


        });

    }
    private ArrayList<Double> moneyInputHistory;
    private ArrayList<String> moneyNameHistory;
    private DispensedItemView dispensedItemView;
    private DenomNumPadView denomNumPadView;
}
