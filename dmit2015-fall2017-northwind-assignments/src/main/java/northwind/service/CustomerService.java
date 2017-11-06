package northwind.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import northwind.data.CustomerRepository;
import northwind.model.Customer;

@Stateless
public class CustomerService {

	@Inject
	private CustomerRepository customerRepository;
	
	public void createCustomer(String companyName, String contactName, String contactTitle, String address, String city, String region, String postalCode, String country, String phone, String fax) {
		Customer currentCustomer = new Customer();
		currentCustomer.setCompanyName(companyName);
		currentCustomer.setContactName(contactName);
		currentCustomer.setContactTitle(contactTitle);
		currentCustomer.setAddress(address);
		currentCustomer.setCity(city);
		currentCustomer.setRegion(region);
		currentCustomer.setPostalCode(postalCode);
		currentCustomer.setCountry(country);
		currentCustomer.setPhone(phone);
		currentCustomer.setFax(fax);
		createCustomer(currentCustomer);
		
	}
	public void createCustomer(Customer currentCustomer) {
		customerRepository.persist(currentCustomer);
	}
}
