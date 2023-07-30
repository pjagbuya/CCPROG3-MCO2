package Models;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;
import java.text.DecimalFormat;


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
		
		
		setOperator(
			new SellingOperator(
				getSlots(),
				getCurrentMoney(),
				getOrderHistory(),
				getChange() ) );
				
		maintenance = new Maintenance( getCurrentMoney(), getSlots(), null );
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
	 * Display's the VM's latest restocking history
	 * (not including initial stocking at creation)
	 *
	 *
	 */
	// public void displayAllStockInfo()
	// {

	// 	double profit;
	// 	String profitLabel;
	// 	String denomination;
	// 	int count;

	// 	VM_StockedInfo tempStockInfo;
	// 	VM_Slot tempSlot;
	// 	LinkedHashMap<VM_Slot, Integer> slotAndStock;

	// 	profit = 0;
	// 	profitLabel = "";

	// 	// Only triggers if the data is not empty
	// 	if(recordCurrInd > 0 && !(stockedInfos.get(recordCurrInd-1).isEmptyData()))
	// 	{
	// 		tempStockInfo = stockedInfos.get(recordCurrInd-1);
	// 		slotAndStock = tempStockInfo.getItemSlotsAndStock();
			
			
	// 		System.out.printf("\t| %20s | %20s | %11s | %20s | %20s |\n", " Item Name ", "Item Prev Stock ", "Items Sold", " Items in Stock", "Profit Collected");
	// 		System.out.println("        |______________________|______________________|_____________|______________________|______________________|");
	// 		for(Map.Entry<VM_Slot, Integer> tempEntry : slotAndStock.entrySet())
	// 		{
	// 			tempSlot = findSlot(tempEntry.getKey().getSlotItemName());
	// 			if( tempSlot != null &&																	// Checks if there is no slot
	// 				tempSlot.getItems() != null &&  														// Checks if the slot is empty
	// 				tempEntry.getKey().getSlotItemName()	!= null &&									// Check if slot has a name
	// 			   	tempEntry.getKey().getSlotItemName().equalsIgnoreCase(tempSlot.getSlotItemName()))		//Compares if the original item is equal to the new item
	// 			{
	// 				// Represents them in the order, name, prev stock, Sold, Current Stock, Profit
	// 				System.out.printf("\t| %20s | %20s | %11s | %20s | %20s |\n", tempEntry.getKey().getSlotItemName(), tempEntry.getValue()+ "",				// Item name, stock previous
	// 																					 tempSlot.getSlotItemSold() + "", tempSlot.getSlotItemStock() + "",				// Sold, stock of current
	// 																					"+" + "Php " + FORMAT.format(tempSlot.getStoredProfit()));						// profit
	// 				System.out.println("        |______________________|______________________|_____________|______________________|______________________|");
					
	// 				// continuously add total profit
	// 				profit += tempSlot.getStoredProfit();
	// 			}


	


				
	// 		}

	// 		profitLabel = profit + "";
	// 		if(profit != 0 && profitLabel.indexOf(".") != -1 && 			// Makes sure that the label has a decimal i.e. greater than 0
	// 		profitLabel.substring(profitLabel.indexOf(".")).length() < 3)	// Checks if the string starting at '.' is less than ".0"
	// 			profitLabel = profitLabel + "0";
			
			
	// 		System.out.printf("                                                                                           |Profit: \033[1;32mPHP %10s\033[0m|\n", profitLabel);
	// 		System.out.printf("Prev Money from prev stock: \033[1;32mPHP %.2f\033[0m\n", tempStockInfo.getMoney().getTotalMoney());


	// 		// For each entry in the Stock info, get every denomination and count
	// 		for(Map.Entry<String,Integer> tempEntry2 : tempStockInfo.getMoney().getDenominations().entrySet())
	// 		{
	// 			denomination = tempEntry2.getKey();
	// 			count = tempEntry2.getValue();
	// 			System.out.println(denomination + ": \033[1;33m" + count + "\033[0m");
	// 		}
	// 		System.out.println("_____________________________________________________________________________________________________");

	// 		// Display differences only if they are not equal
	// 		if(tempStockInfo.getMoney().getTotalMoney() != currentMoney.getTotalMoney())
	// 		{
	// 			// Display current money
	// 			System.out.printf("Current money in stock: \033[1;32mPHP %.2f\033[0m\n", currentMoney.getTotalMoney());

	// 			// get the string denomination and how many they are
	// 			for(Map.Entry<String,Integer> tempEntry2 : currentMoney.getDenominations().entrySet())
	// 			{
	// 				denomination = tempEntry2.getKey();
	// 				count = tempEntry2.getValue();
	// 				System.out.println(denomination + ": \033[1;33m" + count + "\033[0m");
	// 			}

				
	// 		}


			
	// 	}
	// 	else
	// 	{
	// 		System.out.println("\033[1;38;5;202mThere are no stocked Info updated/ item stocks are empty!\033[0m");

			
	// 	}
		
	// }
	

	


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

	
	protected void recordCurrentInventory() {
		stockedInfos.add( new VM_StockedInfo( slots , null , getCurrentMoney() ) );
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

}