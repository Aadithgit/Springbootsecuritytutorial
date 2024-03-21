package net.springbootex.springbootbackend;

import net.springbootex.springbootbackend.model.Employee;
import net.springbootex.springbootbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cache.annotation.EnableCaching;



@SpringBootApplication
//@EnableCaching

public class SpringbootBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}
	@Autowired
	private EmployeeRepository employeeRepository;
	@Override
	public void run(String... args) throws Exception {
		/*Employee employee=new Employee();
		employee.setFirstname("Aadith");
		employee.setLastname("Prakash");
		employee.setEmailid("aadithprakash2001@gmail.com");
		employeeRepository.save(employee);*/


		/*Employee employee1=new Employee();
		employee1.setFirstname("John");
		employee1.setLastname("Cena");
		employee1.setEmailid("Johncena@gmail.com");
		employeeRepository.save(employee1);*/





	}

}

