package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.hibernate.validator.constraints.NotBlank;
import org.omnifaces.util.Messages;

import northwind.data.CustomerRepository;
import northwind.model.Customer;
import northwind.service.CustomerService;


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
	
	@Inject 
	private CustomerService customerService;
	
	@NotBlank(message="Company Name is required.")
	private String companyName; // + getter + setter
	@NotBlank(message="Contact Name is required.")
	private String contactName; // + getter + setter
	@NotBlank(message="Contact Title is required.")
	private String contactTitle; // + getter + setter
	@NotBlank(message="Address is required.")
	private String address; // + getter + setter
	@NotBlank(message="City is required.")
	private String city; // + getter + setter
	@NotBlank(message="Region is required.")
	private String region; // + getter + setter
	@NotBlank(message="Postal Code is required.")
	private String postalCode; // + getter + setter
	@NotBlank(message="Country Name is required.")
	private String country; // + getter + setter
	@NotBlank(message="Phone Number is required.")
	private String phone; // + getter + setter
	@NotBlank(message="Fax Number is required.")
	private String fax; // + getter + setter


	
	public void createNewCustomer() {
		try {
			customerService.createCustomer(companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax);
			Messages.addGlobalInfo("Create customer was successful.");
			companyName = "";
		} catch(Exception e) {
			Messages.addGlobalError("Error creating customer with exception: {0}", e.getMessage());
		}
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
}
