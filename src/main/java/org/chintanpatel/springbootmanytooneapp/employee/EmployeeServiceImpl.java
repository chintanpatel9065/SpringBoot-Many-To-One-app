package org.chintanpatel.springbootmanytooneapp.employee;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployeeList() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public void deleteEmployeeById(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public boolean isEmailExist(String email) {
        return employeeRepository.existsByEmail(email);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return employeeRepository.existsByUsername(username);
    }

    @Override
    public List<Employee> searchEmployeeByNameAndDepartment(String keyword) {
        return employeeRepository.findByNameAndDepartment(keyword);
    }
}
