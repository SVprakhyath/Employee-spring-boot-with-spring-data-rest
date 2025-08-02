package com.employee.springboot.empdemo.dao;

import com.employee.springboot.empdemo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    // define methods to be implemented by the DAO layer
    List<Employee> findAll();

    // find employee by id
    Employee findById(int theId);

    //update employee by id
    Employee save(Employee theEmployee);

    //delete employee by id
    void deleteById(int theId);
}
