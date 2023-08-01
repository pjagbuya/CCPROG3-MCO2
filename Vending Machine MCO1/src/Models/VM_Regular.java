package Models;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;
import java.text.DecimalFormat;
import ItemSelectLib.PresetItem;

/** This class represents a Regular Vending Machine
  * and its methods and attributes
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  */
public class VM_Regular {

		
	/**
	 * Creates VM_Regular object and inititializes the array of slots,
	 * the array list containing order history, and stock information
	 *
	 * @param name the name of the vending machine
	 * @param nOfSlots the number of slots in VM
	 * @param item_max the maximum capacity of each slot in VM
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
				
		setMaintenance(new Maintenance(
			getStockedInfos(),
			getOrderHistory(),
			getCurrentMoney(),
			getSlots(),
			null,
			getCustomItems() ));
			
		for(PresetItem item : PresetItem.values())
		{
			presetItems.put(item.name(), item.getIsIndependent());
		}	
	}
	
	
	public Money getChange()
	{
		return change;
	}
	
	public SellingOperator getOperator()
	{
		return operator;
	}
	
	/**
	 * Replaces the current record of order history with a blank new one
	 *
	 */
	public void emptyOrderHistory() 
	{
		orderHistory = new ArrayList<Order>();
	}
	
	
	/**
	 * Displays item descriptions on terminal, else displays that there is no items stocked
	 *
	 *
	 */
	public void displayAllItems() {
		int i;
		boolean isThereItem;
		System.out.println();
		System.out.println("Item Info: ");
		System.out.println();

		isThereItem = false;
		for(i = 0; i < slots.length; i++)
			if(slots[i] != null && slots[i].getItems() != null)
			{
				slots[i].displayAllItems();
				isThereItem = true;
			}
		if(!isThereItem)
			System.out.println("No item stock/label is available to display");
				
	}

	/**
	 * Gets VM's slot array
	 *
	 * @return array of the VM's slots
	 */
	public VM_Slot[] getSlots() {
		return slots;
	}
	
	/**
	 * Gets a slot specified by its index
	 *
	 * @param ind the index of the slot to be returned
	 * @return the desired slot
	 */
	public VM_Slot getSlot(int ind) {
		if(ind >= 0 && ind < slots.length)
			return slots[ind];
		return null;
	}
	
	/**
	 * This method gets currentMoney containing denominations of this vending machine
	 * 
	 * @return the money tray of this VM
	 */
	public Money getCurrentMoney() {
		return currentMoney;
	}


	/**
	 * This method gets the order history of the VM.
	 * 
	 * @return the array list instance of order history of the Vending machine
	 */
	public ArrayList<Order> getOrderHistory() {
		return orderHistory;
	}
	

	
	/**
	 * Gets MIN_SLOTS
	 *
	 * @return minimum number of slots the VM must hold
	 */
	public static int getMinSLOTS()
	{
		return MIN_SLOTS;
	}
	
	/**
	 * Gets MAX_SLOTS
	 *
	 * @return maximum no. of items that each slot can contain
	 */
	public static int getMaxITEMS()
	{
		return MAX_ITEMS;
	}
	
	/**
	 * This method Gets the VM's name
	 *
	 * @return the VM's name
	 */
	public String getName()
	{
		return name;
	}
	


	
	
	
	/**
	 * Display's the VM's transaction history (not including failed transactions)
	 * starting from the last restocking or repricing
	 *
	 *
	 */
	public void displayOrderHistory() {
		int i;
		
		for(i = 0; i < orderHistory.size(); i++) {
			System.out.println("\nORDER NO. " + (i+1));
			for(String s : orderHistory.get(i).getPendingOrder().keySet())
				System.out.println(
					orderHistory.get(i).getTotalCost() +
					" PHP " +
					orderHistory.get(i).getPendingOrder().get(s) +
					" pc(s). " +
					s +
					"\n" );
		}
	}
	
	

	

	


	/**
	 * Sets VM's name
	 *
	 * @param name the VM's new name
	 */
	public void setName(String name)
	{
		if(name != null && name.length() >= 1)
			this.name = name;
		else
			this.name = new String("defaultName");
	}

	
	
	
	protected ArrayList<VM_StockedInfo> getStockedInfos() {
		return stockedInfos;
	}
	
	
	
	public void setOperator(SellingOperator sellingOperator)
	{
		operator = sellingOperator;
	}
	
	public Maintenance getMaintenance()
	{
		return maintenance;
	}
	
	
	public void setMaintenance(Maintenance maintenance)
	{
		this.maintenance = maintenance;
	}
	
	public LinkedHashMap<String, Integer> getPresetItems() { return presetItems; }
	
	public LinkedHashMap<String, Integer> getCustomItems() { return customItems; }

	private Maintenance maintenance;
	private Money change;
	private SellingOperator operator;
	/** the array of VM slots */
	private VM_Slot[] slots;
	/** the VM's name */
	private String name;
	/** the VM's cash reserve */
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
	
	private LinkedHashMap<String, Integer> presetItems;
	/** user-named items and their calorific values */
	private LinkedHashMap<String, Integer> customItems;

}