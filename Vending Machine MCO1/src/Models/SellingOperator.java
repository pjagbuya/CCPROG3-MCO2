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
	 * This constructor represent a Selling Operator that manages how
	 * the flow of prompts and how the Vending machine would get affected
	 * by such prompts
	 * 
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
		
		for(PresetItem item : PresetItem.values())
		{
			presetItems.put(item.name(), item.getIsIndependent());
		}
    }


	
	
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
	
	
	
	
	
    

	public String addToOrder(int slotNum, int qty)
	{	
		String msg = null;
		boolean orderIsValid;
		orderIsValid = true; // assumed true
		
		// only when selected slot num is within range, this will trigger to add that order
		if( slotNum >= 1 && slotNum <= slots.length ) {	
			if(slots[slotNum-1] == null) {
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
	 * This helper method would help prompt the payment the user wishes to give to the machine
	 * 
	 * @param payment the payment where the user would store his/her denominations as payment
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
	
	
	
	public String subtractFromPayment(String denom)
	{
		String msg;		
		if( Money.getStrToVal().get(denom) != null )
		{
			payment.subtract( denom );
			msg = null;
		}
		else
		{
			msg = new String("ERROR: DENOMINATION DOES NOT EXIST.");
		}
		return msg;
	}
    
	
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
     * This helper method shows the outcome of an order request that is potentially discontinued or
     * failed
     * 
     * @param orderConfirmed states if the order is confirmed when the user follows rules and machine follows rules, false otherwise or when there is restriction
     * @param transactionIsValid boolean that states the cause of a failure is just a transaction side failure
     * @param payment the types of denominations inserted into the VM, and their corresponding quantities greater than or equal to 0
     * @param change the types of denominations returned by the VM as change, and their corresponding quantities greater than or equal to 0
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
	
	public void proceedTrasaction()
	{
		int i;
		ArrayList<VM_Item> soldItems = dispenseItems( getSlots() );
		for(i = 0; i < soldItems.size(); i++)
			this.soldItems.add( soldItems.get(i) );
		updateCashTrays();
		orderHistory.add( getOrder() );
	}
	
	public DenominationItem createDenomination(String denom)
	{
		return new DenominationItem( denom , Money.getStrToVal().get(denom) );
	}
	
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
	
	
	
	/** successful transactions ONLY */
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
	
	
	public void resetDefaults()
	{
		paymentTotal = 0;
		orderTotal = 0;
		cashReservesTotal = 0;
		changeDue = 0;
		calorieTotal = 0;
	}
	
	
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
	 * Generate more of a specified item
	 *
	 * @param s the name of the item to be added
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
	
	private VM_Item generateCustomItem( String s )
	{
		VM_Item item = null;
		
		if( getCustomItems().get( s ) != null )
			item = new VM_Item( new String(s) , 10.00, getCustomItems().get(s) );
		
		return item;
	}
	
	public void createNewOrder()
	{
		/* order is made blank */
		order = new Order();
	}
	
	public void addOrderHistory(Order order) {
		this.orderHistory.add(order);
	}
	
	public double getPaymentTotal() { return paymentTotal; }
	
	public double getOrderTotal() { return orderTotal; }
	
	public double getCashReservesTotal() { return cashReservesTotal; }
	
	public double getChangeDue() { return changeDue; }
	
	public int getCalorieTotal() { return calorieTotal; }
	
	protected VM_Slot[] getSlots() { return slots; }
	
	protected Order getOrder() { return order; }
	
	public ArrayList<VM_Item> getSoldItems() { return soldItems; }
	
	public LinkedHashMap<String, Integer> getPresetItems() { return presetItems; }
	
	public LinkedHashMap<String, Integer> getCustomItems() { return customItems; }
	
    private double paymentTotal = 0;
	private double orderTotal = 0;
	private double cashReservesTotal = 0;
	private double changeDue = 0;
	private int calorieTotal = 0;
	private int slotNum;
	private int qty;
	private String inputSlot;
	private String inputQty;
	private ArrayList<VM_Item> soldItems = null;
	private ArrayList<Order> orderHistory;
	private VM_Slot[] slots;
	private Money vmCashReserves;
	private LinkedHashMap<String, Integer> duplicate;
	private LinkedHashMap<String, Integer> presetItems;
	private LinkedHashMap<String, Integer> customItems;
	private Money payment;
	private Money change;
	private Order order;

}