package StartLib;

import Boxes.AlertBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class SetupVMPopUpController 
{
    
    public SetupVMPopUpController(SetupVMPopUpView setupVMPopUpView)
    {


        slotCapField = setupVMPopUpView.getSlotCapField();
        itemCapField = setupVMPopUpView.getItemCapField();
        nameField = setupVMPopUpView.getNameField();

        SetupVMTopBarView mainTopBar = setupVMPopUpView.getSetupVMTopBar();
        SetupVMTopBarView hiddenTopBar = setupVMPopUpView.getSetupVMHidTopBar();

        AlertBox alertBox = new AlertBox();

        

        this.setupVMPopUpView = setupVMPopUpView;

        setupVMTopBarView = setupVMPopUpView.getSetupVMTopBar();
        
        setupVMTopBarView.setExitBtnListener(e->
        {
            setupVMPopUpView.close();
        });

        this.setupVMPopUpView.setBigAddButtonAction(e->{
            
            
            this.setupVMPopUpView.setScene(this.setupVMPopUpView.getVmTypeScene());
            this.setupVMPopUpView.centerOnScreen();

        });

        this.setupVMPopUpView.setItemAddBtnAction(e->
        {
            int numCap;

            numCap = Integer.parseInt(itemCapField.getText());
            itemCapField.setText(numCap+1+"");

            
        });

        this.setupVMPopUpView.setSlotAddBtnAction(e->
        {
            int numCap;

            numCap = Integer.parseInt(slotCapField.getText());
            slotCapField.setText(numCap+1+"");

            
        });


        this.setupVMPopUpView.setItemSubBtnAction(e->
        {
            int numCap;
            
            numCap = Integer.parseInt(itemCapField.getText());

            if(numCap > 10)
            {
                itemCapField.setText(numCap-1+"");
            }
            
        });
        this.setupVMPopUpView.setSlotSubBtnAction(e->
        {
            int numCap;
            
            numCap = Integer.parseInt(slotCapField.getText());

            if(numCap > 8)
            {
                slotCapField.setText(numCap-1+"");
            }
            
        });

        this.setupVMPopUpView.addTxtFieldsEventFilter(KeyEvent.KEY_TYPED, event ->{
            if(!event.getCharacter().matches("\\d*"))
            {
                event.consume();
            }
        });


        this.setupVMPopUpView.setItemFieldFocusListener((observable, oldValue, newValue) ->{
            if (newValue) {
                itemCapField.setText("");
            }
            else
                slotCapField.setText("10");
        });


        this.setupVMPopUpView.setSlotFieldFocusListener((observable, oldValue, newValue) ->{
            if (newValue) {
                slotCapField.setText("");
            }
            else
                slotCapField.setText("8");
        });




        // Set action on change of text fields

        this.setupVMPopUpView.setItemTextChangeListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(newValue);
                    if (value < 10) {
                        itemCapField.setText("10");
                        alertBox.display("Invalid Input", "Please use only at minimum 10", 12);
                    }
                } catch (NumberFormatException e) {
                    itemCapField.setText("10");
                    alertBox.display("Invalid Input", "Please enter a valid number", 12);
                }
            }
        });
        
        this.setupVMPopUpView.setSlotTextChangeListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(newValue);
                    if (value < 8) {
                        slotCapField.setText("8");
                        alertBox.display("Invalid Input", "Please use only at minimum 8", 12);
                    }
                } catch (NumberFormatException e) {
                    slotCapField.setText("8");
                    alertBox.display("Invalid Input", "Please enter a valid number", 12);
                }
            }
        });



        // Menu bars set action
        mainTopBar.setExitBtnListener(e->
        {
            resetForm();
            setupVMPopUpView.close();
        });

        hiddenTopBar.setExitBtnListener(e->{
            setupVMPopUpView.setScene(setupVMPopUpView.getPrevScene());
        });


        hiddenTopBar.setFinishBtnListener(e->
        {
            if(nameField.getText().length() != 0)
            {
                resetForm();
                hiddenTopBar.getParentWin().setScene(hiddenTopBar.getTargetScene());
                hiddenTopBar.getParentWin().centerOnScreen();
                setupVMPopUpView.setScene(setupVMPopUpView.getVmMainScene());
                setupVMPopUpView.close();
            }
            else
            {
                alertBox.display("Missing Inputs", "Please add a name to your Vending Machine", 12);
            }
                
        });


    }

    public void resetForm() 
    {
        slotCapField.setText("8");
        itemCapField.setText("10");
        nameField.setText("");

    }


    private TextField slotCapField;
    private TextField itemCapField;
    private TextField nameField;
    private SetupVMPopUpView setupVMPopUpView;
    private SetupVMTopBarView setupVMTopBarView;



}
