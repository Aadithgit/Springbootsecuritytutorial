package net.springbootex.springbootbackend.controller;

import java.util.List;



import net.springbootex.springbootbackend.Service.JwtService;
import net.springbootex.springbootbackend.exception.ResourceNotfoundException;

import net.springbootex.springbootbackend.model.AuthRequest;
import net.springbootex.springbootbackend.model.Employee;
import net.springbootex.springbootbackend.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/*import org.springframework.cache.annotation.CachePut;*/
//import org.springframework.cache.annotation.Cacheable;
/*import org.springframework.cache.annotation.Cacheable;*/
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")

public class EmployeeController  {
    private static final Logger logInfo=LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping
   // @Cacheable(value="employees")
   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Employee> getallEmployees()
    {
        List<Employee>employees=employeeRepository.findAll();
        if(employees!=null)
        {
            logInfo.info("Fetched Employees");
        }
        else
        {
            logInfo.error("List is empty");
        }
        return employees;
    }
   /* @PreAuthorize("hasRole('ADMIN')")*/

   @PostMapping
  // @PreAuthorize("hasRole('ADMIN')")
    public Employee createemployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
  /*@PostMapping("/authenticate")
  public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
      return jwtService.generateToken(authRequest.getUsername());
  }*/
  @PostMapping("/authenticate")
  public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
      );
      if (authentication.isAuthenticated()) {
          String token = jwtService.generateToken(authRequest.getUsername());
          return ResponseEntity.ok(token);
      } else {
          throw new ResourceNotfoundException("Invalid credentials");
      }
  }

    @GetMapping("{id}")
   @PreAuthorize("hasAuthority('ADMIN')")
    /*@Cacheable(value="employeeCache",key="#id")*/
   // @Cacheable(value = "employees",key="#id")
   /* @Cacheable(value ="employees",key = "#id")*/

    public ResponseEntity<Employee> getemployeebyid(@PathVariable  long id)
    {
        System.out.println("Fetching data from the database");

        Employee employee=employeeRepository.findById(id)
                .orElseThrow(() ->new ResourceNotfoundException("Employee not found with id" + id ));

        return ResponseEntity.ok(employee);
    }



    //@PreAuthorize("hasRole('ADMIN')")
   /* @PostMapping
   // @PreAuthorize("hasRole('ROLE_ADMIN')") // Example role-based authorization


    public String addNewUser(@RequestBody UserInfo userInfo){
        return service.addUser(userInfo);

    }*/




    @PutMapping("{id}")
   /* @CachePut(value = "employee",key="#id")*/

    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeedetails)
    {
        Employee updateEmployee=employeeRepository.findById(id)
                .orElseThrow(() ->new ResourceNotfoundException("Employee id not found " + id));
        updateEmployee.setFirstname(employeedetails.getFirstname());
        updateEmployee.setLastname(employeedetails.getLastname());
        updateEmployee.setEmailid(employeedetails.getEmailid());
        employeeRepository.save(updateEmployee);
        return ResponseEntity.ok(updateEmployee);
    }
    @DeleteMapping("{id}")

    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id)
    {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotfoundException("Employee not exsist with id" + id));
        employeeRepository.delete(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
