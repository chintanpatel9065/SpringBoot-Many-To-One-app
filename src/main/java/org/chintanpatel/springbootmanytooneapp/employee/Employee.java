package org.chintanpatel.springbootmanytooneapp.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chintanpatel.springbootmanytooneapp.department.Department;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @NotEmpty(message = "Please Provide Employee Name")
    @Column(name = "name", nullable = false)
    private String name;

    @Email(message = "Please Provide Valid Email")
    @NotEmpty(message = "Please Provide Email")
    @Column(name = "email", nullable = false)
    private String email;

    @NotEmpty(message = "Please Provide Mobile Number")
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please Provide Joining Date")
    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;

    @DecimalMin(value = "0.01", message = "Salary must be greater than 0.0")
    @NotNull(message = "Please Provide Salary")
    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    @NotEmpty(message = "Please Provide Username")
    @Column(name = "username", nullable = false)
    private String username;

    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    @NotEmpty(message = "Please Provide Password")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "Please Select Department")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
}
