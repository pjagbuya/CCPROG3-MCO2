package DenomLib;

public class DenominationModel extends Money {
    public DenominationModel()
    {
        super();
    }

    public void addDenominationData(String label, int numLabel)
    {
        super.getDenominations().put(label, numLabel);

    }

}
