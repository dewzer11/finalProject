package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.EmployeeRepository;
import northwind.model.Employee;
import northwind.model.Product;

@Model
public class EmployeeController {
	
	@Inject
	private EmployeeRepository employeeRepository;
	
	private List<Employee> employees;
	
	@PostConstruct
	void init() {
		employees = employeeRepository.findAll();
	}

	public List<Employee> getEmployees() {
		return employees;
	}
	private List<Product> ordersByEmployee;	// getter

	
	
	
}