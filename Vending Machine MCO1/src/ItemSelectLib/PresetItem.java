package ItemSelectLib;
import java.util.LinkedHashSet;
public enum PresetItem
{
    CHEESE("/Pics/cheese.png"),
    COCOA("/Pics/cocoa-bean.png"),
    CREAM("/Pics/cream.png"),
    EGG("/Pics/egg.png"),
    KANGKONG("/Pics/kangkong.png"),
    MILK("/Pics/milk.png"),
    SALT("/Pics/salt.png"),
    SUGAR("/Pics/sugar.png"),
    CORNSTARCH("/Pics/cornstarch.png"),
    TOFU("/Pics/tofu.png"),
    CHICKEN("/Pics/chicken-leg.png"),
    BBQ("/Pics/bbq.png"),
    FLOUR("/Pics/flour.png"),
    SOY_SAUCE("/Pics/soy-sauce.png"),
    CHILI("/Pics/chili.png");

    private final String imagePath;

    PresetItem(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return this.imagePath;
    }
}






