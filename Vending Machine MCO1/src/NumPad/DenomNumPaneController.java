package NumPad;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Models.Money;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class DenomNumPaneController 
{
    
    public DenomNumPaneController(DenomNumPadView denomNumPadView)
    {
        this.denomNumPadView = denomNumPadView;

        Money moneyDict = new Money();                          //Put logic to model
        moneyInputHistory = new ArrayList<Double>();

        DecimalFormat df = new DecimalFormat("0.00");
        Button[] denomBtns = denomNumPadView.getNumButtons();
        TextField numField = denomNumPadView.getNumField();

        for(int i = 0; i < 13; i++)
        {
            final int trackedIndex = i;

            this.denomNumPadView.setNumButtonAction(i, e->{
                double num;
                double inputtedBtnVal;

                String text = numField.getText();
                String denomNumText = denomBtns[trackedIndex].getText();
                if(denomNumText.contains(String.valueOf('B')) )

                    inputtedBtnVal = Double.parseDouble(denomNumText.substring(0, denomNumText.indexOf('B')));
                else if (denomNumText.contains(String.valueOf('C')) )
                    inputtedBtnVal = Double.parseDouble(denomNumText.substring(0, denomNumText.indexOf('C')));

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
                    numField.setText(df.format(num));
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

                
                String text = numField.getText();

                if(moneyInputHistory.size()-1  > 0)
                    previousInputVal = moneyInputHistory.get(moneyInputHistory.size()-1);
                else
                    previousInputVal = moneyInputHistory.get(0);
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
                    num -= previousInputVal;
                    moneyInputHistory.remove(moneyInputHistory.size()-1);
                    numField.setText(df.format(num));
                }


        });

    }
    private ArrayList<Double> moneyInputHistory;
    private DenomNumPadView denomNumPadView;
}
