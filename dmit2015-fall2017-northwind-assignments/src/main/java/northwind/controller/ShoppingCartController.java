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

import northwind.model.Product;
import northwind.service.OrderService;
import northwind.service.ProductService;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class ShoppingCartController implements Serializable {

//	private Set<InvoiceLine> items = new HashSet<>();	// +getter
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
//		return "/public/transaction/shoppingCart.xhtml?faces-redirect=true";
//	}
//	
//	public String addItem(Product currentProduct) {
//		InvoiceLine item = new InvoiceLine();
//		item.setTrack(currentProduct);
//		item.setQuantity(1);
//		item.setUnitPrice( currentProduct.getUnitPrice() );
//
//		if (!items.add(item)) {
//
//			InvoiceLine existingItem = items.stream().filter( singleItem -> singleItem.getInvoiceLineId() == item.getInvoiceLineId() ).findFirst().orElse(null);
//			if (existingItem != null) {
//				existingItem.setQuantity( existingItem.getQuantity() + 1);
//				Messages.addFlashGlobalInfo("Item quantity was updated");
//			}
//		} else {
//			Messages.addFlashGlobalInfo("Item was added to cart");
//		}
//		
//		return "/public/transaction/ShoppingCart.xhtml?faces-redirect=true";
//	}
//	
//	}
//	
	public void emptyCart() {
		products.clear();
	}
//
//	public Set<InvoiceLine> getItems() {
//		return items;
//	}
//	
	public String getTotalPrice() {
		double totalPrice = 0;
		for(InvoiceLine item : prod) {
			totalPrice += item.getQuantity() * item.getUnitPrice().doubleValue();
		}
		return Numbers.formatCurrency(totalPrice, "$");
	}
	
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
			products.add(currentProduct);
			Messages.addGlobalInfo("Add product was successful.");
		}
	}
	
	public void removeProduct(Product currentProduct) {
		products.remove(currentProduct);
		Messages.addGlobalInfo("Remove product was successful");
	}
	
}