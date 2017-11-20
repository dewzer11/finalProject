package northwind.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import northwind.data.CategoriesRepository;
import northwind.data.ProductRepository;
import northwind.data.SupplierRepository;
import northwind.model.Category;
import northwind.model.Product;
import northwind.model.Supplier;
@Stateless
public class ProductService {
	@Inject
	private Logger log;
	
	@Inject
	private ProductRepository productRepository;
	
	@Inject
	private SupplierRepository supplierRepository;
	
	@Inject 
	private CategoriesRepository categoriesRepository;
	
	public Product findOne(int productId) {
		Product currentProduct = null;
		try {
			currentProduct = productRepository.find(productId);
		} catch(NoResultException nre) {
			currentProduct = null;
		}
		return currentProduct;
	}
	
	public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	public List<Product> findAllByCategoryId(int categoryId){
		return productRepository.findAllByCategoryId(categoryId);
	}
	
	private void createProduct(Product newProduct) {
		
			productRepository.persist(newProduct);
		
			
		}
	
	
	public void createProduct(Product newProduct, Integer SupplierId, Integer CategoryId) {
		if(SupplierId != null) {
			Supplier currentSupplier = supplierRepository.find(SupplierId);
			newProduct.setSupplier(currentSupplier);
		}
		if (CategoryId != null) {
			Category currentCategory = categoriesRepository.find(CategoryId);
			newProduct.setCategory(currentCategory);
		}
		
		
		createProduct(newProduct);
	}
	

}
