package Models;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import ItemSelectLib.PresetItem;

import DenomLib.Denomination;


/** This class represents a Selling Operator
  * which provides for selling of regular items only
  * through the use of text based prompt
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  */
public class SellingOperator
{

	/**
	 * Instantiates the "seller" part of the brain of the vending machine.
	 * 
	 * @param slots the regular slots of the parent vending machine
  	 * @param vmCashReserves the cash reserves of the parent vending machine
     * @param orderHistory the list of (successful) transactions from the parent vending machine
     * @param change the change tray
	 * @param customItems the list of custom items from the parent vending machine
	 */
	    public SellingOperator(
			VM_Slot[] slots,
			Money vmCashReserves,
			ArrayList<Order> orderHistory,
			Money change,
			LinkedHashMap<String, Integer> customItems )
	    {
			this.orderHistory = orderHistory;
			this.slots = slots;
			this.vmCashReserves = vmCashReserves;
			this.change = change;
			this.customItems = customItems;
			this.presetItems = new LinkedHashMap<String, Integer>();
			
			for(PresetItem item : PresetItem.values())
			{
				presetItems.put(item.name(), item.getIsIndependent());
			}
    	}


	
	/**
	 * Tell the operator to compute for payment total, order total, and calorie total,
	 * and change due for the current transaction.
	 */
	public void calculateBasicInformation()
	{
		/* creates a copy of the set of denominations currently in the VM */
		duplicateDenominations();
		
		/* calculates the total amount of cash reserves currently in the VM */
		cashReservesTotal = vmCashReserves.getTotalMoney();
		
		/* calculates payment total */
		for(String s : payment.getDenominations().keySet())
			paymentTotal += payment.getDenominations().get(s).size() * Money.getStrToVal().get(s);
		
		/* calculates order total */
       	 	orderTotal = order.getTotalCost();
		
		/* calculates calorie total */
		calorieTotal = order.getTotalCalories();
		
		/* calculates change due */
		changeDue = paymentTotal - orderTotal;
	}
	
	
	
	
	
    
	/**
	 * Adds a specified quantity of an item to the order list.
	 *
	 * @param slotNum the number of the slot whose item is to be listed in the order
	 * @param qty the number of pieces ordered
	 * @return null if the item was successfully added to the order, error message otherwise
	 */
	public String addToOrder(int slotNum, int qty)
	{	
		String msg = null;
		boolean orderIsValid;
		orderIsValid = true; // assumed true
		
		// only when selected slot num is within range, this will trigger to add that order
		if( slotNum >= 1 && slotNum <= slots.length ) 
		{
			System.out.println("Slot No. " + (slotNum-1) + ", " + slots[slotNum-1].getSlotItemName());	
			if(slots[slotNum-1] == null || slots[slotNum-1].getSlotItemName() == null) {
				msg = new String("ERROR: SLOT IS NULL.\n");
				orderIsValid = false;
			}
			else if( slots[slotNum-1].getSlotItemStock() == 0 ||
					 qty > slots[slotNum-1].getSlotItemStock() ||
					 qty <= 0 ) {
				msg = new String("ERROR: INSUFFICIENT STOCK.\n");
				orderIsValid = false;
			}
			
			if( orderIsValid ) {
				addToPendingMap( order.getPendingOrder(), slots[slotNum-1], qty);
				msg = null;
			}
		}
		else
			msg = new String("ERROR: SLOT NUM OUT OF BOUNDS.");
		
		return msg;
	}

	/**
	 * Updates the information on the order list.
	 *
	 * @param pending the hashmap inside in the Order object
	 * @param slot a slot set of the vending machine
	 * @param qty the number of ordered items
	 */
	protected void addToPendingMap(LinkedHashMap <String, Integer> pending, VM_Slot slot, int qty)
	{
		if( pending.get( slot.getSlotItemName().toUpperCase() ) != null ) 
		{
		
			order.setTotalCost(
				order.getTotalCost() - 
				slot.getPrice() *
				pending.get(
				slot.getSlotItemName().toUpperCase() ));

			orderTotal = order.getTotalCost();
			order.setTotalCalories(
				order.getTotalCalories() - 
				slot.getItems().get(0).getItemCalories() *
				pending.get(
					slot.getSlotItemName().toUpperCase() ));
			calorieTotal = order.getTotalCalories();
		}
		
		pending.put( slot.getSlotItemName().toUpperCase() , qty );
	}


	/**
	 * Adds coins or bills to the payment tray.
	 * 
	 * @param denom the worded value of the coin or banknote to be inserted into the vending machine.
	 * @return null if coin/banknote was successfully added to payment tray, error message otherwise
	 */
	public String addToPayment(String denom)
	{
		String msg;		
		if( Money.getStrToVal().get(denom) != null )
		{
			payment.add( createDenomination( denom ) );
			msg = null;
		}
		else
			msg = new String("ERROR: DENOMINATION DOES NOT EXIST.");
		return msg;
	}
	
