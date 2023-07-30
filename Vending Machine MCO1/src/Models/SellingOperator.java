package Models;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

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
    public SellingOperator()
    {
		
    }

    
	/**
	 * Activates regular selling method immediately, and dispenses sold items.
	 *
	 * @param duplicate		a duplicate set of the VM's current denominations	 
	 * @param payment		the types of denominations inserted into the VM, and their corresponding quantities greater than or equal to 0
	 * @param change		the types of denominations returned by the VM as change, and their corresponding quantities greater than or equal to 0
     * @param order			the order object, contains the user's order
	 */
	public ArrayList<VM_Item> sellingOperation(
		VM_Regular vm,
		Money duplicate,
		Money payment,
		Money change,
		Order order )
	{
		ArrayList<VM_Item> soldItems = null;
		VM_Item tempItemHolder;
		VM_Slot[] slots;
		int i;
		int j;
		
		slots = vm.getSlots();
		
		
		sellRegularItems( vm, duplicate, payment, change, order ); 
		
		
		if( order.getPendingOrder().size() > 0 )
		{
			soldItems = new ArrayList<VM_Item>();
			
			/* Instantiates the items themselves, and updates their prices. */
			for( String k : order.getPendingOrder().keySet() )
				for(i = 0; i < slots.length; i++)
					if( slots[i].getSlotItemName().equalsIgnoreCase( k ) )
						for(j = 0; j < order.getPendingOrder().get(k); j++)
						{
							tempItemHolder = generateItem( k );
							tempItemHolder.setPrice( slots[i].getPrice() );
							soldItems.add( tempItemHolder );
						}
		}
		
		return soldItems;
	}
	
			
	
	/**
	 * This method takes user's order and accepts their payment,
	 * validates inputs,
	 * and decides whether to proceed with transaction or not.
	 * 
	 * Uses text-based interface.
	 * 
	 * @param duplicate		a duplicate set of the VM's current denominations	 
	 * @param payment		the types of denominations inserted into the VM, and their corresponding quantities greater than or equal to 0
	 * @param change		the types of denominations returned by the VM as change, and their corresponding quantities greater than or equal to 0
     * @param order			the order object, contains the user's order
	 */
	private void sellRegularItems(
		VM_Regular vm,
		Money duplicate,
		Money payment,
		Money change,
		Order order )
	{
		Scanner sc = new Scanner(System.in);
		String input;
		boolean transactionIsValid = true; // initially true
		boolean orderConfirmed = true; // intially true
        double paymentTotal = 0;
		double orderTotal = 0;
		double cashReservesTotal = 0;
		double changeDue = 0;
		int calorieTotal = 0;
		
		
		/* order is made blank */
		order = new Order();
		
		/* display VM's initial stock */
		vm.displayAllItems();
	
		System.out.print("\n\n");

		/* Asks user for their order */
		promptOrder(vm, order);

		/* Asks user for their payment */
		promptPayment(payment.getDenominations());
		
		/* creates a copy of the set of denominations currently in the VM */
		duplicateDenominations( vm, duplicate, change );
		
		/* calculates the total amount of cash reserves currently in the VM */
		cashReservesTotal = vm.getCurrentMoney().getTotalMoney();
		
		/* calculates payment total */
		for(String s : payment.getDenominations().keySet())
			paymentTotal += payment.getDenominations().get(s)*Money.getStrToVal().get(s);
		
		/* calculates order total */
        orderTotal = order.getTotalCost();
		
		/* calculates calorie total */
		calorieTotal = order.getTotalCalories();
		
		/* calculates change due */
		changeDue = paymentTotal - orderTotal;
		
		
		/* display all transaction information */
		System.out.println();
		System.out.println("\nCash Reserves Total: " + FORMAT.format(cashReservesTotal) + " PHP");
		System.out.println("Order Total: " + orderTotal + " PHP");
		System.out.println("Payment Received: " + paymentTotal + " PHP");
		System.out.println("Change Due: " + changeDue + " PHP");
		System.out.println("Calorie Total: " + calorieTotal + " kCal\n");
		
		
		/* asks user to confirm or cancel order */
		System.out.print("Continue with order (\033[1;33mEnter Y to confirm, any other key to discontinue order\033[0m)? : ");
		input = sc.next();
		if( input.equalsIgnoreCase("Y") && orderTotal != 0 )
			orderConfirmed = true;
		else
			orderConfirmed = false;
		System.out.print("\n");
		
		
		/* TRANSACTION VALIDATION */
		transactionIsValid = validateTransaction( vm, order, duplicate, paymentTotal, orderTotal, cashReservesTotal, changeDue );
		
		/* decides whether to proceed with transaction or not */
		if( transactionIsValid && orderConfirmed )
			displayTransactionProceed(vm, duplicate.getDenominations(), payment.getDenominations(), change.getDenominations(), order);
		else
            displayFailedOrDiscontinue(orderConfirmed, transactionIsValid, payment.getDenominations(), change.getDenominations());
		
		
		vm.displayAllItems();
		System.out.println();
		
		
		/* clearing payment tray */
		for( String s : payment.getDenominations().keySet() )
			payment.getDenominations().put(s, 0);
		
		
		/* display change */
		System.out.println("\nCHANGE RETURNED:");
		for(Map.Entry<String, Integer> m : change.getDenominations().entrySet() )
		System.out.println(" " + m.getValue() + " " + m.getKey());
		System.out.print("\n\n");
		
		sc = null;
	}
	
	protected boolean validateTransaction(
		VM_Regular vm,
		Order order,
		Money duplicate,
		double paymentTotal,
		double orderTotal,
		double cashReservesTotal,
		double changeDue )
	{
		boolean transactionIsValid = true; // assumed true
		boolean changeIsPossible;
		
		changeIsPossible = deductChange(changeDue, duplicate.getDenominations());
		
		if( !hasEnoughStock(vm, order) ) {
			transactionIsValid = false;
			System.out.println("\033[1;38;5;202mm-ERROR: INSUFFICIENT STOCK\033[0m");
		}
		if( paymentTotal < orderTotal ) {
			transactionIsValid = false;
			System.out.println("\033[1;38;5;202m-ERROR: INSUFFICIENT PAYMENT\033[0m");
		}
		if ( cashReservesTotal < orderTotal && !changeIsPossible ) {
			transactionIsValid = false;
			System.out.println("\033[1;38;5;202m-ERROR: NOT ENOUGH MONEY RESERVES\033[0m");
		}
		if( changeDue >= 0 && !changeIsPossible ) {
			transactionIsValid = false;
			System.out.println("\033[1;38;5;202m-ERROR: CANNOT RETURN CHANGE, INSERT EXACT AMOUNT\033[0m");
		}
		if(orderTotal == 0)
			transactionIsValid = true;
		
		return transactionIsValid;
	}
	
	
	protected void duplicateDenominations(
		VM_Regular vm,
		Money duplicate,
		Money change )
	{
		/* duplicating cash reserves of VM, while setting change to zero */
		for(String s : vm.getCurrentMoney().getDenominations().keySet()) {
			duplicate.getDenominations().put(s, vm.getCurrentMoney().getDenominations().get(s));
			change.getDenominations().put(s, 0);
		}
	}
	

	/**
	 * Checks whether the VM has sufficient stock
	 * of all ordered items
	 *
	 * @param order the item containing the list of items to be released from the VM,
					including how many of each should be released
	 * @return true if VM's stock contains all required items, false otherwise
	 */
	protected boolean hasEnoughStock(VM_Regular vm, Order order) {
		int i;
		VM_Slot[] slots = vm.getSlots();
		LinkedHashMap<String, Integer> orders;

		orders = order.getPendingOrder();

		boolean stockHasRequiredQuantities = true; // initially true
		for( String s :orders.keySet() )
		{
			for(i = 0; i < slots.length; i++)
				if( s.equalsIgnoreCase( slots[i].getSlotItemName() ) )
					/* if the current slot does not hold the required quantity of its item */
					if( !( slots[i].hasEnoughStock( orders.get(s) ) ) )
					{
						stockHasRequiredQuantities = false;
						break;
					}
		}		
		
		return stockHasRequiredQuantities;
	}
    
    /**
	 * Iteratively deducts coins/bills from a duplicate of the current set of coins/bills,
	 * in order to meet a specified change amount
	 *
	 * @param amt the amount of change that must be met by the VM's cash reserves
	 * @param duplicate a duplicate of the VM's cash reserves
	 * @return true if deduction leads to zero or extremely close to zero, false otherwise
	 */
	protected boolean deductChange(double amt, LinkedHashMap<String, Integer> duplicate)
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
	 * This helper method sets up the console based interaction with the user.
	 * It would then setup his/her order depending on his/her comments
	 * 
	 * @param order the ordered items
	 */
	private void promptOrder(VM_Regular vm, Order order)
	{	
		String input;
		String inputQty;
		int slotNum;
		int qty;

		VM_Slot[] slots;

		Scanner sc = new Scanner(System.in);

		slots =  vm.getSlots();
		/* order request while loop prompting*/
		while(true)
		try
		{
			System.out.print("What would you like to order (\033[1;33mEnter 'Y' to confirm/finish\033[0m)? \033[1;32m<slot num> <qty>\033[0m\n>> ");
			input = sc.next();
			if( input.equalsIgnoreCase("Y") )
				break;
			inputQty = sc.next();
					
				
			slotNum = Integer.parseInt(input);
			qty = Integer.parseInt(inputQty);
			
			// only when selected slot num is within range, this will trigger to add that order
			if( slotNum >= 1 && slotNum <= slots.length )
				if(order.addOrder(slots[slotNum-1], qty))
					System.out.println("\033[1;32m-ADDED TO ORDER\033[0m");
				else
				{
					order.getPendingOrder().remove(slots[slotNum-1].getSlotItemName());
					System.out.println("\033[1;38;5;202m-ERROR: INSUFFICENT STOCK OR NO SLOT HOLDS THIS ITEM. ENTER A DIFF. SLOT NUM/QUANTITY.\033[0m");
				}
			else
				System.out.println("\033[1;38;5;202m-ERROR: SLOT NUM OUT OF BOUNDS\033[0m");
		}
		catch(NumberFormatException e)
		{
			System.out.println("\033[1;38;5;202m-ERROR: NOT PARSABLE TO INT, Please enter slot number\033[0m");
		}

		sc = null;
	}


	/**
	 * This helper method would help prompt the payment the user wishes to give to the machine
	 * 
	 * @param payment the payment where the user would store his/her denominations as payment
	 */
	protected void promptPayment(LinkedHashMap<String, Integer> payment)
	{
		String input;
		String inputQty;
		double denom;
		int qty;

		Scanner sc = new Scanner(System.in);

		/* payment while loop prompting */
		System.out.println();
		while(true)
		try
		{
			System.out.print("Insert payment (\033[1;33mEnter 'Y' to confirm/finish\033[0m): \033[1;32m<bill/coin num> <qty>\033[0m\n>> ");
			input = sc.next();
			if( input.equalsIgnoreCase("Y") )
				break;
			inputQty = sc.next();
			
			denom = Double.parseDouble(input);
			qty = Integer.parseInt(inputQty);
					
			if( Money.getValToStr().get(denom) != null )
				payment.put(Money.getValToStr().get(denom), qty);
			else
				System.out.println("\033[1;38;5;202m-ERROR: DENOMINATION DOES NOT EXIST\033[0m");	
		}
		catch (NumberFormatException e)
		{
			System.out.println("\033[1;38;5;202m-ERROR: DENOMINATION DOES NOT EXIST\033[0m");	
		}

		sc = null;
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
    protected void displayFailedOrDiscontinue(boolean orderConfirmed, 
                                            boolean transactionIsValid, 
                                            LinkedHashMap<String, Integer> payment,
		                                    LinkedHashMap<String, Integer> change)
    {
        if( !orderConfirmed )
            System.out.println("\nTRANSACTION DISCONTINUED------------------------");
        else if( !transactionIsValid )
            System.out.println("\n\033[1;38;5;202mTRANSACTION FAILS------------------------\033[0m");
        /* returns payment to change tray */
        for( String s : payment.keySet() )
            change.put( s, payment.get(s) );
        /* sets payment tray back to 0 */
        for( String s : payment.keySet() )
            payment.put( s, 0 );
    }


    /**
     * This helper method provides for when the transaction is valid and the user chooses to proceed with it.
	 * 
     * @param duplicate		a duplicate set of the VM's current denominations	 
	 * @param payment		the types of denominations inserted into the VM, and their corresponding quantities greater than or equal to 0
	 * @param change		the types of denominations returned by the VM as change, and their corresponding quantities greater than or equal to 0
     * @param order			the order object, contains the user's order
     */
    protected void displayTransactionProceed(
		VM_Regular vm,
		LinkedHashMap<String, Integer> duplicate,
        LinkedHashMap<String, Integer> payment,
        LinkedHashMap<String, Integer> change,
        Order order)
    {

        VM_Slot[] slots;
        int currAmt;
        int i;


        slots = vm.getSlots();
        System.out.println("\n\033[1;32mTRANSACTION PROCEEDS--------------------------\033[0m");

        for(String itemName : order.getPendingOrder().keySet())
            for(i = 0; i < slots.length; i++)
                if( itemName.equalsIgnoreCase( slots[i].getSlotItemName() ) )
                {
                    currAmt = order.getPendingOrder().get(itemName);

                    // Check max amount should be dispensed if order was greater than 10
                    if(currAmt > slots[i].getMAX())
					{
                        System.out.println("Dispensing: " +  slots[i].getMAX() + " \033[1;33m" + slots[i].getSlotItemName() + "\033[0m");
						slots[i].releaseStock( currAmt );
					}
                    // Dispenses the item amount wished
                    else
					{
                        System.out.println("Dispensing: " +  currAmt + " \033[1;33m" + slots[i].getSlotItemName() + "\033[0m");
						slots[i].releaseStock( currAmt );
					}
                }
                    
        vm.releaseStock(order);


        /* computes for the change tray values based on the original cash reserves and the subtracted cash reserve duplicate */
        for( String s : change.keySet() )
        {
			if(order.getTotalCost() == 0 || vm.getCurrentMoney().getDenominations().get(s) - duplicate.get(s) < 0)
				change.put( s, duplicate.get(s) );
			else
				change.put( s, vm.getCurrentMoney().getDenominations().get(s) - duplicate.get(s) );
		}    
        
		
        /* updates the cash reserves */
        vm.getCurrentMoney().setDenominations(duplicate);
        vm.getCurrentMoney().acceptDenominations(payment);
        
        /* sets payment tray back to zero */
        for( String s : payment.keySet() )
            payment.put( s, 0 );
        vm.getOrderHistory().add(order);
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
			item = new Cheese("Cheese", 40.00, 15);
							
		else if( s.equalsIgnoreCase("Cocoa") )
			item = new Cocoa("Cocoa", 20.00, 4);
							
		else if( s.equalsIgnoreCase("Cream") )
			item = new Cream("Cream", 18.00, 5);
							
		else if( s.equalsIgnoreCase("Egg") )
			item = new Egg("Egg", 12.00, 35);
							
		else if( s.equalsIgnoreCase("Kangkong") )
			item = new Kangkong("Kangkong", 10.00, 2);
							
		else if( s.equalsIgnoreCase("Cornstarch") ) 
			item = new Cornstarch("Cornstarch", 13.00, 2);
							
		else if( s.equalsIgnoreCase("Milk") )
			item = new Milk("Milk", 99.00, 20);
							
		else if( s.equalsIgnoreCase("Tofu") )
			item = new Tofu("Tofu", 5.00, 3);
							
		else if( s.equalsIgnoreCase("Salt") )
			item = new Salt("Salt", 5.00, 1);
							
		else if( s.equalsIgnoreCase("Sugar") )
			item = new Sugar("Sugar", 5.00, 30);
							
		else if( s.equalsIgnoreCase("Chicken") )
			item = new Chicken("Chicken", 150.00, 42);
							
		else if( s.equalsIgnoreCase("BBQ") )
			item = new BBQ("BBQ", 5.00, 1);
							
		else if( s.equalsIgnoreCase("Flour") )
			item = new Flour("Flour", 5.00, 1);
		
		return item;
	}

 

    /** Format constant that would help format labels of each item prices or computations*/
	protected static final DecimalFormat FORMAT = new DecimalFormat("0.00");
}