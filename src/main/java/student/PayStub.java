package student;

public class PayStub implements IPayStub {
    // PayStub is immutable. Use final to avoid changing.
    private final String name;              // the name of the employee
    private final double netPay;            // the net pay fo the employee
    private final double taxesPaid;         // the taxes paid by the employee
    private final double ytdEarnings;       // the YTD earnings of the employee
    private final double ytdTaxesPaid;      // the YTD taxes paid by the employee

    public PayStub(String name, double netPay, double taxesPaid, double ytdEarnings, double ytdTaxesPaid) {
        this.name = name;
        this.netPay = netPay;
        this.taxesPaid = taxesPaid;
        this.ytdEarnings = ytdEarnings;
        this.ytdTaxesPaid = ytdTaxesPaid;
    }

    @Override
    public double getPay() {
        return this.netPay;
    }

    public double getTaxesPaid(){
        return this.taxesPaid;
    }

    @Override
    public String toCSV() {
        return String.format("%s,%.2f,%.2f,%.2f,%.2f", name, netPay, taxesPaid, ytdEarnings, ytdTaxesPaid);
    }
}
