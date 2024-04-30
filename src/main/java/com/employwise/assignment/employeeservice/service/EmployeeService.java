package com.employwise.assignment.employeeservice.service;

import com.employwise.assignment.employeeservice.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.employwise.assignment.employeeservice.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // implement methods to interact with the repository
    public String createEmployee(Employee employee) {
        return employeeRepository.save(employee).getId();
    }

    public Employee getEmployeeById(String id) {

        return employeeRepository.findById(id).orElse(null);
    }

    public Employee updateEmployee(Employee employee) {
        // check if the employee exists
        Employee existingEmployee = getEmployeeById(employee.getId());
        if (existingEmployee == null) {
            return null;
        }
        return employeeRepository.save(employee);
    }

    public Boolean deleteEmployee(String id) {
        // check if the employee exists
        Employee existingEmployee = getEmployeeById(id);
        if (existingEmployee == null) {
            return false;
        }
        employeeRepository.deleteById(id);
        return true;
    }

    public Page<Employee> getAllEmployees(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));

        return employeeRepository.findAll(paging);
    }

    public Employee getNthLevelManager(String id, int level) {
        Employee employee = getEmployeeById(id);

        if (employee == null) {
            return null;
        }

        if (level == 0) {
            return employee;
        }
        if (employee.getReportsTo() == null) {
            return null;
        }
        return getNthLevelManager(employee.getReportsTo(), level - 1);
    }

    public Employee isDataExist(@org.jetbrains.annotations.NotNull Employee reqData) {
        return employeeRepository.findByEmail(reqData.getEmail());
    }
}