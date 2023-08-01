package ItemSelectLib;
import java.net.URL;
import java.util.LinkedHashSet;
public enum PresetItem
{
    CHEESE("Cheese", "/Pics/cheese.png", 1, 40.00, 15),
    COCOA("Cocoa", "/Pics/cocoa-bean.png", 0, 20.00, 4),
    CREAM("Cream", "/Pics/cream.png", 0, 18.00, 5),
    EGG("Egg", "/Pics/egg.png", 1, 12.00, 35),
    KANGKONG("Kangkong", "/Pics/kangkong.png", 1, 10.00, 2),
    MILK("Milk", "/Pics/milk.png", 1, 99.00, 20),
    SALT("Salt", "/Pics/salt.png", 0, 5.00, 1),
    SUGAR("Sugar", "/Pics/sugar.png", 0, 5.00, 30),
    CORNSTARCH("Cornstarch", "/Pics/cornstarch.png", 0, 13.00, 2),
    TOFU("Tofu", "/Pics/tofu.png", 1, 5.00, 3),
    CHICKEN("Chicken", "/Pics/chicken-leg.png", 1, 150.00, 42),
    BBQ("BBQ", "/Pics/bbq.png", 0, 5.00, 1),
    FLOUR("Flour", "/Pics/flour.png", 0, 5.00, 1);
    // SOY_SAUCE("Soy Sauce", "/Pics/soy-sauce.png", 0, 0.00, 0),
    // CHILI("Chili", "/Pics/chili.png", 0, 0.00, 0);

    private final String name;
    private final String imagePath;
    private final int isIndependent;
    private final double price;
    private final int calories;

    PresetItem(String name, String imagePath, int isIndependent, double price, int calories) {
        this.name = name;
        this.imagePath = imagePath;
        this.isIndependent = isIndependent;
        this.price = price;
        this.calories = calories;
    }

    public String getName() {
        return this.name.toUpperCase();
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public int getIsIndependent() {
        return this.isIndependent;
    }

    public double getPrice() {
        return this.price;
    }

    public int getCalories() {
        return this.calories;
    }
    public static URL getImageUrlByName(String name) {
        for (PresetItem item : PresetItem.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return PresetItem.class.getResource(item.getImagePath());
            }
        }
        return null; 
    }
}






