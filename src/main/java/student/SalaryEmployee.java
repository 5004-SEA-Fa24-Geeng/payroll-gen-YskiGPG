package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * SalaryEmployee class represents an employee who is paid a fixed salary.
 *
 * Implements the IEmployee interface and provides methods to:
 *  - Retrieve employee information (name, ID, pay rate, etc.).
 *  - Calculate payroll based on fixed salary divided into 24 periods.
 *  - Update year-to-date earnings and taxes paid.
 *  - Generate a PayStub for each payroll cycle.
 *
 * Taxes are calculated at a total rate of 22.65% on the net pay (after pretax deductions).
 */
public class SalaryEmployee implements IEmployee {
    /** Employee's name */
    private final String name;

    /** Employee's ID */
    private final String id;

    /** Annual salary pay rate */
    private final double payRate;

    /** Year-to-date earnings */
    private double ytdEarnings;

    /** Year-to-date taxes paid */
    private double ytdTaxesPaid;

    /** Pretax deductions for the employee */
    private final double pretaxDeductions;

    /**
     * Constructs a SalaryEmployee with provided details.
     *
     * @param name Employee's name
     * @param id Employee's ID
     * @param payRate Annual salary pay rate
     * @param ytdEarnings Year-to-date earnings
     * @param ytdTaxesPaid Year-to-date taxes paid
     * @param pretaxDeductions Pretax deductions
     */
    public SalaryEmployee(String name, String id, double payRate, double ytdEarnings, double ytdTaxesPaid, double pretaxDeductions) {
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
        return "SALARY";
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
     * Runs the payroll for the employee's salary.
     * Salary is divided into 24 pay periods.
     * Deducts pretax deductions, applies taxes, and returns a PayStub.
     *
     * @param hoursWorked Irrelevant, but included to maintain method signature.
     * @return The PayStub for this pay period.
     */
    @Override
    public IPayStub runPayroll(double hoursWorked) {
        if (hoursWorked < 0) {
            return null;
        }

        BigDecimal payRateBD = BigDecimal.valueOf(payRate);
        BigDecimal pretaxDeductionsBD = BigDecimal.valueOf(pretaxDeductions);

        BigDecimal grossPay = payRateBD.divide(BigDecimal.valueOf(24), 10, RoundingMode.HALF_UP);
        BigDecimal netPayBeforeTax = grossPay.subtract(pretaxDeductionsBD);
        BigDecimal taxes = netPayBeforeTax.multiply(BigDecimal.valueOf(0.2265));
        BigDecimal finalNetPay = netPayBeforeTax.subtract(taxes);

        finalNetPay = finalNetPay.setScale(2, RoundingMode.HALF_UP);
        taxes = taxes.setScale(2, RoundingMode.HALF_UP);

        ytdEarnings = BigDecimal.valueOf(ytdEarnings).add(finalNetPay).setScale(2, RoundingMode.HALF_UP).doubleValue();
        ytdTaxesPaid = BigDecimal.valueOf(ytdTaxesPaid).add(taxes).setScale(2, RoundingMode.HALF_UP).doubleValue();

        return new PayStub(name, finalNetPay.doubleValue(), taxes.doubleValue(), ytdEarnings, ytdTaxesPaid);
    }

    /**
     * Converts the employee's information to a CSV string.
     * The format is: employee_type,name,ID,payRate,pretaxDeductions,YTDEarnings,YTDTaxesPaid
     *
     * @return A CSV string representing the employee.
     */
    @Override
    public String toCSV() {
        return String.format(
                "SALARY,%s,%s,%.2f,%.2f,%.2f,%.2f",
                name, id, payRate, pretaxDeductions, ytdEarnings, ytdTaxesPaid
        );
    }
}
