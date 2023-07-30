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

        SetupVMTopBarView mainTopBar = setupVMPopUpView.getSetupVMTopBar();
        SetupVMTopBarView hiddenTopBar = setupVMPopUpView.getSetupVMHidTopBar();

        slotCapField = setupVMPopUpView.getSlotCapField();
        itemCapField = setupVMPopUpView.getItemCapField();
        nameField = setupVMPopUpView.getNameField();
        

        this.setupVMPopUpView = setupVMPopUpView;


        

        // When exit close window
        mainTopBar.setExitBtnListener(e->
        {
            setupVMPopUpView.close();
        });

        // When press to add vending machine, change popup scene
        this.setupVMPopUpView.setBigAddButtonAction(e->{
            
            
            this.setupVMPopUpView.changePopUpSceneVMType();


        });

        setAddAndSubBtnAction();
        setTextFieldBehavior();


        // Menu bars set action
        mainTopBar.setExitBtnListener(e->
        {
            resetForm();
            setupVMPopUpView.close();
        });

        hiddenTopBar.setExitBtnListener(e->
        {
            setupVMPopUpView.changePopUpSceneMain();
        });


        hiddenTopBar.setFinishBtnListener(e->
        {     

            if(nameField.getText().length() != 0)
            {
                setupVMPopUpView.changeWindowScene();
                setupVMPopUpView.changePopUpSceneMain();
                setupVMPopUpView.close();
                resetForm();
            }
            else
            {
                
                setupVMPopUpView.raiseAlert("Missing Inputs", "Please add a name to your Vending Machine", 12);
                e.consume();
            }
                
        });


    }
    private void setAddAndSubBtnAction()
    {
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

    }
    private void setTextFieldBehavior()
    {
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
                        this.setupVMPopUpView.raiseAlert("Invalid Input", "Please use only at minimum 10", 12);
                    }
                } catch (NumberFormatException e) {
                    itemCapField.setText("10");
                    this.setupVMPopUpView.raiseAlert("Invalid Input", "Please enter a valid number", 12);
                }
            }
        });
        
        this.setupVMPopUpView.setSlotTextChangeListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(newValue);
                    if (value < 8) {
                        slotCapField.setText("8");
                        this.setupVMPopUpView.raiseAlert("Invalid Input", "Please use only at minimum 8", 12);
                    }
                } catch (NumberFormatException e) {
                    slotCapField.setText("8");
                    this.setupVMPopUpView.raiseAlert("Invalid Input", "Please enter a valid number", 12);
                }
            }
        });
    }
    public void resetForm() 
    {
        slotCapField.setText("8");
        itemCapField.setText("10");
        setupVMPopUpView.getRegRadButton().setSelected(true);
        nameField.setText("");

    }


    private TextField slotCapField;
    private TextField itemCapField;
    private TextField nameField;
    private SetupVMPopUpView setupVMPopUpView;
    private SetupVMTopBarView setupVMTopBarView;



}