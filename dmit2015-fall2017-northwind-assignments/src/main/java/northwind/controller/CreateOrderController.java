package northwind.controller;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.omnifaces.util.Messages;


import northwind.model.Product;
import northwind.service.OrderService;
import northwind.service.ProductService;


@SuppressWarnings("serial")
@Named
@SessionScoped
public class CreateOrderController implements Serializable{
	

	@NotNull(message="ProductId field value is required")
	private Integer currentSelectedProductId;						// +getter +setter
	private Set<Product> products = new HashSet<>();
	
	@Inject
	private OrderService orderService;
	
	@Inject
	private ProductService productService;

	
	public void addProductToOrder() {
		// Find the Product entity with the currentSelectedProductid
		Product currentProduct = productService.findOne(currentSelectedProductId);
		if (currentProduct == null) {
			Messages.addGlobalWarn("{0} is not a valid ProductId value.", currentSelectedProductId);
		} else {
			if(products.add(currentProduct) )
				Messages.addGlobalInfo("Add product was successful.");
			else
				Messages.addGlobalInfo("Product is already in the shopping cart");
			currentSelectedProductId = null;
				
		}
	}
	
	public void removeProduct(Product currentProduct) {
		products.remove(currentProduct);
		Messages.addGlobalInfo("Remove product was successful");
	}
	
//	public void createNewOrder() {
//		try {
//			orderService.createNewOrder(customer, products, employee, requiredDate, orderId, orderDate, shipAddress, shipCity, shipCountry, shipName, shipPostalCode, shipRegion);
//			Messages.addGlobalInfo("Create Order was successful");
//			
//			products.clear();
//		} catch(Exception e) {
//			Messages.addGlobalError("Create order was not successful");
//		}
//	}
	


	public Integer getCurrentSelectedProductId() {
		return currentSelectedProductId;
	}

	public void setCurrentSelectedProductId(Integer currentSelectedProductId) {
		this.currentSelectedProductId = currentSelectedProductId;
	}

	public Set<Product> getProducts() {
		return products;
	}
	
	
	
}
