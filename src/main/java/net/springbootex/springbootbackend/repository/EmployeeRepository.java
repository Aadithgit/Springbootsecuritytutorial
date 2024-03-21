package net.springbootex.springbootbackend.repository;

import net.springbootex.springbootbackend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.data.repository.CrudRepository;


public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}


