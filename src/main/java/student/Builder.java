package student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Builder {

    /**
     * Reads the employees.csv file and returns a list of IEmployee objects.
     *
     * @param filePath Path to the employees.csv file
     * @return List of IEmployee objects
     * @throws IOException if the file cannot be read
     */
    public static List<IEmployee> readEmployees(String filePath) throws IOException {
        // Read all lines from the employees.csv file
        List<String> employeeLines = Files.readAllLines(Paths.get(filePath));

        // Convert each line to an IEmployee object using Stream API
        return employeeLines.stream()
                .skip(1)        // skip the header
                .map(Builder::buildEmployeeFromCSV)
                .collect(Collectors.toList());
    }

    /**
     * Converts a single CSV line into an IEmployee object.
     *
     * @param csvLine A line from the employees.csv file
     * @return IEmployee object (HourlyEmployee or SalaryEmployee)
     */
    public static IEmployee buildEmployeeFromCSV(String csvLine) {
        String[] parts = csvLine.split(",");

        if (parts.length != 7) {
            // throw an exception
            throw new IllegalArgumentException("Invalid employee record: " + csvLine);
        }

        // Extract employee data from CSV line
        String type = parts[0].trim();
        String name = parts[1].trim();
        String id = parts[2].trim();
        double payRate = Double.parseDouble(parts[3].trim());
        double pretaxDeductions = Double.parseDouble(parts[4].trim());
        double ytdEarnings = Double.parseDouble(parts[5].trim());
        double ytdTaxesPaid = Double.parseDouble(parts[6].trim());

        // Create the appropriate employee type
        switch (type.toUpperCase()) {
            case "HOURLY":
                return new HourlyEmployee(name, id, payRate, ytdEarnings, ytdTaxesPaid, pretaxDeductions);
            case "SALARY":
                return new SalaryEmployee(name, id, payRate, ytdEarnings, ytdTaxesPaid, pretaxDeductions);
            default:
                throw new IllegalArgumentException("Unknown employee type: " + type);
        }
    }

    /**
     * Reads the time_cards.csv file and returns a list of ITimeCard objects.
     *
     * @param filePath Path to the time_cards.csv file
     * @return List of ITimeCard objects
     * @throws IOException if the file cannot be read
     */
    public static List<ITimeCard> readTimeCards(String filePath) throws IOException {
        // Read all lines from the time_cards.csv file
        List<String> timeCardLines = Files.readAllLines(Paths.get(filePath));

        // Convert each line to an ITimeCard object using Stream API
        return timeCardLines.stream()
                .skip(1)        // skip the header
                .map(Builder::buildTimeCardFromCSV)
                .collect(Collectors.toList());
    }

    /**
     * Converts a single CSV line into an ITimeCard object.
     *
     * @param csvLine A line from the time_cards.csv file
     * @return ITimeCard object
     */
    public static ITimeCard buildTimeCardFromCSV(String csvLine) {
        String[] parts = csvLine.split(",");

        // Ensure correct CSV format
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid time card record: " + csvLine);
        }

        // Extract employee ID and hours worked from CSV line
        String id = parts[0].trim();
        double hoursWorked = Double.parseDouble(parts[1].trim());

        // Return a new TimeCard object
        return new TimeCard(id, hoursWorked);
    }
}
