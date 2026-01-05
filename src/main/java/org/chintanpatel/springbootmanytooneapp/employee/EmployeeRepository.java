package org.chintanpatel.springbootmanytooneapp.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("select e from Employee e join e.department d where lower(e.name) like lower(concat('%',:keyword, '%')) or " +
            "lower(d.departmentName) like lower(concat('%',:keyword,'%'))")
    List<Employee>findByNameAndDepartment(@Param("keyword")String keyword);
}