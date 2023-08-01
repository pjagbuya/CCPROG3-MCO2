package StartLib;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import DenomLib.Denomination;
import Models.DenominationItem;
import Models.Maintenance;
import Models.Money;
import Models.SellingOperator;
import Models.VM_Factory;
import Models.VM_Item;
import Models.VM_Regular;
import Models.VM_Slot;
import Models.VM_Special;

public class AppModel 
{
    public AppModel()
    {
        this.factory = new VM_Factory();
        this.seller = null;
        this.maintainer = null;
        this.itemOrder = new ArrayList<String>();
        this.vendingMachines = new ArrayList<VM_Regular>();
        this.currInd = -1;
        this.currSlotCap = 8;
        this.currItemCap = 10;
        this.currItemSlot = 0;
    }
    
    public void addVM(String vmType, String name, int nOfSlots, int nOfItems)
    {

        currInd++;
        System.out.println(vmType);
        this.vendingMachines.add(this.factory.createBlankVM(vmType.substring(0, 1), 
                                                            name, nOfSlots, 
                                                            nOfItems));
        this.currSlotCap = nOfSlots;
        this.currItemCap = nOfItems;
        this.seller = getVendingMachine().getOperator();
        this.maintainer = getVendingMachine().getMaintenance();

    }
    public String findSlotNameInVM(int num) {
       return getVendingMachine().getSlot(num).getSlotItemName();
    }
    
    public String addToOrder(int slotNum, int qty) {
        return this.seller.addToOrder(slotNum,qty);
    }

    public boolean isVendingMachineReservesEmpty()
    {

        for(ArrayList<DenominationItem> count : vendingMachines.get(currInd).getCurrentMoney().getDenominations().values())
        {
            if(count.size() != 0)
                return false;
        }
        return true;
    }

    public boolean isVendingMachineSlotEmpty() {
        
        return vendingMachines.get(currInd).getSlots().length == 0;
    }
    
    public boolean isSpecialVM() {
        return this.vendingMachines.get(currInd) instanceof VM_Special;
    }

    public void addReservesToVM(String denom, int count) {
        this.factory.specifyInitialCashReserves(denom, count);
    }
    
    public void addReservesToVM(LinkedHashMap<String, Integer> setReservesInfo) {
       for(Map.Entry<String, Integer> item : setReservesInfo.entrySet())
        {
            itemOrder.add(item.getKey());
            addReservesToVM(item.getKey(), item.getValue());
    
        }
    }
    
    public void repriceItem(String itemName, double price) {
        this.maintainer.repriceItems(itemName, price);
    }
    
    public String addItemToVM(String itemName, int qty) {
        return this.factory.specifyInitialStocks(itemName, qty);
    }
    
    public void addItemToVM(LinkedHashMap<String, Integer> itemInfo, ArrayList<String> order) {
        for(String item: order) { 
            addItemToVM(item, itemInfo.get(item));
        }
    }

    public String createNewItem(String name, int calories)
    {
        String msg;
        msg = this.maintainer.createCustomItem(name, calories);
        if(msg == null || msg.isEmpty())
        {
            this.factory.addAsPossibleItem(name, calories);
        }      

        return msg;
    }
    
    public void restartModel() {
        this.factory.defaultItems();
    }
    
    public VM_Regular getVendingMachine() {
        return vendingMachines.get(currInd);
    }

    public int getCurrItemCap() {
        return currItemCap;
    }
    
    public int getCurrSlotCap() {
        return currSlotCap;
    }



    private SellingOperator extractOperator(int index) {
        return vendingMachines.get(index).getOperator();
    }
    
    public String addToPayment(String denom) {
        return extractOperator(currInd).addToPayment( denom );
    }

    public String subtractFromPayment(String denom) {
        return extractOperator(currInd).subtractFromPayment( denom );
    }

    public Money getChange() {
        return extractOperator(currInd).getChange();
    }

    public double getChangeDue() {
        return extractOperator(currInd).getChangeDue();
    }

    public Money getPayment() {
        return extractOperator(currInd).getPayment();
    }

    public double getPaymentTotal() {
        return extractOperator(currInd).getPaymentTotal();
    }


    
    
    private VM_Factory factory;
    private ArrayList<VM_Regular> vendingMachines;
    private ArrayList<String> itemOrder;
    private SellingOperator seller;
    private Maintenance maintainer;
    private int currSlotCap;
    private int currItemCap;
    private int currItemSlot;
    private int currInd;
    
}
