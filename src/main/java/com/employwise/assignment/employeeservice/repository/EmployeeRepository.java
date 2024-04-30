package com.employwise.assignment.employeeservice.repository;

import com.employwise.assignment.employeeservice.model.Employee;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface EmployeeRepository extends CouchbaseRepository<Employee, String> {
    // implement findAll method for pagination and sorting
    Page<Employee> findAll(Pageable pageable);

    Employee findByEmail(String email);

    Optional<Employee> findById(String id);
}
