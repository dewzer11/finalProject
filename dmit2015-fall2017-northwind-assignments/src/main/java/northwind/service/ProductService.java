package northwind.service;

import javax.inject.Inject;

import northwind.data.ProductRepository;
import northwind.model.Product;

public class ProductService {

	@Inject
	private ProductRepository productRepository;
	
	public void createProduct(String productName) {
		Product currentProduct = new Product();
		currentProduct.setProductName(productName);
		createProduct(currentProduct);
	}
	
	public void createProduct (Product currentProduct) {
		productRepository.persist(currentProduct);
	}
}
