package Models;
import DenomLib.Denomination;
import ItemSelectLib.PresetItem;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.InputMismatchException;

/**
 * The class Maintenance represents that part of a vending machine
 * that allows the user to restock, replenish, reprice, add/replace items,
 * check order history, and update its inventory information.
 * 
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class Maintenance implements Generatable
{
	/**
	 * Takes note of the list of possible items in the program
	 *
	 * @param stockedInfos the list of the all inventory records of the parent VM
	 * @param orderHistory the list of all orders that this machine has performed thus far.
	 * @param vmMoney the cash reserves of the parent vending machine
	 * @param slots : the regular slots of the parent vending machine
	 * @param specialSlots : the special slots of the parent vending machine
	 * @param customItems the list of user-minted items
	 */
    public Maintenance(
		ArrayList<VM_StockedInfo> stockedInfos,
		ArrayList<Order> orderHistory,
		Money vmMoney,
		VM_Slot[] slots,
		VM_Slot[] specialSlots,
		LinkedHashMap<String, Integer> customItems )
    {
		this.stockedInfos = stockedInfos;
		this.orderHistory = orderHistory;
		this.vmMoney = vmMoney;
		this.slots = slots; 
		this.specialSlots = specialSlots;
		this.presetItems = new LinkedHashMap<String, Integer>();
		this.customItems = customItems;

		for(PresetItem item : PresetItem.values())
		{
			presetItems.put(item.name(), item.getIsIndependent());
		}
    }

    /**
	 * Provides for manual restocking of the vending machine's individual sellable items.
	 * 
	 * @param itemName the item to restock
	 * @param qty the number of that item to be restocked
	 * @return null if stocking is successful, an error message otherwise
	 */
    public String restockItems(String itemName, int qty)
    {
		String msg = null;
		int i;
		int slotIndex;
        boolean anItemIsRestocked = false; // initially false
		boolean slotFound = false; // intially false
		VM_Slot[] slots = null;
		
		slotIndex = -1;

		slots = switchToSlot( itemName );
		
		for(i = 0; i < slots.length; i++)
			if( slots[i].getSlotItemName() != null &&
				slots[i].getSlotItemName().equalsIgnoreCase(itemName) )
			{
				slotIndex = i;
				slotFound = true;
			}

		if( slotFound )
			msg = new String("ERROR: SLOT NOT FOUND.\n");
        
        // Only proceed updating and allow adding if the slot
		if( slotFound && slots[slotIndex].getSlotItemName() != null )
		{
			if ( !anItemIsRestocked ) {
				anItemIsRestocked = true;
				for( i = 0; i < qty; i++ )
					slots[slotIndex].addItemStock( generateItem( slots[slotIndex].getSlotItemName() ) );
			}
			else
				msg = new String("ERROR: SLOT HAS NO ASSIGNED ITEM. ENTER A DIFF. SLOT NUM.\n");		

		}

		return msg;
    }


    /**
	 * Provides for repricing of the vendine machine's items.
	 *
	 * @param itemName the name of the item to be repriced
	 * @param amt the new price of the given item
	 * @return null if repricing is successful, an error message otherwise
	 */
	public String repriceItems(String itemName, double amt) 
	{
		String msg = null;
		int i;
		int slotIndex;
		boolean slotFound = false; // assumed false
		boolean slotHasName = true; // assumed true
		boolean slotIsEmpty = false; // assumed false
		VM_Slot[] slots = null;
		
		/* INPUT VALIDATION */
		slotIndex = -1;

		slots = switchToSlot( itemName );
		
		for(i = 0; i < slots.length; i++)
			if( slots[i].getSlotItemName() != null &&
				slots[i].getSlotItemName().equalsIgnoreCase(itemName) ) {
				slotIndex = i;
				slotFound = true;
			}
			
		if( !slotFound )
			msg = new String("ERROR: SLOT WITH ITEM NAME NOT FOUND.\n");
		
		/* Checks whether slot is empty */
		if( slotFound && slotHasName && slots[slotIndex].getSlotItemStock() == 0 )
		{	
			msg = new String("ERROR: SLOT IS EMPTY. NO ITEM TO REPRICE.\n");
			slotIsEmpty = true;
		}
			
		/* Decision */
		if( slotFound && slotHasName && !slotIsEmpty )
			slots[slotIndex].repriceItem(amt);
		
		return msg;
	}


    /**
	 * Provides for manual replenishing of the VM's cash reserves.
	 *
	 * @param denom the word-format representation of a piece of money.
	 * @param qty the number of that item to be restocked
	 * @return null if repricing is successful, an error message otherwise
	 */
	public String replenishDenominations(String denom, int qty)
	{
		String msg;
		int i;

		msg = null;
		if( Money.getStrToVal().get(denom) != null )
			for(i = 0; i < qty; i++)
				vmMoney.add( createDenomination(denom) );
		else
			msg = new String("ERROR: DENOMINATION DOES NOT EXIST.\n");
		
		return msg;
	}
	
	
	/**
	 * Creates a new instance of a bill or coin.
	 *
	 * @param denom the name of the new coin/bill to be generated
	 * @return the new bill/coin
	 *
	 */
	public DenominationItem createDenomination(String denom)
	{
		return new DenominationItem( denom , Money.getStrToVal().get(denom) );
	}
	
	
	/**
	 * Tells the vending machine to add another record to its current inventory records
	 */ 
	public void recordCurrentInventory() {
		stockedInfos.add( new VM_StockedInfo( slots , specialSlots , vmMoney ) );
	}
	
    /**
	 * Provides for item stock replacement/filling-in
	 *
	 * @param itemName the name of the item to be used as a substitute
	 * @param qty the number of that item to be restocked
	 * @param slotNum the number of the slots whose items are to be replaced
	 * @return null if stock was successfully replaced, error message otherwise
	 */
	public String replaceItemStock(String itemName, int qty, int slotNum)
	{
		String msg = "";
		int i;
		boolean stockIsReplaced = false; // initially false
		boolean sameNameExists = false; // initially false
		boolean itemExists = true; // assumed true
		boolean qtyIsPositive = true; // assumed true
		boolean slotNumOutOfBounds = false; // assumed false
		VM_Slot[] slots = null;
		
		
		slots = switchToSlot( itemName );
		
		/* START OF INPUT VALIDATION */
			
		/* Ensures that each slot has a unique item. */
		for(i = 0; i < slots.length; i++)
			if(	slots[i].getSlotItemName() != null &&
				slots[i].getSlotItemName().equalsIgnoreCase(itemName) &&
				slots[i].getSlotItemStock() != 0)
			{
				msg = msg + "ERROR: SLOT WITH SAME ITEM EXISTS. RESTOCK INSTEAD.\n";
				sameNameExists = true;
			}
			
		/* Slot Number is Out Of Bounds. */
		if( !sameNameExists && (slotNum < 1 || slotNum > slots.length) )
		{
			msg = msg + "-ERROR: SLOT NUM OUT OF BOUNDS.\n";
			slotNumOutOfBounds = true;
		}
		
		/* Vending Machine Does Not Know This Item. */
		if( presetItems.get(itemName) == null && customItems.get(itemName) == null )
		{
			msg = msg + "ERROR: VENDING MACHINE DOES NOT KNOW THIS ITEM.\n";
			itemExists = false;
		}
			

		/* Adding a zero or negative quantity of the item is not allowed. */
		if( qty <= 0 )
		{
			msg = msg + "ERROR: NON-POSITIVE QUANTITY.\n";
			qtyIsPositive = false;
		}
			
		/* Decision of whether to continue with stock replacement/filling-in */
		if(	qtyIsPositive && !sameNameExists && itemExists && !slotNumOutOfBounds )
		{
			for(i = 0; i < qty; i++)
				slots[slotNum-1].addItemStock( generateItem( itemName ) );
			stockIsReplaced = true;
		}	
		
		if( stockIsReplaced )
			return null;
		return msg;
	}
	


	/**
	 * Provides a simple collection of cash reserves.
	 *
	 * @param denom the word-format name of the denomination
	 * @param qty the number of that item to be restocked
	 * @return null if collecting is successful, an error message otherwise
	 */
	public String collectCashReserves(String denom, int qty)
	{
		String msg;
		int i;
		boolean canSubtract;
		canSubtract = true;
		
		msg = "";
		/* input validation */ 	
		if( Money.getStrToVal().get(denom) == null ) {	/* if denomination does not exist in the standard set */
			msg = new String("ERROR: INVALID DENOMINATION.\n");
			canSubtract = false;
		} if( qty <= 0 ) {	/* if no. of pieces to subtract is negative */
			msg = new String("ERROR: NEGATIVE AND ZERO SUBTRACTION NOT ALLOWED.\n");
			canSubtract = false;
		} if( 	Money.getStrToVal().get(denom) != null &&
				(vmMoney.getDenominations().get(denom).size() - qty < 0 ))
			{	/* if VM cannot release that number of pieces of the denomination */
			msg = new String("ERROR: SUBTRACTION RESULTS IN NEGATIVE DENOMINATIONS.\n");
			canSubtract = false;
		} if( canSubtract )
			for(i = 0; i < vmMoney.getDenominations().get(denom).size(); i++)
				vmMoney.subtract(denom);
		
		return msg;
	}
	
	
	/**
	 * Instatiates new food items, based on the food known by the Maintenance.
	 *
	 * @param s the String name of the item type to be generated
	 * @return the generated item, null otherwise
	 */
	public VM_Item generateItem( String s )
	{
		VM_Item item = null;
		
		if( s.equalsIgnoreCase("Cheese") )
			item = new VM_Item("Cheese", 40.00, 15);
							
		else if( s.equalsIgnoreCase("Cocoa") )
			item = new VM_Item("Cocoa", 20.00, 4);
							
		else if( s.equalsIgnoreCase("Cream") )
			item = new VM_Item("Cream", 18.00, 5);
							
		else if( s.equalsIgnoreCase("Egg") )
			item = new VM_Item("Egg", 12.00, 35);
							
		else if( s.equalsIgnoreCase("Kangkong") )
			item = new VM_Item("Kangkong", 10.00, 2);
							
		else if( s.equalsIgnoreCase("Cornstarch") ) 
			item = new VM_Item("Cornstarch", 13.00, 2);
							
		else if( s.equalsIgnoreCase("Milk") )
			item = new VM_Item("Milk", 99.00, 20);
							
		else if( s.equalsIgnoreCase("Tofu") )
			item = new VM_Item("Tofu", 5.00, 3);
							
		else if( s.equalsIgnoreCase("Salt") )
			item = new VM_Item("Salt", 5.00, 1);
							
		else if( s.equalsIgnoreCase("Sugar") )
			item = new VM_Item("Sugar", 5.00, 30);
							
		else if( s.equalsIgnoreCase("Chicken") )
			item = new VM_Item("Chicken", 150.00, 42);
							
		else if( s.equalsIgnoreCase("BBQ") )
			item = new VM_Item("BBQ", 5.00, 1);
							
		else if( s.equalsIgnoreCase("Flour") )
			item = new VM_Item("Flour", 5.00, 1);
		
		else if( s.equalsIgnoreCase("Soy_Sauce") )
			item = new VM_Item("Soy_Sauce", 4.00, 2);
		
		else if( s.equalsIgnoreCase("Chili") )
			item = new VM_Item("Chili", 2.00, 1);
		
		
		else
			item = generateCustomItem( s );
		
		return item;
	}
	
	
	/**
	 * Instatiates new food items, based on the user-named food known by Maintenance.
	 *
	 * @param s the String name of the item type to be generated
	 * @return the generated item, null otherwise
	 */
	public VM_Item generateCustomItem( String s )
	{
		VM_Item item = null;
		
		if( getCustomItems().get( s ) != null )
			item = new VM_Item( new String(s) , 10.00, getCustomItems().get(s) );
		
		return item;
	}
	
	
	/** 
	 * Allows user to instantiate a VM_Item with a unique name and be able to use its presets later.
	 *
	 * @param name the name to be given to this custom item
	 * @param calories the calorific value of the new item type
	 * @return null if a new custom item was named, error message otherwise
	 */
	public String createCustomItem(String name, int calories)
	{
		int i;
		String msg;
		boolean sameNameExists = false; // assumed false
		msg = null;
		
		/* Checking whether the item already exists in the Maintenance's knowledge. */
		for( String k : presetItems.keySet() )
			if( name.equalsIgnoreCase(k) )
				sameNameExists = true;
		for( String k : customItems.keySet() )
			if( name.equalsIgnoreCase(k) )
				sameNameExists = true;
			
		if(!sameNameExists)
			customItems.put( new String(name) , calories );
		else
			msg = new String("ERROR: ITEM WITH SAME NAME ALREADY EXISTS.\n");
		
		return msg;
	}
	
	/**
	 * Returns the inventory records of the vending machine.
	 *
	 * @return inventory records of this VM_Regular
	 */
	public ArrayList<VM_StockedInfo> getStockedInfos() { return stockedInfos; }
	
	/**
	 * Returns the order history of the vending machine.
	 *
	 * @return orderHistory the list of (successful) transactions
	 */
	public ArrayList<Order> getOrderHistory() { return orderHistory; }
	
	
	/**
	 * Returns the item list that was defined by the user.
	 *
	 * @return the list of custom items for this vending machine
	 */
	public LinkedHashMap<String, Integer>  getCustomItems() { return customItems; }

	/**
         * Chooses a slot set to open based on the given item name.
	 *
	 * @param itemName the name of the item
  	 * @return the slots that correspond with the given name, null if none do
  	 */
	private VM_Slot[] switchToSlot(String itemName)
	{
		/* Switching between special and regular slots. */
		if( presetItems.get( itemName ) == 1 )
			return this.slots;
		else if( presetItems.get( itemName ) == 0 )
			return specialSlots;
		else if( customItems.get( itemName ) != null )
			return this.slots;
		return null;
	}
	
	/** the cash reserves of the parent vending machine */
	private Money vmMoney;
	/** the regular slots of the parent vending machine */
	private VM_Slot[] slots;
	/** the special slots of the parent vending machine */
	private VM_Slot[] specialSlots;
	/** the inventory records of the parent vending machine */
	private ArrayList<VM_StockedInfo> stockedInfos;
	/** the previous transaction info of the parent vending machine */
	private ArrayList<Order> orderHistory;
	/** the list of items that are present in the default item set of this program  */
	private LinkedHashMap<String, Integer> presetItems;
	/** the list of items that are defined by the user during run time */
	private LinkedHashMap<String, Integer> customItems;
}
