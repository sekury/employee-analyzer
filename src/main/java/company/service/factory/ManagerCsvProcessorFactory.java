package company.service.factory;

import company.model.Employee;
import company.service.parser.EmployeeCsvParser;
import company.service.parser.FileParser;
import company.service.processor.EmployeeFileProcessor;
import company.service.processor.ManagerFileProcessor;
import company.validator.ManagerReportingValidator;
import company.validator.ManagerSalaryValidator;
import company.validator.ManagerValidator;
import company.validator.ManagerValidatorFacade;

public class ManagerCsvProcessorFactory implements EmployeesFileProcessorFactory {

    @Override
    public EmployeeFileProcessor createEmployeesFileProcessor() {
        return new ManagerFileProcessor(createParser(), createValidator());
    }

    protected FileParser<Employee> createParser() {
        return new EmployeeCsvParser();
    }

    protected ManagerValidator createValidator() {
        return new ManagerValidatorFacade(createSalaryValidator(), createReportingValidator());
    }

    protected ManagerSalaryValidator createSalaryValidator() {
        return new ManagerSalaryValidator();
    }

    protected ManagerReportingValidator createReportingValidator() {
        return new ManagerReportingValidator();
    }
}
