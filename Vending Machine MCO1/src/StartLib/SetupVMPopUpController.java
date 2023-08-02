package StartLib;

import Boxes.AlertBox;
import ItemSelectLib.SetItemPaneView;
import VMLib.VMachineModelPaneView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
/**
 * This class is a controller that relays the data of what the user wants his/her vending machine to be
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class SetupVMPopUpController 
{
    
    public SetupVMPopUpController(SetupVMPopUpView setupVMPopUpView,
                                  CreateRegTopBarView createRegTopBarView,
                                  CreateRegMenu regMenu,
                                  SetItemPaneView setItemPaneView,
                                  AppModel appModel)
    {

        SetupVMTopBarView mainTopBar = setupVMPopUpView.getSetupVMTopBar();
        SetupVMTopBarView hiddenTopBar = setupVMPopUpView.getSetupVMHidTopBar();

        slotCapField = setupVMPopUpView.getSlotCapField();
        itemCapField = setupVMPopUpView.getItemCapField();
        nameField = setupVMPopUpView.getNameField();
        
        this.regMenu = regMenu;
        this.setupVMPopUpView = setupVMPopUpView;
        this.createRegTopBarView = createRegTopBarView;
        this.setItemPaneView = setItemPaneView;
        this.vMachineModelPaneView = regMenu.getvMachineModelPaneView();

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
            selectedRadBtn = (RadioButton) setupVMPopUpView.getRadButtons().getSelectedToggle();
            if(nameField.getText().length() != 0)
            {
 
                appModel.addVM( selectedRadBtn.getText(),
                                nameField.getText(), 
                                Integer.parseInt(slotCapField.getText()),
                                Integer.parseInt(itemCapField.getText()));
                
                createRegTopBarView.setNameTextField(nameField.getText());


                this.vMachineModelPaneView.setMaxSlotVMView(Integer.parseInt(slotCapField.getText()));
                this.vMachineModelPaneView.setUpVendMachView(appModel.getVendingMachine());

                setupVMPopUpView.updateVMList(appModel.getVendingMachine());
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
            if(!itemCapField.getText().isEmpty())
            {
                int numCap;

                numCap = Integer.parseInt(itemCapField.getText());
                itemCapField.setText(numCap+1+"");
            }


            
        });

        this.setupVMPopUpView.setSlotAddBtnAction(e->
        {
            int numCap;
            if(!slotCapField.getText().isEmpty())
            {
                numCap = Integer.parseInt(slotCapField.getText());
                slotCapField.setText(numCap+1+"");

            }
        });


        this.setupVMPopUpView.setItemSubBtnAction(e->
        {
            int numCap;
            if(!itemCapField.getText().isEmpty())
            {
                numCap = Integer.parseInt(itemCapField.getText());

                if(numCap > 10)
                {
                    itemCapField.setText(numCap-1+"");
                }      
            }      

            
        });
        this.setupVMPopUpView.setSlotSubBtnAction(e->
        {
            int numCap;
            if(!slotCapField.getText().isEmpty())
            {
                numCap = Integer.parseInt(slotCapField.getText());

                if(numCap > 8)
                {
                    slotCapField.setText(numCap-1+"");
                }
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
            if (newValue) 
            
            {
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
    private RadioButton selectedRadBtn;
    private AppModel appModel;
    
    private SetItemPaneView setItemPaneView;
    private CreateRegMenu regMenu;
    private VMachineModelPaneView vMachineModelPaneView;
    private CreateRegTopBarView createRegTopBarView;
    private SetupVMPopUpView setupVMPopUpView;



}
