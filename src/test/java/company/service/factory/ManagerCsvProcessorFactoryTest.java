package company.service.factory;

import company.model.Employee;
import company.service.parser.EmployeeCsvParser;
import company.service.parser.FileParser;
import company.service.processor.EmployeeFileProcessor;
import company.service.processor.ManagerFileProcessor;
import company.validator.ManagerValidator;
import company.validator.ManagerValidatorFacade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ManagerCsvProcessorFactoryTest {

    ManagerCsvProcessorFactory factory = new ManagerCsvProcessorFactory();

    @Test
    void whenCreatingEmployeeFileProcessor_thenReturnManagerFileProcessor() {
        EmployeeFileProcessor employeeFileProcessor = factory.createEmployeesFileProcessor();
        Assertions.assertEquals(ManagerFileProcessor.class, employeeFileProcessor.getClass());
    }

    @Test
    void whenCreatingFileParser_thenReturnEmployeeCsvParser() {
        FileParser<Employee> fileParser = factory.createParser();
        Assertions.assertEquals(EmployeeCsvParser.class, fileParser.getClass());
    }

    @Test
    void whenCreatingManagerValidator_thenReturnManagerValidatorFacade() {
        ManagerValidator managerValidator = factory.createValidator();
        Assertions.assertEquals(ManagerValidatorFacade.class, managerValidator.getClass());
    }
}