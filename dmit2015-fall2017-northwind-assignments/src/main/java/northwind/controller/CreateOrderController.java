package northwind.controller;

import java.io.Serializable;
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
import northwind.data.CustomerRepository;
import northwind.model.OrderDetail;
import northwind.model.Product;
import northwind.service.ProductService;


@SuppressWarnings("serial")
@Named
@SessionScoped
public class CreateOrderController implements Serializable{
	

	@NotNull(message="ProductId field value is required")
	private Integer currentSelectedProductId;// +getter +setter
	
	@NotNull(message="CustomerId field value is required")
	private Integer currentSelectedCustomerId;
	
	@Inject
	private CustomerRepository customerRepository;
	
	private String shippingName;
	private String shippingAddress;
	private String shippingCity;
	private String shippingRegion;
	private String shippingPostalCode;
	private String shippingCountry;
	
	
	public void changeShippingInfo() {
		int customerId = currentSelectedCustomerId;
		Customer orderCustomer = customerRepository.find(customerId);
		shippingName = orderCustomer.getCompanyName();
		shippingAddress = orderCustomer.getAddress();
		shippingCity = orderCustomer.getCity();
		shippingRegion = orderCustomer.getRegion();
		shippingPostalCode = orderCustomer.getPostalCode();
		shippingCountry = orderCustomer.getCountry();
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
		return "/public/transaction/pointOfSales.xhtml?faces-redirect=true";
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


	public Integer getCurrentSelectedCustomerId() {
		return currentSelectedCustomerId;
	}


	public void setCurrentSelectedCustomerId(Integer currentSelectedCustomerId) {
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


private Set<OrderDetail> items = new HashSet<>();	// +getter
	
	
	
	
}
