package Models;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;
import java.text.DecimalFormat;
import ItemSelectLib.PresetItem;

/** The class VM_Regular represents a Regular Vending Machine.
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  */
public class VM_Regular {

		
	/**
	 * Instantiates a VM_Regular object and inititializes its array of slots,
	 * and the array lists containing order history and stock information.
	 *
	 * @param name the name of the vending machine
	 * @param nOfSlots the number of slots in this vending machine
	 * @param item_max the maximum capacity of each slot in this vending maching
	 */
	public VM_Regular(String name, 
					  int nOfSlots, 
					  int item_max,
					  Money change)
	{
		this.name = name;
		if(nOfSlots >= 8)
			slots = new VM_Slot[nOfSlots];
		else
			slots = new VM_Slot[MIN_SLOTS];
		
		for (int i = 0; i < nOfSlots; i++)
		{
			if(item_max >= 10)
				slots[i] = new VM_Slot(item_max);
			else
				slots[i] = new VM_Slot(MAX_ITEMS);
		}
		currentMoney = new Money();
		
		orderHistory = new ArrayList<Order>();
		stockedInfos = new ArrayList<VM_StockedInfo>();
		this.change = change;
		presetItems = new LinkedHashMap<String, Integer>();
		customItems = new LinkedHashMap<String, Integer>();
		
		setOperator(
			new SellingOperator(
				getSlots(),
				getCurrentMoney(),
				getOrderHistory(),
				getChange(),
				getCustomItems() ) );
				
		maintenance = new Maintenance(
			getStockedInfos(),
			getOrderHistory(),
			getCurrentMoney(),
			getSlots(),
			null,
			getCustomItems() );
			
		for(PresetItem item : PresetItem.values())
		{
			presetItems.put(item.name(), item.getIsIndependent());
		}	
	}
	
	/**
 	 * Returns this vending machine's change tray.
   	 *
     	 * @return the change tray
         */
	public Money getChange()
	{
		return change;
	}

	/**
 	 * Returns this vending machine's operator brain.
   	 *
     	 * @return the operator brain
         */
	public SellingOperator getOperator()
	{
		return operator;
	}
	
	/**
	 * Replaces the current order history with a blank new one.
	 */
	public void emptyOrderHistory() 
	{
		orderHistory = new ArrayList<Order>();
	}

	
	/**
	 * Returns the regular slots.
	 *
	 * @return array of this vending machine's regular slots
	 */
	public VM_Slot[] getSlots() {
		return slots;
	}
	
	/**
	 * Return a regular slot specified by its index
	 *
	 * @param ind the index of the slot to be returned
	 * @return the desired slot
	 */
	public VM_Slot getSlot(int ind) {
		if(ind >= 0 && ind < getSlots().length)
			return getSlots()[ind];
		return null;
	}
	
	/**
	 * Returns the cash reserves of this vending machine.
	 * 
	 * @return the money tray of this VM
	 */
	public Money getCurrentMoney() {
		return currentMoney;
	}


	/**
	 * Returns the order history of this vending machine.
	 * 
	 * @return the list of successful transactions of the vending machine
	 */
	public ArrayList<Order> getOrderHistory() {
		return orderHistory;
	}
	

	
	/**
	 * Returns MIN_SLOTS
	 *
	 * @return minimum number of slots the vending machine must hold
	 */
	public static int getMinSLOTS()
	{
		return MIN_SLOTS;
	}

	
	/**
	 * Returns MAX_SLOTS
	 *
	 * @return maximum number of items that each slot can contain
	 */
	public static int getMaxITEMS()
	{
		return MAX_ITEMS;
	}

	
	/**
	 * Returns the vending machine's name
	 *
	 * @return the vending machine's name
	 */
	public String getName()
	{
		return name;
	}	


	/**
	 * Sets this vending machine's name
	 *
	 * @param name the vending machine's new name
	 */
	public void setName(String name)
	{
		if(name != null && name.length() >= 1)
			this.name = name;
		else
			this.name = new String("defaultName");
	}

	
	
	/**
 	 * Return this vending machine's inventory records.
   	 *
     	 * @return the inventory records
     	 */
	protected ArrayList<VM_StockedInfo> getStockedInfos() {
		return stockedInfos;
	}
	
	
	/**
 	 * Sets this vending machine's operator brain.
   	 *
     	 * @param the new operator brain
     	 */
	public void setOperator(SellingOperator sellingOperator)
	{
		operator = sellingOperator;
	}

	/**
 	 * Return this vending machine's maintenance brain.
   	 *
     	 * @return the maintenance brain
     	 */
	public Maintenance getMaintenance()
	{
		return maintenance;
	}
	
	/**
 	 * Sets this vending machine's maintenance brain.
   	 *
     	 * @param the new maintenance brain
     	 */
	public void setMaintenance(Maintenance maintenance)
	{
		this.maintenance = maintenance;
	}

	/**
 	 * Return the list of preset items.
   	 *
     	 * @return the list of preset items
     	 */
	public LinkedHashMap<String, Integer> getPresetItems() { return presetItems; }

	/**
 	 * Return the list of custom (user-named) items.
   	 *
     	 * @return the list of custom items
     	 */
	public LinkedHashMap<String, Integer> getCustomItems() { return customItems; }


	/** the vending machine's maintenance brain */
	private Maintenance maintenance;
	/** the change tray */
	private Money change;
	/** the vending machine's operator brain */
	private SellingOperator operator;
	/** the array of regular slots */
	private VM_Slot[] slots;
	/** the vending machine's name */
	private String name;
	/** the vending machine's cash reserve */
	private Money currentMoney;
	/** Format constant that would help format labels of each item prices or computations*/
	private static final DecimalFormat FORMAT = new DecimalFormat("0.00");
	/** the list of inventory records, with each record being taken right before each restocking or repricing */
	private ArrayList<VM_StockedInfo> stockedInfos;
	/** the list of successful transaction records starting from the last restocking */
	private ArrayList<Order> orderHistory;
	/** the minimum number of slots a machine must hold */
	private static final int MIN_SLOTS = 8;
	/** the maximum number of items a slot can hold */
	private static final int MAX_ITEMS = 10;
	/** the list of preset items in the program */
	private LinkedHashMap<String, Integer> presetItems;
	/** user-named items and their calorific values */
	private LinkedHashMap<String, Integer> customItems;

}
