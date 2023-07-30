package MaintenanceLib;




import Labels.HeaderLabel;
import Labels.SubLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MaintenanceOrderHisView extends BorderPane
{
    public MaintenanceOrderHisView(Stage parentWin)
    {


        ScrollPane mainScrollPane = new ScrollPane();
       
        this.mainCanvasPane = new VBox();
        this.mainCanvasPane.setSpacing(20);
        this.mainCanvasPane.setAlignment(Pos.CENTER);
        this.orderNum = 0;


        addOrderInfo("20 PHP, 1 pc/s Ice Cream");
        mainScrollPane.setContent(this.mainCanvasPane);

        this.setCenter(mainScrollPane);
    }

    public void addOrderInfo(String order)
    {
        
        this.orderNum += 1;
        Label orderNumLabel = new SubLabel("ORDER NO. " + this.orderNum +"");
        Label tempLabel = new SubLabel(order);
        VBox orderAndLabelVBox = new VBox();

        orderAndLabelVBox.setStyle(BORDERSTYLE);
        orderAndLabelVBox.getChildren().addAll(orderNumLabel, tempLabel);

        this.mainCanvasPane.getChildren().add(orderAndLabelVBox);
        
    }

    private static final String COLOR_FONT = "#97FEED";
    private static final String BORDERSTYLE = "-fx-border-color:"+COLOR_FONT+"; -fx-border-width: 1; -fx-padding: 10;";
       
    private int orderNum;
    private VBox mainCanvasPane;

}
