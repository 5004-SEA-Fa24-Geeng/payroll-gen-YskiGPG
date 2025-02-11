package student;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BuilderTest {

    @Test
    void testReadEmployees() throws IOException {
        List<IEmployee> employees = Builder.readEmployees("resources/employees.csv");

        assertNotNull(employees);
        assertFalse(employees.isEmpty());

        IEmployee firstEmployee = employees.get(0);
        assertEquals("Luffy", firstEmployee.getName());
        assertEquals("s192", firstEmployee.getID());
    }

    @Test
    void testReadTimeCards() throws IOException {
        List<ITimeCard> timeCards = Builder.readTimeCards("resources/time_cards.csv");

        assertNotNull(timeCards);
        assertFalse(timeCards.isEmpty());

        ITimeCard firstTimeCard = timeCards.get(0);
        assertEquals("s192", firstTimeCard.getEmployeeID());
        assertEquals(45.0, firstTimeCard.getHoursWorked(), 0.01);
    }
}
