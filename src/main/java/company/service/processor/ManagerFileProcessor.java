package company.service.processor;

import company.model.Employee;
import company.model.ManagerBuilder;
import company.service.parser.FileParser;
import company.validator.ManagerValidator;

import java.util.List;

public class ManagerFileProcessor implements EmployeeFileProcessor {

    private final FileParser<Employee> parser;
    private final ManagerValidator validator;

    public ManagerFileProcessor(FileParser<Employee> parser,
                                ManagerValidator validator) {
        this.parser = parser;
        this.validator = validator;
    }

    @Override
    public void processEmployeesFile(String fileName) {
        List<Employee> employees = parser.parseFile(fileName);
        var ceo = ManagerBuilder.withEmployees(employees).build();
        validator.validate(ceo);
    }
}
