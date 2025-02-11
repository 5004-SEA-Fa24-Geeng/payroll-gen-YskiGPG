package student;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HourlyEmployeeTest {

    @Test
    void getName() {
        HourlyEmployee employee = new HourlyEmployee("Luffy", "s192", 30.00, 20000.00, 4530.00, 0);
        assertEquals("Luffy", employee.getName());
    }

    @Test
    void getID() {
        HourlyEmployee employee = new HourlyEmployee("Luffy", "s192", 30.00, 20000.00, 4530.00, 0);
        assertEquals("s192", employee.getID());
    }

    @Test
    void getPayRate() {
        HourlyEmployee employee = new HourlyEmployee("Luffy", "s192", 30.00, 20000.00, 4530.00, 0);
        assertEquals(30.00, employee.getPayRate());
    }

    @Test
    void getEmployeeType() {
        HourlyEmployee employee = new HourlyEmployee("Luffy", "s192", 30.00, 20000.00, 4530.00, 0);
        assertEquals("HOURLY", employee.getEmployeeType());
    }

    @Test
    void getYTDEarnings() {
        HourlyEmployee employee = new HourlyEmployee("Luffy", "s192", 30.00, 20000.00, 4530.00, 0);
        assertEquals(20000.00, employee.getYTDEarnings());
    }

    @Test
    void getYTDTaxesPaid() {
        HourlyEmployee employee = new HourlyEmployee("Luffy", "s192", 30.00, 20000.00, 4530.00, 0);
        assertEquals(4530.00, employee.getYTDTaxesPaid());
    }

    @Test
    void getPretaxDeductions() {
        HourlyEmployee employee = new HourlyEmployee("Luffy", "s192", 30.00, 20000.00, 4530.00, 100);
        assertEquals(100.00, employee.getPretaxDeductions());
    }

    @Test
    void runPayroll_NormalHours() {
        HourlyEmployee employee = new HourlyEmployee("Luffy", "s192", 30.00, 20000.00, 4530.00, 0);
        IPayStub payStub = employee.runPayroll(40);  // No overtime

        assertNotNull(payStub);
        assertEquals(928.20, payStub.getPay(), 0.01);  // Net pay after tax
        assertEquals(271.80, payStub.getTaxesPaid(), 0.01);  // Taxes paid
    }

    @Test
    void runPayroll_Overtime() {
        HourlyEmployee employee = new HourlyEmployee("Luffy", "s192", 30.00, 20000.00, 4530.00, 0);
        IPayStub payStub = employee.runPayroll(45);  // 5 hours overtime

        assertNotNull(payStub);
        assertEquals(1102.24, payStub.getPay(), 0.01);  // Net pay after tax and deductions
        assertEquals(322.76, payStub.getTaxesPaid(), 0.01);  // Taxes paid
    }

    @Test
    void runPayroll_NegativeHours() {
        HourlyEmployee employee = new HourlyEmployee("Luffy", "s192", 30.00, 20000.00, 4530.00, 0);
        IPayStub payStub = employee.runPayroll(-5);  // Negative hours should return null

        assertNull(payStub);
    }

    @Test
    void toCSV() {
        HourlyEmployee employee = new HourlyEmployee("Luffy", "s192", 30.00, 20000.00, 4530.00, 0);
        String expectedCSV = "HOURLY,Luffy,s192,30.00,0.00,20000.00,4530.00";
        assertEquals(expectedCSV, employee.toCSV());
    }
}
