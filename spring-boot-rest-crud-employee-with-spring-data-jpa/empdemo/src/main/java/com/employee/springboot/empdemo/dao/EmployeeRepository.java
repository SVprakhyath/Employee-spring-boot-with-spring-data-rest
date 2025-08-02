package com.employee.springboot.empdemo.dao;

import com.employee.springboot.empdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    // No additional methods are needed as JpaRepository provides basic CRUD operations
}
