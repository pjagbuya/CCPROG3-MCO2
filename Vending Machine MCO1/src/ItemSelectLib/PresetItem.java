package ItemSelectLib;
import java.util.LinkedHashSet;
public enum PresetItem
{
    CHEESE("Cheese", "/Pics/cheese.png", 1),
    COCOA("Cocoa", "/Pics/cocoa-bean.png", 0),
    CREAM("Cream", "/Pics/cream.png", 0),
    EGG("Egg", "/Pics/egg.png", 1),
    KANGKONG("Kangkong", "/Pics/kangkong.png", 1),
    MILK("Milk", "/Pics/milk.png", 1),
    SALT("Salt", "/Pics/salt.png", 0),
    SUGAR("Sugar", "/Pics/sugar.png", 0),
    CORNSTARCH("Cornstarch", "/Pics/cornstarch.png", 0),
    TOFU("Tofu", "/Pics/tofu.png", 1),
    CHICKEN("Chicken", "/Pics/chicken-leg.png", 1),
    BBQ("BBQ", "/Pics/bbq.png", 0),
    FLOUR("Flour", "/Pics/flour.png", 0),
    SOY_SAUCE("Soy Sauce", "/Pics/soy-sauce.png", 0),
    CHILI("Chili", "/Pics/chili.png", 0);

    private final String name;
    private final String imagePath;
    private final int isIndependent;

    PresetItem(String name, String imagePath, int isIndependent) {
        this.name = name;
        this.imagePath = imagePath;
        this.isIndependent = isIndependent;
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
}






