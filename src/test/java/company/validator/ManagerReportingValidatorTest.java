package company.validator;

import company.model.Employee;
import company.model.Manager;
import company.model.ManagerBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ManagerReportingValidatorTest {

    ManagerReportingValidator reportingValidator = new ManagerReportingValidator();

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void whenShortReportingLine_thenNoMessage(int reportLength) {
        var managerTree = prepareManagerTree();
        Optional<String> message = reportingValidator.validate(reportLength, managerTree);
        assertTrue(message.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, Integer.MAX_VALUE})
    void whenLongReportingLine_thenReturnMessage(int reportLength) {
        var managerTree = prepareManagerTree();

        Optional<String> message = reportingValidator.validate(reportLength, managerTree);

        assertTrue(message.isPresent());
        int expectedDiff = reportLength - 4;

        assertEquals("Employee{id=1, firstName='John', lastName='Doe', salary=100.0, managerId=null} reporting line is longer by " + expectedDiff, message.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1})
    void whenInvalidReportingLine_thenThrowException(int reportLength) {
        var managerTree = prepareManagerTree();
        assertThrows(IllegalArgumentException.class, () -> reportingValidator.validate(reportLength, managerTree));
    }

    private static Manager prepareManagerTree() {
        return ManagerBuilder.withEmployees(List.of(new Employee(1, "John", "Doe", 100))).build();
    }
}