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

    public double getTaxesPaid() {
        return this.taxesPaid;
    }

//    @Override
//    public String toCSV() {
//        return String.format("%s,%.2f,%.2f,%.2f,%.2f", name, netPay, taxesPaid, ytdEarnings, ytdTaxesPaid);
//    }
//}

    @Override
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s",
                name,
                formatDecimal(netPay),
                formatDecimal(taxesPaid),
                formatDecimal(ytdEarnings),
                formatDecimal(ytdTaxesPaid));
    }

    // define a Format decimal method
    private String formatDecimal(double value) {
        if (value * 10 == Math.floor(value * 10)) {
            return String.format("%.1f", value);  // 如果是整数，保留一位小数（如 1661.0）
        } else {
            return String.format("%.2f", value);  // 否则保留两位小数
        }
    }
}

