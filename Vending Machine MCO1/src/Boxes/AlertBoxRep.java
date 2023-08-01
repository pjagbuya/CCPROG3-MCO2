package Boxes;

public class AlertBoxRep extends AlertBox
{
    public AlertBoxRep(String title, String message)
    {
        super();
        super.display(title, message);
        super.setDisplayed(false);
    }
}
