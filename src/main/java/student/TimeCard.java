package student;

/**
 * TimeCard class represents the hours worked by an employee.
 * Implements the ITimeCard interface.
 */
public class TimeCard implements ITimeCard {
    private final String employeeID;   // Employee's ID
    private final double hoursWorked;  // Hours worked in the pay period

    /**
     * Constructor for TimeCard.
     *
     * @param employeeID  Employee's ID
     * @param hoursWorked Hours worked in the pay period
     */
    public TimeCard(String employeeID, double hoursWorked) {
        this.employeeID = employeeID;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String getEmployeeID() {
        return employeeID;
    }

    @Override
    public double getHoursWorked() {
        return hoursWorked;
    }
}
