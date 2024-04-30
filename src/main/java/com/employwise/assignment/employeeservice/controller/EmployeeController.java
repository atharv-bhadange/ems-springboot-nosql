package com.employwise.assignment.employeeservice.controller;

import com.employwise.assignment.employeeservice.bean.Result;
import com.employwise.assignment.employeeservice.model.Employee;
import com.employwise.assignment.employeeservice.service.EmployeeService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee) {
        Result<?> result;

        try {

            Employee isDataExist = employeeService.isDataExist(employee);
            if (isDataExist != null) {
                result = new Result<>("Employee already exists", false);
                return new ResponseEntity<>(result, HttpStatus.CONFLICT);
            }

            String id = employeeService.createEmployee(employee);
            result = new Result<>(id, "Employee created successfully", true);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            result = new Result<>("Internal Server Error " + e, false);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        Result<?> result;

        try {

            Employee existingEmployee = employeeService.getEmployeeById(id);
            if (existingEmployee == null) {
                result = new Result<>("Employee not found", false);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }

            employee.setId(id);
            Employee updatedEmployee = employeeService.updateEmployee(employee);
            if (updatedEmployee == null) {
                result = new Result<>("Employee not found", false);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }

            result = new Result<>("Employee updated successfully", true);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result = new Result<>("Internal Server Error", false);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        Result<?> result;

        Employee existingEmployee = employeeService.getEmployeeById(id);
        if (existingEmployee == null) {
            result = new Result<>("Employee not found", false);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        Boolean isDeleted = employeeService.deleteEmployee(id);
        if (!isDeleted) {
            result = new Result<>("Employee not found", false);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        result = new Result<>("Employee deleted successfully", true);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String id) {
        Result<?> result;

        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            result = new Result<>("Employee not found", false);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // get all employees with pagination and sorting
    @GetMapping("/employee")
    public ResponseEntity<?> getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        Result<?> result;

        Page<Employee> employeesPage = employeeService.getAllEmployees(page, size, sortBy);
        if (employeesPage.isEmpty()) {
            result = new Result<>("No employees found", false);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        // convert the page to a list
        ArrayList<Employee> employees = new ArrayList<>(employeesPage.getContent());

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/employee/{id}/manager/{level}")
    public ResponseEntity<?> getNthLevelManager(@PathVariable String id, @PathVariable int level) {
        Result<?> result;

        Employee employee = employeeService.getNthLevelManager(id, level);
        if (employee == null) {
            result = new Result<>("Employee not found or the level is high", false);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

}