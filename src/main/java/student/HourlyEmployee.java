package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Description: HourlyEmployee class represents an employee who is paid based on an hourly rate.
 *
 * Implements the IEmployee interface and provides methods to:
 *  - Retrieve employee information (name, ID, pay rate, etc.).
 *  - Calculate payroll based on hours worked, including overtime pay.
 *  - Update year-to-date earnings and taxes paid.
 *  - Generate a PayStub for each payroll cycle.
 *
 * Overtime is paid at 1.5 times the hourly rate for any hours worked beyond 40 hours per week.
 * Taxes are calculated at a total rate of 22.65% on the net pay (after pretax deductions).
 */
public class HourlyEmployee implements IEmployee {
    /** Employee's name. */
    private final String name;

    /** Employee's ID. */
    private final String id;

    /** Hourly pay rate. */
    private final double payRate;

    /** Year-to-date earnings. */
    private double ytdEarnings;

    /** Year-to-date taxes paid. */
    private double ytdTaxesPaid;

    /** Pretax deductions for the employee. */
    private final double pretaxDeductions;

    /**
     * Constructs an HourlyEmployee with provided details.
     *
     * @param name Employee's name
     * @param id Employee's ID
     * @param payRate Hourly pay rate
     * @param ytdEarnings Year-to-date earnings
     * @param ytdTaxesPaid Year-to-date taxes paid
     * @param pretaxDeductions Pretax deductions
     */
    public HourlyEmployee(String name,
                          String id,
                          double payRate,
                          double ytdEarnings,
                          double ytdTaxesPaid,
                          double pretaxDeductions) {
        this.name = name;
        this.id = id;
        this.payRate = payRate;
        this.ytdEarnings = BigDecimal.valueOf(ytdEarnings).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.ytdTaxesPaid = BigDecimal.valueOf(ytdTaxesPaid).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.pretaxDeductions = pretaxDeductions;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    @Override
    public String getID() {
        return id;
    }

    /** {@inheritDoc} */
    @Override
    public double getPayRate() {
        return payRate;
    }

    /** {@inheritDoc} */
    @Override
    public String getEmployeeType() {
        return "HOURLY";
    }

    /** {@inheritDoc} */
    @Override
    public double getYTDEarnings() {
        return ytdEarnings;
    }

    /** {@inheritDoc} */
    @Override
    public double getYTDTaxesPaid() {
        return ytdTaxesPaid;
    }

    /** {@inheritDoc} */
    @Override
    public double getPretaxDeductions() {
        return pretaxDeductions;
    }

    /**
     * Runs the payroll for the employee based on the hours worked.
     * Calculates gross pay, deducts pretax deductions, applies taxes,
     * and returns a PayStub.
     *
     * @param hoursWorked The number of hours worked in the pay period.
     * @return The PayStub for this pay period, or null if hoursWorked is negative.
     */
    @Override
    public IPayStub runPayroll(double hoursWorked) {
        if (hoursWorked < 0) {
            return null;
        }

        BigDecimal payRateBD = BigDecimal.valueOf(payRate);
        BigDecimal pretaxDeductionsBD = BigDecimal.valueOf(pretaxDeductions);

        BigDecimal regularHours = BigDecimal.valueOf(Math.min(hoursWorked, 40));
        BigDecimal overtimeHours = BigDecimal.valueOf(Math.max(hoursWorked - 40, 0));

        BigDecimal grossPay = regularHours.multiply(payRateBD)
                .add(overtimeHours.multiply(payRateBD).multiply(BigDecimal.valueOf(1.5)));

        BigDecimal netPayBeforeTax = grossPay.subtract(pretaxDeductionsBD);
        BigDecimal taxes = netPayBeforeTax.multiply(BigDecimal.valueOf(0.2265));
        BigDecimal finalNetPay = netPayBeforeTax.subtract(taxes);

        grossPay = grossPay.setScale(2, RoundingMode.HALF_UP);
        taxes = taxes.setScale(2, RoundingMode.HALF_UP);
        finalNetPay = finalNetPay.setScale(2, RoundingMode.HALF_UP);

        ytdEarnings = BigDecimal.valueOf(ytdEarnings).add(finalNetPay).setScale(2, RoundingMode.HALF_UP).doubleValue();
        ytdTaxesPaid = BigDecimal.valueOf(ytdTaxesPaid).add(taxes).setScale(2, RoundingMode.HALF_UP).doubleValue();

        return new PayStub(name, finalNetPay.doubleValue(), taxes.doubleValue(), ytdEarnings, ytdTaxesPaid);
    }

    /**
     * Converts the employee's information to a CSV string.
     * Format: employee_type,name,ID,payRate,pretaxDeductions,YTDEarnings,YTDTaxesPaid
     *
     * @return A CSV string representing the employee.
     */
    @Override
    public String toCSV() {
        return String.format(
                "HOURLY,%s,%s,%.2f,%.2f,%.2f,%.2f",
                name, id, payRate, pretaxDeductions, ytdEarnings, ytdTaxesPaid
        );
    }
}
