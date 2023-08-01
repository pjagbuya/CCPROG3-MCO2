package Models;

public enum CookingMessages
{
	/* These come first, if they come at all */
	CHEESE("Cheese", "Melting cheese"),
    COCOA("Cocoa", "Powderizing cocoa"),
    CREAM("Cream", "Whipping the cream"),
    EGG("Egg", "Breaking eggs"),
    MILK("Milk", "Pouring milk"),
    SUGAR("Sugar", "Sweetening with sugar"),
    TOFU("Tofu", "Sauteing tofu"),
    CHICKEN("Chicken", "Deep-frying chicken bits"),
    BBQ("BBQ", "Unleashing BBQ flavor"),
    SOY_SAUCE("Soy Sauce", "Spattering soy sauce"),
    CHILI("Chili", "Spicing it up with red chili"),
	
	/* Then these */
	FLOUR("Flour", "Adding water and flour"),
	CORNSTARCH("Cornstarch", "Releasing cornstarch"),
	KANGKONG("Kangkong", "Cutting kangkong leaves"),
	
	/* Finally these */
	MIXING("Mixing", "Mixing batter"),
	FRYING("Frying", "Frying chips"),
	SALT("Salt", "Seasoning with salt"),
	PACKAGING("Packaging", "Pouring chips into bag"),
	DONE("Done", "Your Special Item is Ready!");
	
	
    private final String name;
    private final String message;
	
	
    CookingMessages(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return this.name.toUpperCase();
    }

    public String getMessage() {
        return this.message;
    }
}

