package com.employee.springboot.empdemo.service;

import com.employee.springboot.empdemo.dao.EmployeeRepository;
import com.employee.springboot.empdemo.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    // define object for EmployeeDAO
    private EmployeeRepository employeeRepository;

    // create constructor for EmployeeServiceImpl and inject EmployeeDAO
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository){
        employeeRepository=theEmployeeRepository;
    }


    // define method to find all employees
    @Override
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    //find the employee by id
    @Override
    public Employee findById(int theId) {
        // use Optional to handle the case where the employee might not be found
        Optional<Employee> result = employeeRepository.findById(theId);

        // check if employee exists
        Employee theEmployee = null;

        // if employee is found, get the employee
        if (result.isPresent()){
            theEmployee = result.get();
        }
        else{
            //if employee not found, throw an exception
            throw new RuntimeException("Employee not found with id: "+theId);
        }
        return theEmployee;
    }

   // update employee by id
    @Override
    public Employee save(Employee theEmployee) {
        return employeeRepository.save(theEmployee);
    }

   // delete employee by id
    @Override
    public void deleteById(int theId) {
        employeeRepository.deleteById(theId);
    }
}
