package company.validator;

import company.model.Manager;

public class ManagerValidatorFacade implements ManagerValidator {

    private final ManagerSalaryValidator salaryValidator;
    private final ManagerReportingValidator reportingValidator;

    public ManagerValidatorFacade(ManagerSalaryValidator salaryValidator,
                                  ManagerReportingValidator reportingValidator) {
        this.salaryValidator = salaryValidator;
        this.reportingValidator = reportingValidator;
    }

    /**
     * Recursively traverses managers tree and checks managers data
     */
    @Override
    public void validate(Manager manager) {
        validate(0, manager);
    }

    private void validate(int managerLevel, Manager manager) {
        salaryValidator.validate(manager).ifPresent(System.out::println);
        reportingValidator.validate(managerLevel, manager).ifPresent(System.out::println);
        manager.subordinates().forEach(node -> validate(managerLevel + 1, node));
    }
}
