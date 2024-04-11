package company.model;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ManagerBuilderTest {

    @Test
    void whenCollectionIsNull_thenThrowException() {
        assertThrows(NullPointerException.class, () -> ManagerBuilder.withEmployees(null));
    }

    @Test
    void whenCollectionIsEmpty_thenThrowException() {
        NoSuchElementException exception =
                assertThrows(NoSuchElementException.class, () -> ManagerBuilder.withEmployees(List.of()).build());
        assertEquals("CEO not found", exception.getMessage());
    }

    @Test
    void whenWrongManagerId_thenThrowException() {
        var employees = List.of(
                new Employee(1, "John", "Doe", 100),
                new Employee(2, "Jane", "Doe", 100, 3)
        );

        NoSuchElementException exception =
                assertThrows(NoSuchElementException.class, () -> ManagerBuilder.withEmployees(employees).build());
        assertEquals("Manager with id=3 not found", exception.getMessage());
    }

    @Test
    void whenCollectionDoesntHaveCeo_thenThrowException() {
        var employees = List.of(
                new Employee(1, "John", "Doe", 100, 2),
                new Employee(2, "Jane", "Doe", 100, 1)
        );

        NoSuchElementException exception =
                assertThrows(NoSuchElementException.class, () -> ManagerBuilder.withEmployees(employees).build());
        assertEquals("CEO not found", exception.getMessage());
    }

    @Test
    void whenCollectionHasMultipleCeo_thenThrowException() {
        var employees = List.of(
                new Employee(1, "John", "Doe", 100),
                new Employee(2, "Jane", "Doe", 100)
        );

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> ManagerBuilder.withEmployees(employees).build());
        assertEquals("More than one CEO found", exception.getMessage());
    }

    @Test
    void whenCollectionOfManagers_thenBuildManagerTree() {
        Employee ceo = new Employee(123, "Joe", "Doe", 60000);
        Employee martin = new Employee(124, "Martin", "Chekov", 45000, 123);
        Employee bob = new Employee(125, "Bob", "Ronstad", 47000, 123);
        Employee alice = new Employee(300, "Alice", "Hasacat", 50000, 124);
        Employee brett = new Employee(305, "Brett", "Hardleaf", 34000, 300);

        var ceoNode = ManagerBuilder.withEmployees(List.of(ceo, martin, bob, alice, brett)).build();

        // level 0
        assertEquals(ceo, ceoNode.employee());

        // level 1
        var ceoSubordinates = ceoNode.subordinates().stream()
                .map(Manager::employee)
                .sorted(Comparator.comparingInt(Employee::id))
                .toList();

        assertEquals(List.of(martin, bob), ceoSubordinates);

        // level 2
        var martinNode = ceoNode.subordinates().stream()
                .filter(manager -> manager.employee().equals(martin))
                .findFirst()
                .get();

        var martinSubordinates = martinNode.subordinates().stream()
                .map(Manager::employee)
                .toList();

        assertEquals(List.of(alice), martinSubordinates);

        var bobNode = ceoNode.subordinates().stream()
                .filter(manager -> manager.employee().equals(bob))
                .findFirst()
                .get();

        assertTrue(bobNode.subordinates().isEmpty());

        // level 3
        var aliceNode = martinNode.subordinates().stream()
                .findFirst()
                .get();

        var aliceSubordinates = aliceNode.subordinates().stream()
                .map(Manager::employee)
                .toList();

        assertEquals(List.of(brett), aliceSubordinates);

        // level 4
        var brettNode = aliceNode.subordinates().stream()
                .findFirst()
                .get();

        assertTrue(brettNode.subordinates().isEmpty());
    }

}