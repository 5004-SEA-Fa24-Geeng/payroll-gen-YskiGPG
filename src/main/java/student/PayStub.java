package student;

/**
 * PayStub represents a payroll statement for an employee.
 * It contains information about the employee's net pay, taxes paid,
 * year-to-date earnings, and year-to-date taxes paid.
 *
 * This class is immutable.
 */
public class PayStub implements IPayStub {
    /** The name of the employee */
    private final String name;

    /** The net pay for the employee */
    private final double netPay;

    /** The taxes paid by the employee */
    private final double taxesPaid;

    /** The year-to-date earnings of the employee */
    private final double ytdEarnings;

    /** The year-to-date taxes paid by the employee */
    private final double ytdTaxesPaid;

    /**
     * Constructs a PayStub with the given details.
     *
     * @param name The name of the employee
     * @param netPay The net pay for the pay period
     * @param taxesPaid The taxes paid for the pay period
     * @param ytdEarnings The year-to-date earnings
     * @param ytdTaxesPaid The year-to-date taxes paid
     */
    public PayStub(String name, double netPay, double taxesPaid, double ytdEarnings, double ytdTaxesPaid) {
        this.name = name;
        this.netPay = netPay;
        this.taxesPaid = taxesPaid;
        this.ytdEarnings = ytdEarnings;
        this.ytdTaxesPaid = ytdTaxesPaid;
    }

    /**
     * Returns the net pay for the pay period.
     *
     * @return The net pay
     */
    @Override
    public double getPay() {
        return this.netPay;
    }

    /**
     * Returns the taxes paid for the pay period.
     *
     * @return The taxes paid
     */
    public double getTaxesPaid() {
        return this.taxesPaid;
    }

    /**
     * Converts the pay stub information to a CSV string.
     * The format is: name,netPay,taxesPaid,ytdEarnings,ytdTaxesPaid
     *
     * @return A CSV string representing the pay stub
     */
    @Override
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s",
                name,
                formatDecimal(netPay),
                formatDecimal(taxesPaid),
                formatDecimal(ytdEarnings),
                formatDecimal(ytdTaxesPaid));
    }

    /**
     * Formats a decimal value to one or two decimal places.
     * If the value is an integer, it keeps one decimal place.
     * Otherwise, it keeps two decimal places.
     *
     * @param value The value to format
     * @return The formatted decimal as a string
     */
    private String formatDecimal(double value) {
        if (value * 10 == Math.floor(value * 10)) {
            return String.format("%.1f", value);
        } else {
            return String.format("%.2f", value);
        }
    }
}
