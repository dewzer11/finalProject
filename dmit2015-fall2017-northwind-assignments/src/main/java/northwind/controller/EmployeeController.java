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
	private int currentSelectedEmployeeId;
	public int getCurrentSelectedEmployeeId() {
		return currentSelectedEmployeeId;
	}

	public void setCurrentSelectedEmployeeId(int currentSelectedEmployeeId) {
		this.currentSelectedEmployeeId = currentSelectedEmployeeId;
	}

	public Employee getCurrentSelectedEmployee() {
		return currentSelectedEmployee;
	}

	private Employee currentSelectedEmployee;
	
	public void findEmployee() {
		if( !FacesContext.getCurrentInstance().isPostback() ) {
			if( currentSelectedEmployeeId > 0 ) {
				currentSelectedEmployee = employeeRepository.find(currentSelectedEmployeeId);
				if( currentSelectedEmployee == null ) {
					Messages.addGlobalInfo("There is no Employee with Employee ID {0}", 
							currentSelectedEmployeeId);
				} else {
					Messages.addGlobalInfo("Successfully retreived Employee info.");
				}
			} else {
				Messages.addGlobalError("Bad request. A valid Employee ID is required.");
			}
		}		
	}
	
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
	

}