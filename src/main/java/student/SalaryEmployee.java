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

        // Annual salary divided into 24 bi-monthly payments (Gross Pay)
        BigDecimal grossPay = payRateBD.divide(BigDecimal.valueOf(24), 10, RoundingMode.HALF_UP);  // 保留更多小数位以减少中间误差

        // Deduct pretax deductions
        BigDecimal netPayBeforeTax = grossPay.subtract(pretaxDeductionsBD);

        // Total taxes are 22.65% of net pay before tax
        BigDecimal taxes = netPayBeforeTax.multiply(BigDecimal.valueOf(0.2265));

        // Final net pay after deducting taxes
        BigDecimal finalNetPay = netPayBeforeTax.subtract(taxes);

        // 最后统一舍入到两位小数
        finalNetPay = finalNetPay.setScale(2, RoundingMode.HALF_UP);
        taxes = taxes.setScale(2, RoundingMode.HALF_UP);

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
        return String.format("SALARY,%s,%s,%.2f,%.2f,%.2f,%.2f", name, id, payRate, pretaxDeductions, ytdEarnings, ytdTaxesPaid);
    }
}
