package company.model;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ManagerBuilder {

    private final Map<Integer, Employee> employeeMap;

    private ManagerBuilder(Map<Integer, Employee> employeeMap) {
        this.employeeMap = employeeMap;
    }

    /**
     * Creates manager builder initialized with a collection of employees
     */
    public static ManagerBuilder withEmployees(Collection<Employee> employees) {
        return new ManagerBuilder(getEmployeeMap(employees));
    }

    /**
     * Constructs manager tree and returns CEO as a root
     */
    public Manager build() {
        return buildManager(getCeo(), getSubordinatesMap());
    }

    /**
     * Create managers recursively
     */
    private Manager buildManager(Employee employee, Map<Employee, List<Employee>> subordinatesMap) {
        Set<Manager> subordinates = Optional.ofNullable(subordinatesMap.get(employee))
                .orElse(Collections.emptyList())
                .stream()
                .map(subordinate -> buildManager(subordinate, subordinatesMap))
                .collect(Collectors.toSet());

        return new Manager(employee, subordinates);
    }

    private Map<Employee, List<Employee>> getSubordinatesMap() {
        return employeeMap.values()
                .stream()
                .filter(Employee::isNotCeo)
                .collect(Collectors.groupingBy(this::getManager, Collectors.toUnmodifiableList()));
    }

    private Employee getManager(Employee employee) {
        return Optional.ofNullable(employeeMap.get(employee.managerId()))
                .orElseThrow(() -> new NoSuchElementException("Manager with id=" + employee.managerId() + " not found"));
    }

    private Employee getCeo() {
        List<Employee> ceoList = employeeMap.values()
                .stream()
                .filter(Employee::isCeo)
                .toList();

        if (ceoList.isEmpty()) {
            throw new NoSuchElementException("CEO not found");
        }

        if (ceoList.size() > 1) {
            throw new RuntimeException("More than one CEO found");
        }

        return ceoList.getFirst();
    }

    private static Map<Integer, Employee> getEmployeeMap(Collection<Employee> employees) {
        return employees.stream().collect(Collectors.toUnmodifiableMap(Employee::id, Function.identity()));
    }
}
