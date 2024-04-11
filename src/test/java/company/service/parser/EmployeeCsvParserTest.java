package company.service.parser;

import company.model.Employee;
import company.service.parser.EmployeeCsvParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeCsvParserTest {

    EmployeeCsvParser csvParser = new EmployeeCsvParser();

    @Test
    void whenParsingExistingFile_thenReturnManagersCollection() {
        File file = getFile("test-employees.csv");

        List<Employee> managers = csvParser.parseFile(file.getAbsolutePath());

        managers.sort(Comparator.comparingInt(Employee::id));
        Assertions.assertFalse(managers.isEmpty());
        Assertions.assertEquals(List.of(
                new Employee(123, "Joe", "Doe", 60000),
                new Employee(124, "Martin", "Chekov", 45000, 123),
                new Employee(125, "Bob", "Ronstad", 47000, 123),
                new Employee(300, "Alice", "Hasacat", 50000, 124),
                new Employee(305, "Brett", "Hardleaf", 34000, 300)
        ), managers);
    }

    @Test
    void whenFileDoesntHaveManagers_thenReturnEmptyManagerCollection() {
        File file = getFile("empty-employees.csv");

        List<Employee> managers = csvParser.parseFile(file.getAbsolutePath());

        assertNotNull(managers);
        assertTrue(managers.isEmpty());
    }

    @Test
    void whenFileContainsInvalidHeader_thenThrowException() {
        File file = getFile("invalid-header.csv");

        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> csvParser.parseFile(file.getAbsolutePath()));
        assertEquals("Unexpected file header: Id,lastName,salary,managerId", exception.getMessage());
    }

    @Test
    void whenFileIsEmpty_thenThrowException() {
        File file = getFile("empty-file.csv");

        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> csvParser.parseFile(file.getAbsolutePath()));
        assertEquals("Unexpected file header: null", exception.getMessage());
    }

    @Test
    void whenFileDoesntExist_thenThrowException() {
        assertThrows(RuntimeException.class, () -> csvParser.parseFile("fake-file.csv"));
    }

    private File getFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }
}