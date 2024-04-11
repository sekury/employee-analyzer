package company.validator;

import company.model.Employee;
import company.model.Manager;

import java.util.Optional;

public class ManagerSalaryValidator {

    private static final double MAX_PERCENT_DIFF = 1.5;
    private static final double MIN_PERCENT_DIFF = 1.2;
    private static final String EARNS_LESS_BY = " earns less by ";
    private static final String EARNS_MORE_BY = " earns more by ";

    public Optional<String> validate(Manager manager) {
        if (!manager.subordinates().isEmpty()) {
            double averageSalary = calculateAverageSubordinatesSalary(manager);
            return validateMinSalary(manager.employee(), averageSalary)
                    .or(() -> validateMaxSalary(manager.employee(), averageSalary));
        }
        return Optional.empty();
    }

    private Optional<String> validateMinSalary(Employee employee, double averageSalary) {
        double minSalary = averageSalary * MIN_PERCENT_DIFF;
        double salaryDiff = minSalary - employee.salary();
        return salaryDiff > 0
                ? Optional.of(employee + EARNS_LESS_BY + salaryDiff)
                : Optional.empty();
    }

    private Optional<String> validateMaxSalary(Employee employee, double averageSalary) {
        double maxSalary = averageSalary * MAX_PERCENT_DIFF;
        double salaryDiff = employee.salary() - maxSalary;
        return salaryDiff > 0
                ? Optional.of(employee + EARNS_MORE_BY + salaryDiff)
                : Optional.empty();
    }

    private double calculateAverageSubordinatesSalary(Manager manager) {
        return manager.subordinates().stream()
                .map(Manager::employee)
                .mapToDouble(Employee::salary)
                .average()
                .orElse(0);
    }
}