	/**
	 * Removes coins or bills from the payment tray.
	 * 
	 * @param denom the worded value of the coin or banknote to be removed from the vending machine.
	 * @return null if coin/banknote was successfully removed from payment tray, error message otherwise
	 */
	public String subtractFromPayment(String denom)
	{
		String msg;		
		if( Money.getStrToVal().get(denom) != null )
		{
			if( payment.subtract( denom ) != null )
				msg = null;
			else
				msg = new String("ERROR: NO DENOMINATION TO SUBTRACT.");
		}
		else
			msg = new String("ERROR: DENOMINATION DOES NOT EXIST.");
		return msg;
	}
    

	/**
	 * Checks whether the current transaction is valid given its basic information.
	 *
	 * @return null if transaction is valid, error message otherwise
	 */
	public String validateTransaction()
	{
		String msg = new String("");
		Boolean changeIsPossible;
		
		changeIsPossible = deductChange(changeDue);
		
		if( !hasEnoughStock(this.slots) ) {
			msg = msg + "ERROR: INSUFFICIENT STOCK.\n";
		}
		if( paymentTotal < orderTotal ) {
			msg = msg + "ERROR: INSUFFICIENT PAYMENT.\n";
		}
		if ( cashReservesTotal < orderTotal && !changeIsPossible ) {
			msg = msg + "ERROR: NOT ENOUGH MONEY RESERVES.\n";
		}
		if( changeDue >= 0 && !changeIsPossible ) {
			msg = msg + "ERROR: CANNOT RETURN CHANGE, INSERT EXACT AMOUNT.\n";
		}
		if(orderTotal == 0) {
			msg = msg + "ERROR: NO ORDER.\n";
		}
		
		if(msg.equals(""))
			return null;
		return msg;
	}

	/**
	 * Checks whether the given slot set has (part of) the required stock.
	 *
	 * @param slots the slot set to be checked, regular or special
	 * @return true if the [regular/special] slot set has the required [independent/dependent] items, false otherwise
	 */
	protected boolean hasEnoughStock(VM_Slot[] slots)
	{
		int i;
		LinkedHashMap<String, Integer> orders;
		orders = order.getPendingOrder();
		boolean stockHasRequiredQuantities = true; // initially true
		
		for( String s : orders.keySet() )
			for(i = 0; i < slots.length; i++)
				if( slots[i].getSlotItemName() != null &&
					s.equalsIgnoreCase( slots[i].getSlotItemName() ) &&
					orders.get(s) > slots[i].getSlotItemStock() )
						stockHasRequiredQuantities = false;
		return stockHasRequiredQuantities;
	}
	
   	/**
     	 * Records the denominations currently stored in the parent's cash reserves as a hashmap.
         */
	private void duplicateDenominations()
	{
		/* duplicating cash reserves of VM, while setting change to zero */
		for( String s : vmCashReserves.getDenominations().keySet() )
		{
			duplicate.put( s , vmCashReserves.getDenominations().get(s).size() );
			change.getDenominations().put(s , new ArrayList<DenominationItem>() );
		}
	}

	/**
	 * Tells the vending machine to go ahead with the transaction.
	 */
	public void proceedTrasaction()
	{
		int i;
		ArrayList<VM_Item> soldItems = dispenseItems( getSlots() );
		for(i = 0; i < soldItems.size(); i++)
			this.soldItems.add( soldItems.get(i) );
		updateCashTrays();
		orderHistory.add( getOrder() );
	}

	/**
	 * Creates a coin or banknote.
	 *
	 * @param denom the worded value of the denomnation to be added
	 * @return the coin or banknote itself
	 */
	public DenominationItem createDenomination(String denom)
	{
		return new DenominationItem( denom , Money.getStrToVal().get(denom) );
	}

	/**
	 * Releases items based on current order.
	 *
	 * @param slots the slot set from which to release (a subset of) the ordered items.
	 * @return the released items
	 */
	public ArrayList<VM_Item> dispenseItems(VM_Slot[] slots)
	{
        int currAmt;
        int i;
		int j;
		ArrayList<VM_Item> soldItems = new ArrayList<VM_Item>();
		
		if( getOrder().getPendingOrder().size() > 0 )
        for(String itemName : getOrder().getPendingOrder().keySet())
            for(i = 0; i < slots.length; i++)
                if( slots[i].getSlotItemName() != null && itemName.equalsIgnoreCase( slots[i].getSlotItemName() ) )
                {
                    currAmt = getOrder().getPendingOrder().get(itemName);
                    // Check max amount should be dispensed if order was greater than 10
                    if( currAmt > slots[i].getMAX() )
						for(j = 0; j < slots[i].getMAX(); j++)
							soldItems.add( slots[i].releaseStock() );
                    // Dispenses the item amount wished
                    else
						for(j = 0; j < currAmt; j++)
							soldItems.add( slots[i].releaseStock() );
                }
		return soldItems;
    }
	
	
	
