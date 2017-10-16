package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.CustomerRepository;
import northwind.model.Customer;
import northwind.model.Employee;

@Model
public class CustomerController {

	

	public Customer getCurrentSelectedCustomer() {
		return currentSelectedCustomer;
	}

	private String currentSelectedCustomerId;  //getter / setter
	public String getCurrentSelectedCustomerId() {
		return currentSelectedCustomerId;
	}

	public void setCurrentSelectedCustomerId(String currentSelectedCustomerId) {
		this.currentSelectedCustomerId = currentSelectedCustomerId;
	}

	private Customer currentSelectedCustomer; //getter

	public void findCustomer() {
		if( !FacesContext.getCurrentInstance().isPostback() ) {
			if( currentSelectedCustomerId  != null) {
				currentSelectedCustomer = customerRepository.find(currentSelectedCustomerId);
				if( currentSelectedCustomer == null ) {
					Messages.addGlobalInfo("There is no Customer with Customer ID {0}", 
							currentSelectedCustomerId);
				} else {
					Messages.addGlobalInfo("Successfully retreived Customer info.");
				}
			} else {
				Messages.addGlobalError("Bad request. A valid Customer ID is required.");
			}
		}		
	}
	
	@Inject
	private CustomerRepository customerRepository;
	
	private List<Customer> customers;
	
	@PostConstruct
	void init() {
		customers = customerRepository.findAll();
	}

	public List<Customer> getCustomers() {
		return customers;
	}
	
}
