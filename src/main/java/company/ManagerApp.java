package company;

import company.service.processor.EmployeeFileProcessor;
import company.service.factory.ManagerCsvProcessorFactory;
import company.service.factory.EmployeesFileProcessorFactory;

public class ManagerApp {

    public static void main(String[] args) {
        if (args.length == 0 || args[0].isBlank()) {
            throw new RuntimeException("File name must be provided");
        }

        EmployeesFileProcessorFactory processorFactory = new ManagerCsvProcessorFactory();
        EmployeeFileProcessor fileProcessor = processorFactory.createEmployeesFileProcessor();
        fileProcessor.processEmployeesFile(args[0]);
    }
}
