package Boxes;

public class AlertBoxRep extends AlertBox
{
    public AlertBoxRep(String title, String message)
    {
        super();
        super.display(title, message);
        super.setDisplayed(false);
    }
    public AlertBoxRep(String title, String message, int size)
    {
        super();
        super.display(title, message, size);
        super.setDisplayed(false);
    }
}
