package northwind.service;

import java.math.BigDecimal;

import javax.inject.Inject;

import northwind.data.ProductRepository;
import northwind.model.Product;

public class ProductService {

	@Inject
	private ProductRepository productRepository;
	
	public void createProduct(String productName, int SupplierId, int CategoryId, Byte discontinued, String quantityPerUnit, short reorderLevel, BigDecimal unitPrice, short unitsInStock, short unitsOnOrder) {
		Product currentProduct = new Product();
		currentProduct.setProductName(productName);
		
		createProduct(currentProduct);
	}
	
	public void createProduct (Product currentProduct) {
		productRepository.persist(currentProduct);
	}
}
