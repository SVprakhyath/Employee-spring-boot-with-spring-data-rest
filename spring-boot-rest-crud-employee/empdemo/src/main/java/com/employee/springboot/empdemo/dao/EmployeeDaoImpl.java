package com.employee.springboot.empdemo.dao;

import com.employee.springboot.empdemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDAO{
    //define entity manager field for constructor injection
    private EntityManager entityManager;

    //setup constructor injection
    @Autowired
    public EmployeeDaoImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    // implement findAll method
    @Override
    public List<Employee> findAll(){
        //create a query
        TypedQuery<Employee> theQuery = entityManager.createQuery("from Employee", Employee.class);

        //execute query and get result list
        List<Employee> employees = theQuery.getResultList();

        //return employees
        return employees;
    }

    @Override
    public Employee findById(int theId) {
        //get employee by id
        Employee theEmployee = entityManager.find(Employee.class,theId);

        //return employee
        return theEmployee;
    }

    @Override
    public Employee save(Employee theEmployee) {
        // save the employee
        Employee dbEmployee = entityManager.merge(theEmployee);

        //return the employee
        return dbEmployee;
    }

    @Override
    public void deleteById(int theId) {

        //find the employee by id
        Employee theEmployee = entityManager.find(Employee.class, theId);

        //remove the employee
       entityManager.remove(theEmployee);

    }

}
