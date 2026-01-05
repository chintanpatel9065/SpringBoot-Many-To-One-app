package org.chintanpatel.springbootmanytooneapp.employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    void addEmployee(Employee employee);

    List<Employee>getAllEmployeeList();

    Optional<Employee> getEmployeeById(Long employeeId);

    void deleteEmployeeById(Long employeeId);

    boolean isEmailExist(String email);

    boolean isUsernameExist(String username);

    List<Employee>searchEmployeeByNameAndDepartment(String keyword);
}
