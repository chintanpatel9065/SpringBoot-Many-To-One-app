package org.chintanpatel.springbootmanytooneapp.department;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private Department department;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
        department = new Department(1L, "IT");
    }

    @Test
    void shouldListDepartments() throws Exception {
        when(departmentService.getAllDepartmentList()).thenReturn(Arrays.asList(department));

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-list"))
                .andExpect(model().attributeExists("departments"));
    }

    @Test
    void shouldShowCreateDepartmentForm() throws Exception {
        mockMvc.perform(get("/departments/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-form"))
                .andExpect(model().attributeExists("department"));
    }

    @Test
    void shouldInsertDepartment() throws Exception {
        when(departmentService.isDepartmentExist("IT")).thenReturn(false);

        mockMvc.perform(post("/departments/insertOrUpdateDepartment")
                        .flashAttr("department", new Department(null, "IT")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments"))
                .andExpect(flash().attribute("successMessage", "Department Created Successfully"));
    }

    @Test
    void shouldUpdateDepartment() throws Exception {
        mockMvc.perform(post("/departments/insertOrUpdateDepartment")
                        .flashAttr("department", department))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments"))
                .andExpect(flash().attribute("successMessage", "Department Updated Successfully"));
    }

    @Test
    void shouldReturnFormOnValidationError() throws Exception {
        mockMvc.perform(post("/departments/insertOrUpdateDepartment")
                        .flashAttr("department", new Department(null, "")))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-form"));
    }

    @Test
    void shouldGetDepartmentForEdit() throws Exception {
        when(departmentService.getDepartmentById(1L)).thenReturn(Optional.of(department));

        mockMvc.perform(get("/departments/getDepartment/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-form"))
                .andExpect(model().attribute("department", department));
    }

    @Test
    void shouldDeleteDepartment() throws Exception {
        mockMvc.perform(get("/departments/deleteDepartment/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments"))
                .andExpect(flash().attribute("successMessage", "Department Deleted Successfully"));
    }
}