	/**
	 * Releases change into the change tray, accepts and sorts the payment. Clears references (pointers) in payment tray.
	 * Reminder: For successful transactions ONLY
	 */
	public void updateCashTrays()
	{		
		int difference; // the difference between the corresponding nos. of denominations in the cash reserve and the duplicate hashmap
		int additional; // the additional payment pieces that have to be placed into the cash reserves
		int i;
		
        /* Takes change out. Accepts and sorts the payment. This means the payment tray should be empty. */
		for( String s : vmCashReserves.getDenominations().keySet() )
		{
			difference = vmCashReserves.getDenominations().get(s).size() - duplicate.get(s);
			for(i = 0; i < difference; i++)
				change.add( vmCashReserves.subtract( s ) );
			additional = payment.getDenominations().get(s).size();
			for(i = 0; i < additional; i++)
				vmCashReserves.add( payment.subtract( s ) );
			for(i = 0; i < additional; i++)
				payment.getDenominations().put( s , new ArrayList<DenominationItem>() );
		}
	}
	
	
	/**
	 * Tells the vending machine to cancel the transaction. Returns any entered payment.
	 */
	public void discontinueTransaction()
	{
		for( String s : payment.getDenominations().keySet() )
			change.add( payment.subtract( s ) );
	}
	

	/**
	 * Reverts calculated basic transaction information values back to zero.
	 */
	public void resetDefaults()
	{
		paymentTotal = 0;
		orderTotal = 0;
		cashReservesTotal = 0;
		changeDue = 0;
		calorieTotal = 0;
	}
	
