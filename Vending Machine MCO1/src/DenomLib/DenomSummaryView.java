package DenomLib;

import java.util.LinkedHashMap;

import Labels.SubLabel;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DenomSummaryView extends ScrollPane
{
    public DenomSummaryView()
    {
        Label tempNameLabel;
       
        int i;
        GridPane denomSummaryPane;
        this.moneyNameToind = new LinkedHashMap<String, Integer>();
        this.countLabels = new Label[13];
        denomSummaryPane = new GridPane();
        mainCanvasVBox = new VBox();
        i = 0;
        for (Denomination denomination : Denomination.values()) 
        {
            tempNameLabel = new SubLabel(denomination.getName(), 18);
            denomSummaryPane.add(tempNameLabel, 0, i);
            countLabels[i] = new SubLabel("0", 18);
            denomSummaryPane.add(countLabels[i], 1, i);

            moneyNameToind.put(tempNameLabel.getText(), i);
            i++;

            
        }
        denomSummaryPane.setHgap(10);
        denomSummaryPane.setVgap(10);
        this.mainCanvasVBox.getChildren().add(denomSummaryPane);
        this.setContent(mainCanvasVBox);
    }
    public void updateCountLabel(int i) 
    {
        int num = Integer.parseInt(this.countLabels[i].getText());
        this.countLabels[i].setText(num+1 + "");
    }
    public void updateCountLabel(String moneyName) 
    {
        updateCountLabel(moneyNameToind.get(moneyName));
    }
    public void updateCountLabel(Double moneyVal) 
    {
        updateCountLabel(moneyNameToind.get(Denomination.fromValue(moneyVal)));
    }
    public void subCountLabel(int i) 
    {
        int num = Integer.parseInt(this.countLabels[i].getText());
        this.countLabels[i].setText(num-1 + "");
    }
    public void subCountLabel(String moneyName) 
    {
        subCountLabel(moneyNameToind.get(moneyName));
    }
    public void subCountLabel(Double moneyVal) 
    {
        subCountLabel(moneyNameToind.get(Denomination.fromValue(moneyVal)));
    }
    private Label[] countLabels;
    private LinkedHashMap<String, Integer> moneyNameToind;
    private VBox mainCanvasVBox;
    
}
