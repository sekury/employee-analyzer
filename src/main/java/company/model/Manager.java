package company.model;

import java.util.Set;

/**
 * Represents a manager tree node or leaf
 */
public record Manager(Employee employee, Set<Manager> subordinates) {
    public Manager {
        subordinates = Set.copyOf(subordinates);
    }
}
