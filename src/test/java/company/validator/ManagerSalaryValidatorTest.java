package company.validator;

import company.model.Employee;
import company.model.ManagerBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManagerSalaryValidatorTest {

    ManagerSalaryValidator salaryValidator = new ManagerSalaryValidator();

    @Test
    void whenEmptySubordinates_thenNoMessage() {
        var managerTree = ManagerBuilder.withEmployees(List.of(
                new Employee(1, "John", "Doe", 100)
        )).build();

        Optional<String> message = salaryValidator.validate(managerTree);

        assertTrue(message.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(doubles = {120, 130, 140, 150})
    void whenSalaryIsValid_thenNoMessage(double salary) {
        Optional<String> message = validateManagerSalary(salary);

        assertTrue(message.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(doubles = {100, 110, 119.99})
    void whenSalaryIsSmall_thenReturnMessage(double salary) {
        Optional<String> message = validateManagerSalary(salary);

        assertTrue(message.isPresent());
        double expectedDiff = 120.0 - salary;
        assertEquals("Employee{id=1, firstName='John', lastName='Doe', salary=" +
                salary + ", managerId=null} earns less by " + expectedDiff, message.get());
    }

    @ParameterizedTest
    @ValueSource(doubles = {150.01, 170})
    void whenSalaryIsBig_thenReturnMessage(double salary) {
        Optional<String> message = validateManagerSalary(salary);

        assertTrue(message.isPresent());
        double expectedDiff = salary - 150;
        assertEquals("Employee{id=1, firstName='John', lastName='Doe', salary=" +
                salary + ", managerId=null} earns more by " + expectedDiff, message.get());
    }

    private Optional<String> validateManagerSalary(double salary) {
        var managerTree = ManagerBuilder.withEmployees(List.of(
                new Employee(1, "John", "Doe", salary),
                new Employee(2, "Jack", "Doe", 100, 1),
                new Employee(3, "Jane", "Doe", 100, 1)
        )).build();

        return salaryValidator.validate(managerTree);
    }
}