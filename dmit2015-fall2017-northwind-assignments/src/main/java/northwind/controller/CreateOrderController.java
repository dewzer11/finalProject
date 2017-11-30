package northwind.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;


import org.omnifaces.el.functions.Numbers;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;


import northwind.model.Customer;
import northwind.model.Employee;
import northwind.data.CustomerRepository;
import northwind.data.EmployeeRepository;
import northwind.exception.IllegalQuantityException;
import northwind.exception.NoInvoiceLinesException;
import northwind.model.OrderDetail;
import northwind.model.Product;
import northwind.service.OrderService;
import northwind.service.ProductService;


@SuppressWarnings("serial")
@Named
@SessionScoped
public class CreateOrderController implements Serializable{
	

	@NotNull(message="ProductId field value is required")
	private Integer currentSelectedProductId;// +getter +setter
	
	@NotNull(message="CustomerId field value is required")
	private String currentSelectedCustomerId;
	
	@NotNull(message="Employee field value is required")
	private int currentSelectedEmployeeId;
	
	public int getCurrentSelectedEmployeeId() {
		return currentSelectedEmployeeId;
	}

	public void setCurrentSelectedEmployeeId(int currentSelectedEmployeeId) {
		this.currentSelectedEmployeeId = currentSelectedEmployeeId;
	}


	@Inject
	private CustomerRepository customerRepository;
	@Inject
	private EmployeeRepository employeeRepository;
	
	
	
	private String shippingName;
	private String shippingAddress;
	private String shippingCity;
	private String shippingRegion;
	private String shippingPostalCode;
	private String shippingCountry;
	
	private String companyName;
	private String contactName;
	private String contactTitle;
	private String address;
	private String city;
	private String region;
	private String postalCode;
	private String country;
	private String phone;
	private String fax;
	
	
	public void changeShippingInfo() {
		String customerId = currentSelectedCustomerId;
		Customer orderCustomer = customerRepository.find(customerId);
		shippingName = orderCustomer.getCompanyName();
		shippingAddress = orderCustomer.getAddress();
		shippingCity = orderCustomer.getCity();
		shippingRegion = orderCustomer.getRegion();
		shippingPostalCode = orderCustomer.getPostalCode();
		shippingCountry = orderCustomer.getCountry();
	}
	
	public void changeCustomerDetail() {
		String customerId = currentSelectedCustomerId;
		Customer customerDetail = customerRepository.find(customerId);
		companyName = customerDetail.getCompanyName();
		contactName = customerDetail.getContactName();
		contactTitle = customerDetail.getContactTitle();
		address = customerDetail.getAddress();
		city = customerDetail.getCity();
		region = customerDetail.getRegion();
		postalCode = customerDetail.getPostalCode();
		country = customerDetail.getCountry();
		phone = customerDetail.getPhone();
		fax = customerDetail.getFax();
		
	}


	@Inject
	private ProductService productService;
	
	public void addItemWithProductId() {
		Product currentProduct = productService.findOne(currentSelectedProductId);
		if (currentProduct != null) {
			addItem(currentProduct);	
		} else {
			Messages.addGlobalError("Invalid productId {0}", currentSelectedProductId);
		}
	}
	
	public String addItemProductIdQueryParameter() {	
		String productIdParam = Faces.getRequestParameter("productId");
		if( productIdParam != null && !productIdParam.isEmpty() ) {
			int productId = Integer.parseInt(productIdParam);
			Product currentProduct = productService.findOne(productId);
			if( currentProduct != null ) {
				addItem(currentProduct);
			} else {
				Messages.addGlobalError("Invalid productId {0}", currentSelectedProductId);
			}
		}
		return "/public/TransactionProcessing/pointOfSales.xhtml?faces-redirect=true";
	}
	
