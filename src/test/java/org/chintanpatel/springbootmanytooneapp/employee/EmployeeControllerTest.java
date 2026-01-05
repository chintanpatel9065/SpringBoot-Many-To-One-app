package org.chintanpatel.springbootmanytooneapp.employee;

import org.chintanpatel.springbootmanytooneapp.department.Department;
import org.chintanpatel.springbootmanytooneapp.department.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee;
    private Department department;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        department = new Department(1L, "IT");
        employee = new Employee(1L, "John Doe", "john@example.com", "1234567890",
                LocalDate.now(), new BigDecimal("50000"), "johndoe", "password123", department);
    }

    @Test
    void shouldListEmployees() throws Exception {
        when(employeeService.getAllEmployeeList()).thenReturn(Arrays.asList(employee));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-list"))
                .andExpect(model().attributeExists("employees"));
    }

    @Test
    void shouldSearchEmployees() throws Exception {
        when(employeeService.searchEmployeeByNameAndDepartment("John")).thenReturn(Arrays.asList(employee));

        mockMvc.perform(get("/employees").param("keyword", "John"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-list"))
                .andExpect(model().attributeExists("employees"));
    }

    @Test
    void shouldShowCreateEmployeeForm() throws Exception {
        when(departmentService.getAllDepartmentList()).thenReturn(Arrays.asList(department));

        mockMvc.perform(get("/employees/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-form"))
                .andExpect(model().attributeExists("employee"))
                .andExpect(model().attributeExists("departments"));
    }

    @Test
    void shouldInsertEmployee() throws Exception {
        when(employeeService.isEmailExist(anyString())).thenReturn(false);
        when(employeeService.isUsernameExist(anyString())).thenReturn(false);

        mockMvc.perform(post("/employees/insertOrUpdateEmployee")
                        .flashAttr("employee", new Employee(null, "Jane Doe", "jane@example.com", "0987654321",
                                LocalDate.now(), new BigDecimal("60000"), "janedoe", "password123", department)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"))
                .andExpect(flash().attribute("successMessage", "Employee Created Successfully"));
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        mockMvc.perform(post("/employees/insertOrUpdateEmployee")
                        .flashAttr("employee", employee))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"))
                .andExpect(flash().attribute("successMessage", "Employee Updated Successfully"));
    }

    @Test
    void shouldGetEmployeeForEdit() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employee));
        when(departmentService.getAllDepartmentList()).thenReturn(Arrays.asList(department));

        mockMvc.perform(get("/employees/getEmployee/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-form"))
                .andExpect(model().attribute("employee", employee))
                .andExpect(model().attributeExists("departments"));
    }

    @Test
    void shouldDeleteEmployee() throws Exception {
        mockMvc.perform(get("/employees/deleteEmployee/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"))
                .andExpect(flash().attribute("successMessage", "Employee Deleted Successfully"));
    }
}
