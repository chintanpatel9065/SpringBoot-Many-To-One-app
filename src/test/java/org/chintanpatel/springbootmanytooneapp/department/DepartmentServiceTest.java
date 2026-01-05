package org.chintanpatel.springbootmanytooneapp.department;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department(1L, "IT");
    }

    @Test
    void shouldAddDepartment() {
        departmentService.addDepartment(department);
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void shouldGetAllDepartments() {
        List<Department> departments = Arrays.asList(department, new Department(2L, "HR"));
        when(departmentRepository.findAll()).thenReturn(departments);

        List<Department> result = departmentService.getAllDepartmentList();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getDepartmentName()).isEqualTo("IT");
    }

    @Test
    void shouldGetDepartmentById() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Optional<Department> result = departmentService.getDepartmentById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getDepartmentName()).isEqualTo("IT");
    }

    @Test
    void shouldDeleteDepartmentById() {
        departmentService.deleteDepartmentById(1L);
        verify(departmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldReturnTrueWhenDepartmentExists() {
        when(departmentRepository.existsByDepartmentName("IT")).thenReturn(true);

        boolean exists = departmentService.isDepartmentExist("IT");

        assertThat(exists).isTrue();
    }
}