	public String addItem(Product currentProduct) {
		OrderDetail item = new OrderDetail();
		item.setProduct(currentProduct);
		item.setQuantity((short) 1);
		item.setUnitPrice( currentProduct.getUnitPrice() );
		// Add item to shopping cart
		if (!items.add(item)) {
			// Item is already in the shopping cart
			// Get existing item and increment quantity by 1
			OrderDetail existingItem = items.stream().filter( singleItem -> singleItem.getId() == item.getId() ).findFirst().orElse(null);
			if (existingItem != null) {
				existingItem.setQuantity( (short) (existingItem.getQuantity() + 1));
				Messages.addFlashGlobalInfo("Item quantity was updated");
			}
		} else {
			Messages.addFlashGlobalInfo("Item was added to cart");
		}
		
		// return navigation to the page shoppingBag.xhtml
		return "/public/TransactionProcessing/pointOfSales.xhtml?faces-redirect=true";
	}
	
	@Inject
	private OrderService orderService;
	

	
	public void createOrder() {
		try {
			String customerId = currentSelectedCustomerId;
			Customer orderCustomer = customerRepository.find(customerId);
			int employeeId = currentSelectedEmployeeId;
			Employee orderEmployee = employeeRepository.find(employeeId);
			Date today = Calendar.getInstance().getTime();
			Date requiredDate = new Timestamp(today.getTime());
			Date orderDate = new Timestamp(today.getTime());
		
			int orderId = orderService.createNewOrder(
					orderCustomer,
					orderEmployee,
					requiredDate,
					orderDate,
					shippingAddress,
					shippingCity,
					shippingCountry,
					shippingName,
					shippingPostalCode,
					shippingRegion,
					new ArrayList<>(items));
			Messages.addGlobalInfo("Successfully created order #{0}", orderId);

			// clear the form field values
			currentSelectedCustomerId = null;
			shippingAddress = null;
			shippingCity = null;
			shippingName = null;
			shippingPostalCode = null;
			shippingCountry = null;
			shippingRegion = null;
			// empty the shopping cart
		items.clear();			
		} catch( NoInvoiceLinesException | IllegalQuantityException e ) {
			Messages.addGlobalError(e.getMessage());
		} catch( Exception e ) {
			Messages.addGlobalError("Create Order was not successful");
		}
	}
	

	
	
	
	public void removeItem(OrderDetail item) {
		items.remove(item);
	}
	
	public void emptyCart() {
		items.clear();
	}

	public Set<OrderDetail> getItems() {
		return items;
	}
	
	public String getTotalPrice() {
		double totalPrice = 0;
		for(OrderDetail item : items) {
			totalPrice += item.getQuantity() * item.getUnitPrice().doubleValue();
		}
		return Numbers.formatCurrency(totalPrice, "$");
	}


	public String getCurrentSelectedCustomerId() {
		return currentSelectedCustomerId;
	}


	public void setCurrentSelectedCustomerId(String currentSelectedCustomerId) {
		this.currentSelectedCustomerId = currentSelectedCustomerId;
	}
	
	public Integer getCurrentSelectedProductId() {
		return currentSelectedProductId;
	}
	

	public void setCurrentSelectedProductId(Integer currentSelectedProductId) {
		this.currentSelectedProductId = currentSelectedProductId;
	}

	public String getShippingName() {
		return shippingName;
	}


	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}


	public String getShippingAddress() {
		return shippingAddress;
	}


	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}


	public String getShippingCity() {
		return shippingCity;
	}


	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}


	public String getShippingRegion() {
		return shippingRegion;
	}


	public void setShippingRegion(String shippingRegion) {
		this.shippingRegion = shippingRegion;
	}


	public String getShippingPostalCode() {
		return shippingPostalCode;
	}


	public void setShippingPostalCode(String shippingPostalCode) {
		this.shippingPostalCode = shippingPostalCode;
	}


	public String getShippingCountry() {
		return shippingCountry;
	}


	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
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


private Set<OrderDetail> items = new HashSet<>();	// +getter
	
	
	
	
}
