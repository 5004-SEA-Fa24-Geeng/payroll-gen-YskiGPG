package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SalaryEmployee implements IEmployee{
    private final String name;                // Employee's name
    private final String id;                  // Employee's ID
    private final double payRate;             // Hourly pay rate
    private double ytdEarnings;               // YTD earnings
    private double ytdTaxesPaid;              // YTD taxes paid
    private final double pretaxDeductions;    // Pretax deductions for the employee

    public SalaryEmployee(String name, String id, double payRate, double ytdEarnings, double ytdTaxesPaid, double pretaxDeductions) {
        this.name = name;
        this.id = id;
        this.payRate = payRate;
        this.ytdEarnings = ytdEarnings;
        this.ytdTaxesPaid = ytdTaxesPaid;
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

    // Get the type of employee, which is "SALARY" in this case
    @Override
    public String getEmployeeType() {
        return "SALARY";
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
     * Runs the payroll for the employee's salary.
     * Salary is divided into 24 pay periods.
     * Deducts pretax deductions, applies taxes, and returns a PayStub.
     *
     * @param hoursWorked Irrelevant, but included to maintain method signature.
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


        // Annual salary divided into 24 bi-monthly payments
        BigDecimal grossPay = payRateBD.divide(BigDecimal.valueOf(24), 2, RoundingMode.HALF_UP);

        BigDecimal netPayBeforeTax = grossPay.subtract(pretaxDeductionsBD);
        BigDecimal taxes = netPayBeforeTax.multiply(BigDecimal.valueOf(0.2265))
                .setScale(2, RoundingMode.HALF_UP);  // rounding up 2 decimals
        BigDecimal finalNetPay = netPayBeforeTax.subtract(taxes)
                .setScale(2, RoundingMode.HALF_UP);  // rounding up 2 decimals

        // Update ytd earnings and taxes paid
        ytdEarnings += finalNetPay.doubleValue();
        ytdTaxesPaid += taxes.doubleValue();

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
        return String.format("SALARY,%s,%s,%.2f,%.2f,%.2f,%.2f", name, id, payRate, pretaxDeductions, ytdEarnings, ytdTaxesPaid);
    }
}
