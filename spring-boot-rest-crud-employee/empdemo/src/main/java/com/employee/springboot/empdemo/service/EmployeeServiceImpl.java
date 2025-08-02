package com.employee.springboot.empdemo.service;

import com.employee.springboot.empdemo.dao.EmployeeDAO;
import com.employee.springboot.empdemo.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    // define object for EmployeeDAO
    private EmployeeDAO employeeDAO;

    // create constructor for EmployeeServiceImpl and inject EmployeeDAO
    @Autowired
    public EmployeeServiceImpl(EmployeeDAO theEmployeeDAO){
        employeeDAO=theEmployeeDAO;
    }


    // define method to find all employees
    @Override
    public List<Employee> findAll(){
        return employeeDAO.findAll();
    }

    //find the employee by id
    @Override
    public Employee findById(int theId) {

        return employeeDAO.findById(theId);
    }

    @Transactional
    @Override
    public Employee save(Employee theEmployee) {
        return employeeDAO.save(theEmployee);
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        employeeDAO.deleteById(theId);
    }
}
