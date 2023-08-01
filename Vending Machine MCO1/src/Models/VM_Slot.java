package Models;

import java.util.ArrayList;



/** The class VM_Slot represents a slot of the vending machine.
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  */
public class VM_Slot {


    /**
     * Instantiates a slot and initializes its capacity.
     * 
     * @param capacity the maximum number of items that this slot can hold
     */
    public VM_Slot(int capacity){

        storedProfit = 0.0;
        slotItemName = null;
        items = null;
		copy_stockCount = 0;
		price = 0.0;
		
		slotItemSold = 0;
		
        if(capacity >= 10)
            MAX = capacity;
        else
            MAX = 10;
    }
	
	
   /**
     * Instructs slot to hold a different item, or even another of the same item
     * 
     * @param givenItem the new item replacing the slot's original item type
     */
    public void replaceStock(VM_Item givenItem)
    {	
        if(givenItem != null)
        {
			items = new ArrayList<VM_Item>();
			items.add( givenItem );
			slotItemName = new String(givenItem.getItemName());
		}
		
		repriceItem( givenItem.getItemPrice() );
    }
	
	
   /**
     * Checks whether this slot contains the desired number of its item.
     * 
     * @param qty the desired number of pieces of the item
     * @return true if slot contain the desired quantity of items, false otherwise
     */
	public boolean hasEnoughStock(int qty) {
		if( items != null &&
			getSlotItemStock() >= 0 &&
			qty >= 0 &&
			qty <= getSlotItemStock() )
			return true;
		return false;
	}
	
	
   /**
     * Computes for that fraction of the total cost
     * contributed by the desired number of items from this slot.
     * 
     * @param qty the desired quantity of items
     * @return the total cost of desired number of items from this slot
     */
	public double computePartialCost(int qty) 
   	 {
	        double sum = 0.0;
			sum = getPrice() * qty;
	        return sum;
	}

	
   /**
     * Tells slot to release a certain quantity of its item.
     * 
     * @param qty the number of items to be released from this slot
     * @return the item released
     */
    public VM_Item releaseStock()
    {
		int i;
		VM_Item releasedItem = null;
		/* Only release stock if there is enough of it to release */
        if( items != null && hasEnoughStock(1) )
        {
			releasedItem = items.remove(0);
            storedProfit += getPrice();
            slotItemSold++;
        }
		return releasedItem;
    }
	
	
    /**
     * Returns the name of this slot.
     * 
     * @return the String name of the slot
     */
    public String getSlotItemName() 
    {
        return slotItemName;
    }


    /**
     * Returns the stock count of items in the slot.
     * 
     * @return the number of items currently in this slot
     */
    public int getSlotItemStock()
	{
		if( items != null )
			return items.size();
		return 0;
    }

    
   /**
     * Returns the maximum number of items that this slot can hold.
     * 
     * @return the maximum capacity of this slot
     */
    public int getMAX() {
        return MAX;
    }

	
    /**
     * Adds the given item if it has
     * the same name as the currently held item,
     * but sets the stock count and replaces the currently held item
     * with givenItem if the two items have different names.
     * 
	 * @param givenItem the given type of item to be added
     * @param qty the quantity of objects
     */
    public void addItemStock( VM_Item givenItem )
    {
        
        
        if( givenItem == null ) // Error that there was no stock added
            System.out.println("\033[1;38;5;202mERROR! no stocks/item is detected\033[0m");
        // State and excess and return it
        else if( 1 + getSlotItemStock() > MAX && givenItem.getItemName().equalsIgnoreCase(slotItemName) ) {
            System.out.println("You had an excess of " + 1 + " " +givenItem.getItemName() + " while we were stocking. Returning...");
            System.out.println("\033[1;33m" + "Press and Enter Any Key To Continue..." + "\033[0m");
        }

        // If slot was initialized empty, proceed to put in stock
	if( items == null || getSlotItemStock() == 0 )
            replaceStock( givenItem );
		// Skips conditional construct if restocker is empty
	else if( givenItem == null );
        // If the slot is not empty, then proceed to add the stock
        else if( givenItem.getItemName().equalsIgnoreCase(slotItemName) && (getSlotItemStock()+1) <= MAX )
	{
		givenItem.setPrice( getPrice() );
		items.add( givenItem );
	}
        // if this slot already has an item, but has a different name
        else
            warnReplace( givenItem );
    }


   /**
     * Sets the price of the item instances held by this slot.
     * 
     * @param amt the new price of the item(s)
     */
	public void repriceItem(double amt)
	{
		int i;
		if(amt < 0.5) // minimum price of an item is 50 Cents
			amt = 0.5;
		for(i = 0; i < items.size(); i++)
			items.get(i).setPrice(amt);
		price = amt;
	}
	
	 
   /**
     * Returns how many items have been sold
     * by this slot since last restocking.
     * 
     * @return the number of items sold by this slot since last restocking
     */
    public int getSlotItemSold() {
        return slotItemSold;
    }

	
   /**
     * Sets the number of items sold
     * by this slot since the last restocking.
     * 
     * @param slotItemSold the new number of items sold by this slot since last restocking
     */
	public void setSlotItemSold(int slotItemSold) {
		this.slotItemSold = slotItemSold;
	}
	

	/**
	 * Returns the amount of profit generated
	 * by this slot since last restocking.
	 *
	 * @return the profit made by the slot thus far
	 */
    public double getStoredProfit() {
        return storedProfit;
    }
	
	
	/**
	 * Resets the stored profit record back to 0.0
	 *
	 *
	 */
    public void clearStoredProfit() {
        storedProfit = 0.0;
    }
	
	
    /**
     * Informs user of restocking conflict,
     * asks user to choose whether or not to replace currently stocked item(s)
     * with the new givenItem.
     * 
     * @param givenItem the new item to be held by this slot
     */
	/*
    private void warnReplace( VM_Item givenItem )
    {
        System.out.println("\033[1;33mConflict with another type of item\033[0m, will you be replacing this stock of " + slotItemName + 
                            " with " + givenItem.getItemName() + ". (Y/N)");
        
        // Only proceed to replace when user agrees
        if(sc.nextLine().equalsIgnoreCase("Y"))
        {
            System.out.println("Replaced " + slotItemName + " with " + givenItem.getItemName());
            replaceStock( givenItem );
        }
    }
	*/
	
	/** 
 	 * Returns the price of the item(s) in this slot.
 	 *
   	 * @return the price
     	 */
	public double getPrice() { return price; }

	/**
 	 * Returns the items currently held by this slot.
   	 *
     	 * @return the stack of items held by this slot
       	 */
	public ArrayList<VM_Item> getItems() { return items; }
	

    	/** the number of items sold by this slot since last restocking or repricing */
	private int slotItemSold;
	/** the slot's item name */
	private String slotItemName;
	/** profit that this slot has collected */
	private double storedProfit;
	/** max capacity of this slot */
	private final int MAX;
	/** the per piece price of the items */
	private double price;
	/** the items in this slot */
	private ArrayList<VM_Item> items;
}
