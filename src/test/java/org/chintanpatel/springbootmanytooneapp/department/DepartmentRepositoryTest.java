package org.chintanpatel.springbootmanytooneapp.department;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void shouldSaveDepartment() {
        Department department = new Department(null, "IT");
        Department savedDepartment = departmentRepository.save(department);

        assertThat(savedDepartment).isNotNull();
        assertThat(savedDepartment.getDepartmentId()).isGreaterThan(0);
        assertThat(savedDepartment.getDepartmentName()).isEqualTo("IT");
    }

    @Test
    void shouldFindDepartmentById() {
        Department department = new Department(null, "HR");
        Department savedDepartment = departmentRepository.save(department);

        Optional<Department> foundDepartment = departmentRepository.findById(savedDepartment.getDepartmentId());

        assertThat(foundDepartment).isPresent();
        assertThat(foundDepartment.get().getDepartmentName()).isEqualTo("HR");
    }

    @Test
    void shouldReturnTrueWhenDepartmentExistsByName() {
        Department department = new Department(null, "Finance");
        departmentRepository.save(department);

        boolean exists = departmentRepository.existsByDepartmentName("Finance");

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseWhenDepartmentDoesNotExistByName() {
        boolean exists = departmentRepository.existsByDepartmentName("NonExistent");

        assertThat(exists).isFalse();
    }

    @Test
    void shouldDeleteDepartment() {
        Department department = new Department(null, "Admin");
        Department savedDepartment = departmentRepository.save(department);

        departmentRepository.deleteById(savedDepartment.getDepartmentId());

        Optional<Department> foundDepartment = departmentRepository.findById(savedDepartment.getDepartmentId());
        assertThat(foundDepartment).isNotPresent();
    }
}
