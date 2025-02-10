package student;

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

        // Annual salary divided into 24 bi-monthly payments
        double grossPay = payRate / 24;

        double netPayBeforeTax = grossPay - pretaxDeductions;
        double taxes = netPayBeforeTax * 0.2265;
        double finalNetPay = netPayBeforeTax - taxes;

        // Update ytd earnings and taxes paid
        ytdEarnings += finalNetPay;
        ytdTaxesPaid += taxes;

        // Return a PayStub for this pay period
        return new PayStub(name, finalNetPay, taxes, ytdEarnings, ytdTaxesPaid);
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
