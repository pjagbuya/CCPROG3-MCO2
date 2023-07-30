package StartLib;

import java.util.ArrayList;

import Models.Money;
import Models.VM_Factory;
import Models.VM_Regular;

public class AppModel 
{
    public AppModel()
    {
        this.factory = new VM_Factory();
        this.vendingMachines = new ArrayList<VM_Regular>();
        this.currInd = -1;
        this.currSlotCap = 8;
        this.currItemCap = 10;
    }
    public void addVM(String name, int nOfSlots, int nOfItems)
    {
        Money money = new Money();
        currInd++;
        vendingMachines.add(new VM_Regular(name, nOfSlots, nOfItems, money));
        this.currSlotCap = nOfSlots;
        this.currItemCap = nOfItems;

    }
    public VM_Regular getVendingMachine() 
    {
        return vendingMachines.get(currInd);
    }

    public int getCurrItemCap() {
        return currItemCap;
    }
    public int getCurrSlotCap() {
        return currSlotCap;
    }
    private VM_Factory factory;
    private ArrayList<VM_Regular> vendingMachines;
    private int currSlotCap;
    private int currItemCap;
    private int currInd;
    
}
