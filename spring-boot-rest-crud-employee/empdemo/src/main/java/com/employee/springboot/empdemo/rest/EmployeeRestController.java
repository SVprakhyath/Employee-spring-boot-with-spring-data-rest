package com.employee.springboot.empdemo.rest;

import com.employee.springboot.empdemo.dao.EmployeeDAO;
import com.employee.springboot.empdemo.entity.Employee;
import com.employee.springboot.empdemo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//create employee rest controller
@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    // define object for EmployeeService
    private EmployeeService employeeService;
    //define object mapper object for the patch operation
    private ObjectMapper objectMapper;

    //create constructor for EmployeeRestController and inject EmployeeService
    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService, ObjectMapper theObjectMapper){
        employeeService=theEmployeeService;
        objectMapper=theObjectMapper;
    }

    //define endpoint for "/employees" and return list of employees
    @GetMapping("/employees")
    List<Employee> findAll(){
        return employeeService.findAll();
    }

    //define endpoint for "/employees/{id}" and return employee by id
    @GetMapping("/employees/{employeeId}")
   public Employee findById(@PathVariable int employeeId){
        //find the employee by id using pathvariable
        Employee theEmployee = employeeService.findById(employeeId);

        // if the employee is null the throw with exception
        if(theEmployee == null){
            throw new RuntimeException("employee id not found: " +employeeId);
        }

        return theEmployee;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee){
        //also just in case they pass id in json, set id to 0
        // this is to force a save of new item instead of update

        theEmployee.setId(0);
        //save the employee
        Employee dbEmployee = employeeService.save(theEmployee);

        //return the saved employee
        return dbEmployee;
    }

    //add mapping for PUT /employees - update existing employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee){

        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    //add mapping for patch request - for the partial json update
    @PatchMapping("/employees/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId, @RequestBody Map<String,Object> patchPayload){
        //find employee by id
        Employee tempEmployee = employeeService.findById(employeeId);

        //throw exception if the employee not found
        if(tempEmployee==null){
            throw new RuntimeException("employee id not found:"+ employeeId);
        }

        // throw exception if the patch payload contains id key
        if(patchPayload.containsKey("id")){
            throw new RuntimeException("employee id not allowed in request body: "+ employeeId);
        }

        //update the employee with the patch payload
        Employee patchedEmployee = apply(patchPayload,tempEmployee);

        //save the patched employee in db
        Employee dbEmployee = employeeService.save(patchedEmployee);

        return dbEmployee;
    }

    // method to apply the patch payload to the employee
    private Employee apply(Map<String,Object> patchpayload, Employee tempEmployee){
        //convert employee object to JSON object node
        ObjectNode employeeNode = objectMapper.convertValue(tempEmployee, ObjectNode.class);

        //convert patch payload map to JSON object node
        ObjectNode patchNode = objectMapper.convertValue(patchpayload,ObjectNode.class);

        //merge the patch update into employee node
        employeeNode.setAll(patchNode);

        return objectMapper.convertValue(employeeNode,Employee.class);
    }


    //add mapping for DELETE /employees/{employeeId} - delete employee by id
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){
        //find the employee by id
        Employee tempEmployee = employeeService.findById(employeeId);

        //if employee is not found throw exception
        if(tempEmployee==null){
            throw new RuntimeException("employee id not found:"+employeeId);
        }

        employeeService.deleteById(employeeId);
        return "deleted employee id: "+ employeeId;
    }
}
