package NumPad;

import java.text.DecimalFormat;
import java.util.ArrayList;

import DenomLib.Denomination;
import Models.Money;
import StartLib.AppController;
import StartLib.AppModel;
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
        this.appModel = appController.getAppModel();
        this.moneyInputHistory = new ArrayList<Double>();
        this.moneyNameHistory = new ArrayList<String>();



        DecimalFormat df = new DecimalFormat("0.00");
        Button[] denomBtns = denomNumPadView.getNumButtons();
        TextField numField = denomNumPadView.getNumField();
        Stage stage = appController.getPrimaryWindow();

        for(int i = 0; i < 13; i++)
        {
            final int trackedIndex = i;

            this.denomNumPadView.setNumButtonAction(i, e->
            {

                double num;
                double inputtedBtnVal;

                String text = numField.getText();
                String denomNumText = denomBtns[trackedIndex].getText();
                String denomName;
        
                


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
                System.out.println("Money inputted: " + inputtedBtnVal);
                denomName = Denomination.fromValue(inputtedBtnVal);
                System.out.println("Money inputted: " + denomName);
                if(num != -1)
                {
                    num += inputtedBtnVal;
                    moneyInputHistory.add(inputtedBtnVal);
                    moneyNameHistory.add(denomName);
                    numField.setText(df.format(num));

                    this.dispensedItemView.addToPaymentView(denomName);
                    this.appModel.addToPayment(denomName);
                }   

                
            });
        }

        this.denomNumPadView.setClrButtonAction(e->
        {

            resetForm();
        });

        this.denomNumPadView.setDelButtonAction(e->
        {
            double num;
            double previousInputVal;
            String previousInputName;
        
            
            String text = numField.getText();
            System.out.println(moneyInputHistory);
            System.out.println(moneyNameHistory);
            if(!moneyInputHistory.isEmpty() && !moneyNameHistory.isEmpty())
            {
                previousInputVal = moneyInputHistory.get(moneyInputHistory.size()-1);
                previousInputName = moneyNameHistory.get(moneyNameHistory.size()-1);
                this.appModel.subToPayment(previousInputName);
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
        this.denomNumPadView.setEnterButtonAction(e->{
            
        });

    }
    public void resetForm()
    {
        TextField numField = denomNumPadView.getNumField();
        numField.setText("");
        if(!moneyInputHistory.isEmpty())
        {
            for(String moneyInput : moneyNameHistory)
            {
                this.appModel.subToPayment(moneyInput);
                this.dispensedItemView.subToPaymentView(moneyInput);

            }
        }
        moneyNameHistory.clear();
        moneyInputHistory.clear();
        numField.setText("");
        
    }
    private AppModel appModel;
    private ArrayList<Double> moneyInputHistory;
    private ArrayList<String> moneyNameHistory;
    private DispensedItemView dispensedItemView;
    private DenomNumPadView denomNumPadView;
}
