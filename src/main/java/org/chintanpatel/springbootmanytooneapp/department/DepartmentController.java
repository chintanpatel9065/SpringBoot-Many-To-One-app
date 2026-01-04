package org.chintanpatel.springbootmanytooneapp.department;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Create a method for listing departments

    @GetMapping("/departments")
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartmentList());
        return "department/department-list";
    }

    // Create a method for creating a new department form

    @GetMapping("/departments/new")
    public String createDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        return "department/department-form";
    }

    // Create a method for inserting or updating a department

    @PostMapping("/departments/insertOrUpdateDepartment")
    public String insertOrUpdateDepartment(@Valid @ModelAttribute("department")Department department, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "department/department-form";
        }
        if (department.getDepartmentId() != null) {
            department.setDepartmentId(department.getDepartmentId());
            departmentService.addDepartment(department);
            redirectAttributes.addFlashAttribute("successMessage", "Department Updated Successfully");
        } else {
            if (departmentService.isDepartmentExist(department.getDepartmentName())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Department Already Exists");
                return "redirect:/departments/new";
            }
            departmentService.addDepartment(department);
            redirectAttributes.addFlashAttribute("successMessage", "Department Created Successfully");
        }
        return "redirect:/departments";
    }

    // Create a method for getting a department by id

    @GetMapping("/departments/getDepartment/{departmentId}")
    public String getDepartment(@PathVariable Long departmentId, Model model) {
        Department department = departmentService.getDepartmentById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department Not Found"));
        model.addAttribute("department", department);
        return "department/department-form";
    }

    // Create a method for deleting a department by id

    @GetMapping("/departments/deleteDepartment/{departmentId}")
    public String deleteDepartment(@PathVariable Long departmentId, RedirectAttributes redirectAttributes) {
        departmentService.deleteDepartmentById(departmentId);
        redirectAttributes.addFlashAttribute("successMessage", "Department Deleted Successfully");
        return "redirect:/departments";
    }
}
