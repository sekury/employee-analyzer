package company.service.processor;

import company.model.Employee;
import company.model.Manager;
import company.service.parser.FileParser;
import company.validator.ManagerValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerFileProcessorTest {
    @Mock
    FileParser<Employee> parser;

    @Mock
    ManagerValidator validator;

    @InjectMocks
    ManagerFileProcessor managerFileProcessor;

    @Test
    void whenCsvFileProvided_thenProcessManagersData() {
        var employees = List.of(new Employee(1, "John", "Doe", 100));
        when(parser.parseFile(Mockito.anyString())).thenReturn(employees);
        String fileName = "employees.csv";

        managerFileProcessor.processEmployeesFile(fileName);

        verify(parser, only()).parseFile(fileName);
        verify(validator, only()).validate(any(Manager.class));
    }
}