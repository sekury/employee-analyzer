package company.service.factory;

import company.service.processor.EmployeeFileProcessor;

public interface EmployeesFileProcessorFactory {
    EmployeeFileProcessor createEmployeesFileProcessor();
}
