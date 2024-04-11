package company.service.parser;

import company.model.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCsvParser implements FileParser<Employee> {

    private final static String CSV_HEADER = "Id,firstName,lastName,salary,managerId";

    /**
     * Parses the .CSV file with employees data.
     *
     * @param fileName path to the .CSV file
     * @return list of employees
     */
    @Override
    public List<Employee> parseFile(String fileName) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            List<Employee> employees = new ArrayList<>();

            String line = reader.readLine();
            if (!CSV_HEADER.equalsIgnoreCase(line)) {
                throw new RuntimeException("Unexpected file header: " + line);
            }

            while ((line = reader.readLine()) != null) {
                Employee employee = parseCsvLine(line);
                employees.add(employee);
            }

            return employees;

        } catch (IOException e) {
            System.err.println("IO exception occurred while parsing managers data");
            throw new RuntimeException(e);
        }
    }

    private Employee parseCsvLine(String line) {
        String[] fields = line.split(",");

        int id = Integer.parseInt(fields[0]);
        String firstName = fields[1];
        String lastName = fields[2];
        double salary = Double.parseDouble(fields[3]);

        // managerId is optional
        Integer managerId = fields.length < 5 ? null : Integer.parseInt(fields[4]);

        return new Employee(id, firstName, lastName, salary, managerId);
    }
}
