package northwind.controller;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.omnifaces.el.functions.Numbers;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;


import northwind.model.OrderDetail;
import northwind.model.Product;
import northwind.service.OrderService;
import northwind.service.ProductService;


@SuppressWarnings("serial")
@Named
@SessionScoped
public class CreateOrderController implements Serializable{
	

//	@NotNull(message="ProductId field value is required")
	private Integer currentSelectedProductId;						// +getter +setter
//	private Set<Product> products = new HashSet<>();
//	
//	
//	@Inject
//	private ProductService productService;
//
//	public String addItem() {	
//		String productIdParam = Faces.getRequestParameter("productId");
//		if( productIdParam != null && !productIdParam.isEmpty() ) {
//			int trackId = Integer.parseInt(productIdParam);
//			Product currentProduct = productService.findOne(productId);
//			if( currentProduct != null ) {
//				addItem(currentProduct);
//			}
//		}
//		return "/public/transaction/ShoppingCart.xhtml?faces-redirect=true";
//	}
//	
//	public String addItem(Product currentProduct) {
//		OrderDetail item = new OrderDetail();
//		item.setProduct(currentProduct);
//		item.setUnitPrice(unitPrice);
//		item.setQuantity(1);
//		
//		if (!items.add(item)) {
//			// Item is already in the shopping cart
//			// Get existing item and increment quantity by 1
//			OrderDetail existingItem = items.stream().filter( singleItem -> singleItem.getOrderDetailId() == item.getOrderDetailId() ).findFirst().orElse(null);
//			if (existingItem != null) {
//				existingItem.setQuantity( (short) (existingItem.getQuantity() + 1));
//				Messages.addFlashGlobalInfo("Item quantity was updated");
//			}
//		} else {
//			Messages.addFlashGlobalInfo("Item was added to cart");
//		}
//		
//	public void addProductToOrder() {
//
//		// Find the Product entity with the currentSelectedProductid
//		Product currentProduct = productService.findOne(currentSelectedProductId);
//		if (currentProduct == null) {
//			Messages.addGlobalWarn("{0} is not a valid ProductId value.", currentSelectedProductId);
//		} else {
//			if(products.add(currentProduct) )
//				Messages.addGlobalInfo("Add product was successful.");
//			else
//				Messages.addGlobalInfo("Product is already in the shopping cart");
//			currentSelectedProductId = null;
//				
//		}
//	}
//	
//	public void removeProduct(Product currentProduct) {
//		products.remove(currentProduct);
//		Messages.addGlobalInfo("Remove product was successful");
//	}
//	
////	public void createNewOrder() {
////		try {
////			orderService.createNewOrder(customer, products, employee, requiredDate, orderId, orderDate, shipAddress, shipCity, shipCountry, shipName, shipPostalCode, shipRegion);
////			Messages.addGlobalInfo("Create Order was successful");
////			
////			products.clear();
////		} catch(Exception e) {
////			Messages.addGlobalError("Create order was not successful");
////		}
////	}
//	
//
//
	public Integer getCurrentSelectedProductId() {
		return currentSelectedProductId;
	}

	public void setCurrentSelectedProductId(Integer currentSelectedProductId) {
		this.currentSelectedProductId = currentSelectedProductId;
	}

//	public Set<Product> getProducts() {
//		return products;
//	}
	
private Set<OrderDetail> items = new HashSet<>();	// +getter
	
	@Inject
	private ProductService productService;
	
	public String addItem() {	
		String productIdParam = Faces.getRequestParameter("productId");
		if( productIdParam != null && !productIdParam.isEmpty() ) {
			int productId = Integer.parseInt(productIdParam);
			Product currentProduct = productService.findOne(productId);
			if( currentProduct != null ) {
				addItem(currentProduct);
			}
		}
		return "/public/TransactionProcessing/ShoppingCart.xhtml?faces-redirect=true";
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
		return "/public/transaction/shoppingCart.xhtml?faces-redirect=true";
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
	
	
}
