package company.model;

public record Employee(int id, String firstName, String lastName, double salary, Integer managerId) {

    public Employee(int id, String firstName, String lastName, double salary) {
        this(id, firstName, lastName, salary, null);
    }

    public boolean isCeo() {
        return managerId == null;
    }

    public boolean isNotCeo() {
        return !isCeo();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", managerId=" + managerId +
                '}';
    }
}
