package org.chintanpatel.springbootmanytooneapp.employee;

import org.chintanpatel.springbootmanytooneapp.department.Department;
import org.chintanpatel.springbootmanytooneapp.department.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department(null, "IT");
        department = departmentRepository.save(department);
    }

    @Test
    void shouldSaveEmployee() {
        Employee employee = new Employee(null, "John Doe", "john@example.com", "1234567890",
                LocalDate.now(), new BigDecimal("50000"), "johndoe", "password123", department);
        Employee savedEmployee = employeeRepository.save(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getEmployeeId()).isGreaterThan(0);
        assertThat(savedEmployee.getName()).isEqualTo("John Doe");
    }

    @Test
    void shouldReturnTrueWhenEmailExists() {
        Employee employee = new Employee(null, "John Doe", "john@example.com", "1234567890",
                LocalDate.now(), new BigDecimal("50000"), "johndoe", "password123", department);
        employeeRepository.save(employee);

        boolean exists = employeeRepository.existsByEmail("john@example.com");

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnTrueWhenUsernameExists() {
        Employee employee = new Employee(null, "John Doe", "john@example.com", "1234567890",
                LocalDate.now(), new BigDecimal("50000"), "johndoe", "password123", department);
        employeeRepository.save(employee);

        boolean exists = employeeRepository.existsByUsername("johndoe");

        assertThat(exists).isTrue();
    }

    @Test
    void shouldFindEmployeesByNameOrDepartment() {
        Employee employee = new Employee(null, "John Doe", "john@example.com", "1234567890",
                LocalDate.now(), new BigDecimal("50000"), "johndoe", "password123", department);
        employeeRepository.save(employee);

        List<Employee> resultByName = employeeRepository.findByNameAndDepartment("John");
        List<Employee> resultByDept = employeeRepository.findByNameAndDepartment("IT");

        assertThat(resultByName).hasSize(1);
        assertThat(resultByDept).hasSize(1);
    }

    @Test
    void shouldDeleteEmployee() {
        Employee employee = new Employee(null, "John Doe", "john@example.com", "1234567890",
                LocalDate.now(), new BigDecimal("50000"), "johndoe", "password123", department);
        Employee savedEmployee = employeeRepository.save(employee);

        employeeRepository.deleteById(savedEmployee.getEmployeeId());

        Optional<Employee> foundEmployee = employeeRepository.findById(savedEmployee.getEmployeeId());
        assertThat(foundEmployee).isNotPresent();
    }
}
