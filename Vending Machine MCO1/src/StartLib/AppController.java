package StartLib;

import Boxes.ConfirmBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppController {
    public AppController (Stage primaryWindow, AppView appView, Scene scene)
    {
        
        this.primaryWindow = primaryWindow;
        this.appView = appView;

        SetupVMPopUpController setupVMPopUpController;

        regMenu = new CreateRegMenu("Create Vending Machine", primaryWindow, scene);
        setUpVMPopUpView = new SetupVMPopUpView(primaryWindow, regMenu.getScene());
        setupVMPopUpController = new SetupVMPopUpController(setUpVMPopUpView);

        this.appView.setBtnCreateRegAction(e->
        {
            setUpVMPopUpView.show();

        });

        this.appView.setBtnExitAction(e->
        {
            closeProgram(this.primaryWindow);
        });

        this.primaryWindow.setOnCloseRequest(e-> {
            e.consume();            // This line ensures the request of closing the program isnt proceeded
            closeProgram(this.primaryWindow);
        });


        
        


    }
    private void closeProgram(Stage window)
    {   ConfirmBox boxMessage = new ConfirmBox();
        boolean answer = boxMessage.display("Warning", "Are you sure you want to exit?");


        if(answer)
            this.primaryWindow.close();
    }


    public CreateRegMenu getRegMenu() 
    {
        return regMenu;
    }
    private CreateRegMenu regMenu;
    private SetupVMPopUpView setUpVMPopUpView;
    private Scene scene;
    private Stage primaryWindow;
    private AppView appView;
}
