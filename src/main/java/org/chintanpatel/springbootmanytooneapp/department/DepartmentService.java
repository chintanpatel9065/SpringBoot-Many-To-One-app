package org.chintanpatel.springbootmanytooneapp.department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    void addDepartment(Department department);

    List<Department>getAllDepartmentList();

    Optional<Department> getDepartmentById(Long departmentId);

    void deleteDepartmentById(Long departmentId);

    boolean isDepartmentExist(String departmentName);
}
