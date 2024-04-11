package company.validator;

import company.model.Manager;

import java.util.Optional;

public class ManagerReportingValidator {

    private static final int MAX_REPORT_LENGTH = 4;

    public Optional<String> validate(int reportLength, Manager manager) {
        if (reportLength < 0) {
            throw new IllegalArgumentException("reporting length must not be negative");
        }

        int diff = reportLength - MAX_REPORT_LENGTH;
        return diff > 0
                ? Optional.of(manager.employee() + " reporting line is longer by " + diff)
                : Optional.empty();
    }
}
