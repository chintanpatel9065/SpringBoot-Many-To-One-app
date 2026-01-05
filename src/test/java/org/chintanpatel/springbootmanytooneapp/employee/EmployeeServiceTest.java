package org.chintanpatel.springbootmanytooneapp.employee;

import org.chintanpatel.springbootmanytooneapp.department.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department(1L, "IT");
        employee = new Employee(1L, "John Doe", "john@example.com", "1234567890",
                LocalDate.now(), new BigDecimal("50000"), "johndoe", "password123", department);
    }

    @Test
    void shouldAddEmployee() {
        employeeService.addEmployee(employee);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void shouldGetAllEmployees() {
        List<Employee> employees = Arrays.asList(employee, new Employee());
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployeeList();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldGetEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.getEmployeeById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void shouldDeleteEmployeeById() {
        employeeService.deleteEmployeeById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldReturnTrueWhenEmailExists() {
        when(employeeRepository.existsByEmail("john@example.com")).thenReturn(true);

        boolean exists = employeeService.isEmailExist("john@example.com");

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnTrueWhenUsernameExists() {
        when(employeeRepository.existsByUsername("johndoe")).thenReturn(true);

        boolean exists = employeeService.isUsernameExist("johndoe");

        assertThat(exists).isTrue();
    }

    @Test
    void shouldSearchEmployees() {
        when(employeeRepository.findByNameAndDepartment("John")).thenReturn(Arrays.asList(employee));

        List<Employee> result = employeeService.searchEmployeeByNameAndDepartment("John");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("John Doe");
    }
}
