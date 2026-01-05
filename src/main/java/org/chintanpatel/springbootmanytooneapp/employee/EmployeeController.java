package org.chintanpatel.springbootmanytooneapp.employee;

import jakarta.validation.Valid;
import org.chintanpatel.springbootmanytooneapp.department.Department;
import org.chintanpatel.springbootmanytooneapp.department.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    // Create a method for listing employees

    @GetMapping("/employees")
    public String listEmployee(@RequestParam(required = false)String keyword, Model model) {
        List<Employee>employees;
        if(keyword == null || keyword.isEmpty()) {
            employees = employeeService.getAllEmployeeList();
        } else {
            employees = employeeService.searchEmployeeByNameAndDepartment(keyword);
        }
        model.addAttribute("employees", employees);
        return "employee/employee-list";
    }

    // Create a method for creating a new employee form

    @GetMapping("/employees/new")
    public String createEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getAllDepartmentList());
        return "employee/employee-form";
    }

    // Create a method for inserting or updating an employee

    @PostMapping("/employees/insertOrUpdateEmployee")
    public String insertOrUpdateEmployee(@Valid @ModelAttribute("employee")Employee employee, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if(bindingResult.hasErrors()) {
            List<Department> departments = departmentService.getAllDepartmentList();
            model.addAttribute("departments", departments);
            return "employee/employee-form";
        }
        if (employee.getEmployeeId() != null) {
            employee.setEmployeeId(employee.getEmployeeId());
            employeeService.addEmployee(employee);
            redirectAttributes.addFlashAttribute("successMessage", "Employee Updated Successfully");
        } else {
            if(employeeService.isEmailExist(employee.getEmail())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Email Already Exists");
                return "redirect:/employees/new";
            }
            if(employeeService.isUsernameExist(employee.getUsername())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Username Already Exists");
                return "redirect:/employees/new";
            }
            employeeService.addEmployee(employee);
            redirectAttributes.addFlashAttribute("successMessage", "Employee Created Successfully");
        }
        return "redirect:/employees";
    }

    // Create a method for getting an employee by id

    @GetMapping("/employees/getEmployee/{employeeId}")
    public String getEmployee(@PathVariable Long employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.getAllDepartmentList());
        return "employee/employee-form";
    }

    // Create a method for deleting an employee by id

    @GetMapping("/employees/deleteEmployee/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployeeById(employeeId);
        redirectAttributes.addFlashAttribute("successMessage", "Employee Deleted Successfully");
        return "redirect:/employees";
    }
}
