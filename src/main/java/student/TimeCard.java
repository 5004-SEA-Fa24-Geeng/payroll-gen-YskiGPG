package student;

/**
 * TimeCard class represents the hours worked by an employee.
 * Implements the ITimeCard interface.
 */
public class TimeCard implements ITimeCard {
    /** Employee's ID. */
    private final String employeeID;
    /** Hours worked in the pay period. */
    private final double hoursWorked;

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
