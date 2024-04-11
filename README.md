# Employee analyzer
## Description
Reads a CSV file and reports:
- which managers earn less than they should, and by how much
- which managers earn more than they should, and by how much
- which employees have a reporting line which is too long, and by how much

## Requirements
Java 21

## Run application
```
./mvnw package
cd target
java -jar employee-analyzer-1.0-SNAPSHOT.jar ../employees.csv
```

## Example
```
Manager{id=124, firstName='Martin', lastName='Chekov', salary=45000.0, managerId=123} earns less by 12000.0
Manager{id=309, firstName='John', lastName='Doe', salary=45000.0, managerId=308} reporting line is longer by 1
```