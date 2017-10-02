package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import northwind.data.ProductRepository;
import northwind.model.Product;

@Model
public class ProductController {

	@Inject
	private ProductRepository productRepository;
	
	private List<Product> products;
	
	@PostConstruct
	void init() {
		products = productRepository.findAll();
	}

	public List<Product> getProducts() {
		return products;
	}
	
}