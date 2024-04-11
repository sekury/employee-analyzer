package company.validator;

import company.model.Employee;
import company.model.Manager;
import company.model.ManagerBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerValidatorFacadeTest {

    @Mock
    ManagerReportingValidator reportingValidator;

    @Mock
    ManagerSalaryValidator salaryValidator;

    @InjectMocks
    ManagerValidatorFacade managerValidatorFacade;

    @Test
    void whenAnalyzingSingleManager_thenSingleManagerValidated() {
        var managerTree = ManagerBuilder.withEmployees(List.of(
                new Employee(1, "John", "Doe", 100)
        )).build();
        when(salaryValidator.validate(any())).thenReturn(Optional.empty());
        when(reportingValidator.validate(anyInt(), any())).thenReturn(Optional.empty());

        managerValidatorFacade.validate(managerTree);

        verify(salaryValidator, only()).validate(eq(managerTree));
        verify(reportingValidator, only()).validate(eq(0), eq(managerTree));
    }

    @Test
    void whenAnalyzingMultipleManagers_thenAllManagersValidated() {
        var employee1 = new Employee(1, "1", "Doe", 100);
        var employee12 = new Employee(12, "12", "Doe", 100, 1);
        var employee13 = new Employee(13, "13", "Doe", 100, 1);
        var employee121 = new Employee(121, "121", "Doe", 100, 12);
        var employee131 = new Employee(131, "131", "Doe", 100, 13);
        var managerTree = ManagerBuilder.withEmployees(List.of(
                employee1, employee12, employee13, employee121, employee131
        )).build();

        when(salaryValidator.validate(any())).thenReturn(Optional.empty());
        when(reportingValidator.validate(anyInt(), any())).thenReturn(Optional.empty());

        managerValidatorFacade.validate(managerTree);

        var salaryValidatorCaptor = ArgumentCaptor.forClass(Manager.class);
        verify(salaryValidator, times(5)).validate(salaryValidatorCaptor.capture());
        assertTrue(salaryValidatorCaptor.getAllValues().stream().map(Manager::employee)
                .toList()
                .containsAll(List.of(employee1, employee12, employee13, employee121, employee131)));

        verify(reportingValidator, times(1)).validate(eq(0), any(Manager.class));
        verify(reportingValidator, times(2)).validate(eq(1), any(Manager.class));
        verify(reportingValidator, times(2)).validate(eq(2), any(Manager.class));
    }
}