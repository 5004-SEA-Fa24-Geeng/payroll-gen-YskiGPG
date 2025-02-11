package student;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SalaryEmployeeTest {

    @Test
    void getName() {
        SalaryEmployee employee = new SalaryEmployee("Nami", "s193", 200000.00, 17017.00, 4983.00, 1000);
        assertEquals("Nami", employee.getName());
    }

    @Test
    void getID() {
        SalaryEmployee employee = new SalaryEmployee("Nami", "s193", 200000.00, 17017.00, 4983.00, 1000);
        assertEquals("s193", employee.getID());
    }

    @Test
    void getPayRate() {
        SalaryEmployee employee = new SalaryEmployee("Nami", "s193", 200000.00, 17017.00, 4983.00, 1000);
        assertEquals(200000.00, employee.getPayRate());
    }

    @Test
    void getEmployeeType() {
        SalaryEmployee employee = new SalaryEmployee("Nami", "s193", 200000.00, 17017.00, 4983.00, 1000);
        assertEquals("SALARY", employee.getEmployeeType());
    }

    @Test
    void getYTDEarnings() {
        SalaryEmployee employee = new SalaryEmployee("Nami", "s193", 200000.00, 17017.00, 4983.00, 1000);
        assertEquals(17017.00, employee.getYTDEarnings());
    }

    @Test
    void getYTDTaxesPaid() {
        SalaryEmployee employee = new SalaryEmployee("Nami", "s193", 200000.00, 17017.00, 4983.00, 1000);
        assertEquals(4983.00, employee.getYTDTaxesPaid());
    }

    @Test
    void getPretaxDeductions() {
        SalaryEmployee employee = new SalaryEmployee("Nami", "s193", 200000.00, 17017.00, 4983.00, 1000);
        assertEquals(1000.00, employee.getPretaxDeductions());
    }

    @Test
    void runPayroll() {
        SalaryEmployee employee = new SalaryEmployee("Nami", "s193", 200000.00, 17017.00, 4983.00, 1000);
        IPayStub payStub = employee.runPayroll(0);  // Hours irrelevant for salaried employees

        assertNotNull(payStub);
        assertEquals(5672.33, payStub.getPay(), 0.01);  // Net pay after tax and deductions
        assertEquals(1661.00, payStub.getTaxesPaid(), 0.01);  // Taxes paid
    }

    @Test
    void runPayroll_NegativeHours() {
        SalaryEmployee employee = new SalaryEmployee("Nami", "s193", 200000.00, 17017.00, 4983.00, 1000);
        IPayStub payStub = employee.runPayroll(-10);  // Negative hours should return null

        assertNull(payStub);
    }

    @Test
    void toCSV() {
        SalaryEmployee employee = new SalaryEmployee("Nami", "s193", 200000.00, 17017.00, 4983.00, 1000);
        String expectedCSV = "SALARY,Nami,s193,200000.00,1000.00,17017.00,4983.00";
        assertEquals(expectedCSV, employee.toCSV());
    }
}
