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
    private final String name;                // Employee's name
    private final String id;                  // Employee's ID
    private final double payRate;             // Hourly pay rate
    private double ytdEarnings;               // YTD earnings
    private double ytdTaxesPaid;              // YTD taxes paid
    private final double pretaxDeductions;    // Pretax deductions for the employee

    public HourlyEmployee(String name, String id, double payRate, double ytdEarnings, double ytdTaxesPaid, double pretaxDeductions) {
        this.name = name;
        this.id = id;
        this.payRate = payRate;
        this.ytdEarnings = BigDecimal.valueOf(ytdEarnings).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.ytdTaxesPaid = BigDecimal.valueOf(ytdTaxesPaid).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.pretaxDeductions = pretaxDeductions;
    }

    // Get the employee's name
    @Override
    public String getName() {
        return name;
    }

    // Get the employee's ID
    @Override
    public String getID() {
        return id;
    }

    // Get the employee's hourly pay rate
    @Override
    public double getPayRate() {
        return payRate;
    }

    // Get the type of employee, which is "HOURLY" in this case
    @Override
    public String getEmployeeType() {
        return "HOURLY";
    }

    // Get the year-to-date earnings
    @Override
    public double getYTDEarnings() {
        return ytdEarnings;
    }

    // Get the year-to-date taxes paid
    @Override
    public double getYTDTaxesPaid() {
        return ytdTaxesPaid;
    }

    // Get the pretax deductions
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
     * @return The PayStub for this pay period.
     */
    @Override
    public IPayStub runPayroll(double hoursWorked) {
        // Skip payroll if negative hours are provided
        if (hoursWorked < 0) {
            return null;
        }

        BigDecimal payRateBD = BigDecimal.valueOf(payRate);
        BigDecimal pretaxDeductionsBD = BigDecimal.valueOf(pretaxDeductions);

        // Regular hours are up to 40, overtime is any additional hours
        BigDecimal regularHours = BigDecimal.valueOf(Math.min(hoursWorked, 40));
        BigDecimal overtimeHours = BigDecimal.valueOf(Math.max(hoursWorked - 40, 0));

        // Gross pay = regular hours * pay rate + overtime (1.5x pay rate)
        BigDecimal grossPay = (regularHours.multiply(payRateBD))
                .add(overtimeHours.multiply(payRateBD).multiply(BigDecimal.valueOf(1.5)));

        // Deduct pretax deductions before applying taxes
        BigDecimal netPayBeforeTax = grossPay.subtract(pretaxDeductionsBD);

        // Total taxes are 22.65% of net pay before tax
        BigDecimal taxes = netPayBeforeTax.multiply(BigDecimal.valueOf(0.2265));

        // Final net pay after deducting taxes
        BigDecimal finalNetPay = netPayBeforeTax.subtract(taxes);

        // 最后统一舍入到两位小数
        grossPay = grossPay.setScale(2, RoundingMode.HALF_UP);
        taxes = taxes.setScale(2, RoundingMode.HALF_UP);
        finalNetPay = finalNetPay.setScale(2, RoundingMode.HALF_UP);

        // Update ytd earnings and taxes paid with final rounding
        ytdEarnings = BigDecimal.valueOf(ytdEarnings).add(finalNetPay).setScale(2, RoundingMode.HALF_UP).doubleValue();
        ytdTaxesPaid = BigDecimal.valueOf(ytdTaxesPaid).add(taxes).setScale(2, RoundingMode.HALF_UP).doubleValue();

        // Return a PayStub for this pay period
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
        return String.format("HOURLY,%s,%s,%.2f,%.2f,%.2f,%.2f", name, id, payRate, pretaxDeductions, ytdEarnings, ytdTaxesPaid);
    }
}
