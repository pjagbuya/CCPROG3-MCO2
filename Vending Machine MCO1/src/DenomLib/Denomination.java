package DenomLib;

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
    
    private final String name;
    private final double value;
    
    Denomination(String name, double value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public double getValue() {
        return value;
    }

    public static double fromName(String denominationName) {
        for (Denomination denomination : Denomination.values()) {
            if (denomination.getName().equals(denominationName)) 
            {
                return denomination.getValue();
            }
        }
        
        return -1;
    }


    public static String fromValue(double value) {
        for (Denomination denomination : Denomination.values()) {
            if (denomination.getValue() == value) {
                return denomination.getName();
            }
        }
        throw new IllegalArgumentException("Invalid denomination value: " + value);
    }
}