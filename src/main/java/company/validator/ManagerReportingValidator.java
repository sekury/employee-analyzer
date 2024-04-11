package company.validator;

import company.model.Manager;

import java.util.Optional;

public class ManagerReportingValidator {

    private static final int MAX_REPORT_LENGTH = 4;

    public Optional<String> validate(int managerLevel, Manager manager) {
        if (managerLevel < 0) {
            throw new IllegalArgumentException("manager level must not be negative");
        }

        int reportLength = managerLevel - 1; // number of managers between current manager and CEO
        int diff = reportLength - MAX_REPORT_LENGTH;
        return diff > 0
                ? Optional.of(manager.employee() + " reporting line is longer by " + diff)
                : Optional.empty();
    }
}