	/**
	 * Checks whether the cash reserves can release a specified amount of cash, for change.
	 * 
	 * @param amt the total amount that is planned to be released
	 * @return true if the vending machine can return the exact amount of change, false otherwise
	 */
	protected boolean deductChange(double amt)
	{

		amt = Math.round(amt*100)/100.0;

		// Loop repeats until it is exactly zero, indicating sufficient change
		while(amt != 0)
		{
			if(amt >= 1000.0 && duplicate.get("One Thousand Bill") > 0) {
				duplicate.put("One Thousand Bill", duplicate.get("One Thousand Bill") - 1);
				amt -= 1000;
			}
			else if(amt >= 500.0 && duplicate.get("Five Hundred Bill") > 0) {
				duplicate.put("Five Hundred Bill", duplicate.get("Five Hundred Bill") - 1);
				amt-= 500;
			}
			else if(amt >= 200.0 && duplicate.get("Two Hundred Bill") > 0) 
			{
				duplicate.put("Two Hundred Bill", duplicate.get("Two Hundred Bill") - 1);
				amt -= 200;
			}
			else if(amt >= 100.0 && duplicate.get("One Hundred Bill") > 0) {
				duplicate.put("One Hundred Bill", duplicate.get("One Hundred Bill") - 1);
				amt -= 100;
			}
			else if(amt >= 50.0 && duplicate.get("Fifty Bill") > 0) {
				duplicate.put("Fifty Bill", duplicate.get("Fifty Bill") - 1);
				amt -= 50;
			}
			else if(amt >= 20.0 && duplicate.get("Twenty Bill") > 0) {
				duplicate.put("Twenty Bill", duplicate.get("Twenty Bill") - 1);
				amt-= 20;
			}
			else if(amt >= 20.0 && duplicate.get("Twenty Coin") > 0) {
				duplicate.put("Twenty Coin", duplicate.get("Twenty Coin") - 1);
				amt -= 20;
			}
			else if(amt >= 10.0 && duplicate.get("Ten Coin") > 0) {
				
				duplicate.put("Ten Coin", duplicate.get("Ten Coin") - 1);
				amt -= 10;
			}
			else if(amt >= 5.0 && duplicate.get("Five Coin") > 0) {
				duplicate.put("Five Coin", duplicate.get("Five Coin") - 1);
				amt -= 5;
			}
			else if(amt >= 1.0 && duplicate.get("One Coin") > 0) {
				duplicate.put("One Coin", duplicate.get("One Coin") - 1);
				amt -= 1;
			}
			else if(amt >= 0.25 && duplicate.get("Twenty Five Cents") > 0) {
				duplicate.put("Twenty Five Cents", duplicate.get("Twenty Five Cents") - 1);
				amt -= 0.25;
			}
			else if(amt >= 0.05 && duplicate.get("Five Cents") > 0) {
				duplicate.put("Five Cents", duplicate.get("Five Cents") - 1);
				amt -= 0.05;
			}
			else if(amt >= 0.01 && duplicate.get("One Cent") > 0) {
				duplicate.put("One Cent", duplicate.get("One Cent") - 1);
				amt -= 0.01;
			}
			// Case where the program arrives into undeterminable amt due to floating point addition, that could not ever lead to zero but close, exit loop
			else if(amt >= -0.000001907348633 && amt <= 0.000001907348633) {    /*  0.0.000001907348633 == (1/2)^19   */
 				break;
			}
			else
			{
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Generates an instance of an item.
	 *
	 * @param s the name of the item to be instantied.
	 * @return the instatiated item, null if nothing was instantiated
	 */
	protected VM_Item generateItem( String s )
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
			generateCustomItem( s );
		
		return item;
	}

	/**
	 * Generates an instance of a custom item.
	 *
	 * @param s the name of the item to be instantied.
	 */
	private VM_Item generateCustomItem( String s )
	{
		VM_Item item = null;
			
		if( getCustomItems().get( s ) != null )
			item = new VM_Item( new String(s) , 10.00, getCustomItems().get(s) );
			
		return item;
	}

	/**
	 * Resets current order back to zero.
	 */
	public void createNewOrder()
	{
		/* order is made blank */
		order = new Order();
	}

	/**
	 * Adds the order to the parent vending machine's order history.
	 *
	 * @param order the order to be added to the transaction records
	 */
		
	public void addOrderHistory(Order order) {
		this.orderHistory.add(order);
	}

	/**
	 * Returns the current total of the inserted payment.
	 *
	 * @return the total value of the inserted payment
	 */
	public double getPaymentTotal() { return paymentTotal; }
	
	/**
	 * Returns the current total of the planned order.
	 *
	 * @return the total value of the inserted payment
	 */
	public double getOrderTotal() { return orderTotal; }
	
	/**
	 * Returns the current total of the cash reserves.
	 *
	 * @return the total value of the parent vending machine's cash reserves
	 */
	public double getCashReservesTotal() { return cashReservesTotal; }
		
	/**
	 * Returns the numerical value of the change due to the customer.
	 *
	 * @return the numerical value total value of the change due to the customer
	 */
	public double getChangeDue() { return changeDue; }
	
	/**
	 * Returns the total amount of calories in the current order.
	 *
	 * @return the total amount of calories in the current order.
	 */
	public int getCalorieTotal() { return calorieTotal; }
	
	/**
	 * Returns the regular slots of the parent vending machine.
	 *
	 * @return the regular slots of the parent vending machine
	 */
	protected VM_Slot[] getSlots() { return slots; }
	
	/**
	 * Returns the current in-progress order.
	 *
	 * @return the current order instance
	 */
	protected Order getOrder() { return order; }
		
	/**
	 * Returns the stack of released items.
	 *
	 * @return the stack of released items
	 */
	public ArrayList<VM_Item> getSoldItems() { return soldItems; }
	
	/**
	 * Returns the list of preset items in the program.
	 *
	 * @return the list of preset items
	 */
	public LinkedHashMap<String, Integer> getPresetItems() { return presetItems; }
	
	/**
	 * Returns the list of custom items in the memory of the parent vending machine.
	 *
	 * @return the list of custom items
	 */
	public LinkedHashMap<String, Integer> getCustomItems() { return customItems; }
	
	/**
	 * Returns the payment tray.
	 *
	 * @return the payment tray
	 */
	public Money getPayment() { return payment; }
	
	/**
	 * Returns the change tray.
	 *
	 * @return the change tray
	 */
	public Money getChange() { return change; }
	
	/** total value of the payment */
	private double paymentTotal = 0;
	/** total value of the ordered items */
	private double orderTotal = 0;
	/** total value of the cash reserves */
	private double cashReservesTotal = 0;
	/** numerical value of the change due to the customer */
	private double changeDue = 0;
	/** total amount of calories in the order */
	private int calorieTotal = 0;
	/** the stack of released items */
	private ArrayList<VM_Item> soldItems = null;
	/** the lsit of successful (previous) transactions */
	private ArrayList<Order> orderHistory;
	/** the regular slots of the parent vending machine */
	private VM_Slot[] slots;
	/** the cash reserves of the parent vending machine */
	private Money vmCashReserves;
	/** the record of denominations in the cash reserves */
	private LinkedHashMap<String, Integer> duplicate;
	/** the list of preset items in the program */
	private LinkedHashMap<String, Integer> presetItems;
	/** the list of custom items in the memory of the parent vending machine */
	private LinkedHashMap<String, Integer> customItems;
	/** the payment tray */
	private Money payment;
	/** the change tray */
	private Money change;
	/** the current order */
	private Order order;
}