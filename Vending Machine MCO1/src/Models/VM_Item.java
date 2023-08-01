package Models;

import java.text.DecimalFormat;


/** The class VM_Item represents an item
  * in the vending machine.
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  */
public class VM_Item {

    /**
     * Instantiates an items and initializes its name,
     * price, and calorific value.
     * 
     * @param name string representation of the name of this item
     * @param price price or cost of this item
     * @param calories amount of calories in this item
     */
    public VM_Item(String name, 
                   double price, 
                   int calories)
    {
        itemName = name;
        itemPrice = price;
        itemCalories = calories;   
    }

    
    /**
     * Returns the name of this item
     * 
     * @return the string representation of this item's name
     */
    public String getItemName() 
    {
        return itemName;
    }

    /**
     * Returns the price of this item
     * 
     * @return the price of this item as a floating-point double
     */
    public double getItemPrice() 
    {
        return itemPrice;
    }

    /**
     * Returns the number of calories in this item
     * 
     * @return the amount of calories in this item
     */
    public int getItemCalories() 
    {
        return itemCalories;
    }

    /**
     * Sets the price of this item.
     * 
     * @param amt the new price to be assigned to this item
     */
    public void setPrice(double amt) {
	itemPrice = amt;	
     }
	
	


    /**
     * Overrides the toString() method to return name, price,
     * and calories of this item with proper formating
     * 
     * @return the String containing name, price, and no. of calories in this item
     */
    @Override
    public String toString(){

        return "Name: " + itemName + " (\033[1;32m" + itemName.substring(0, 3) + "\033[0m)" + "\n" +
               "Price: Php " + FORMAT.format(itemPrice) + "\n" +
               "Calories: " + itemCalories + " kCal";
               
    }

	
	

    /** name of the item */
    private String itemName;
    /** price of the item, per piece */
    private double itemPrice;
    /** the amount of calories in this item */
    protected int itemCalories;
    /** Decimal format instance that prices will follow */
    private static final DecimalFormat FORMAT = new DecimalFormat("0.00");

}
