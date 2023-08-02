package DenomLib;
/**
 * The enum Denomination is a list of all possible coins and banknotes
 * in the universe of this program.
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public enum Denomination {
    
    ONE_THOUSAND_BILL("One Thousand Bill", 1000.0),
    FIVE_HUNDRED_BILL("Five Hundred Bill", 500.0),
    TWO_HUNDRED_BILL("Two Hundred Bill", 200.0),
    ONE_HUNDRED_BILL("One Hundred Bill", 100.0),
    FIFTY_BILL("Fifty Bill", 50.0),
    TWENTY_BILL("Twenty Bill", 20.0),
    TWENTY_COIN("Twenty Coin", 20.001),
    TEN_COIN("Ten Coin", 10.0),
    FIVE_COIN("Five Coin", 5.0),
    ONE_COIN("One Coin", 1.0),
    TWENTY_FIVE_CENTS("Twenty Five Cents", 0.25),
    FIVE_CENTS("Five Cents", 0.05),
    ONE_CENT("One Cent", 0.01);
    /**name of the item denomination */
    private final String name;
    /**value of the denomination */
    private final double value;
    
    /**
     * Denomination constructor for the enums
     * @param name name of the denomination item
     * @param value vlaue of the item
     */
    Denomination(String name, double value) {
        this.name = name;
        this.value = value;
    }
    
    /**
     * This method gets the string equivalent of the money
     * @return the string name of the denomination item
     */
    public String getName() {
        return name;
    }
    /**
     * gets the value of the bill
     * @return returns the value of the bill
     */
    public double getValue() {
        return value;
    }


    /**
     * Converts the denomination name to its corresponding value
     * @param denominationName the name of the denomination item
     * @return the double value of the item
     */
    public static double fromName(String denominationName) {
        for (Denomination denomination : Denomination.values()) {
            if (denomination.getName().equals(denominationName)) 
            {
                return denomination.getValue();
            }
        }
        
        return -1;
    }

    /**
     * Method convers value to its string equivalent
     * @param value value of the Money
     * @return
     */
    public static String fromValue(double value) {
        for (Denomination denomination : Denomination.values()) {
            if (denomination.getValue() == value) {
                return denomination.getName();
            }
        }
        throw new IllegalArgumentException("Invalid denomination value: " + value);
    }
}