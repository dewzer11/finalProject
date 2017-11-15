package northwind.controller;

import java.math.BigDecimal;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import org.omnifaces.util.Messages;
import java.util.logging.Logger;
import northwind.model.Product;
import northwind.service.ProductService;

@Model
public class CreateProductController {
	@Inject
	private Logger log;
	
	
	private Product currentNewProduct = new Product();
	public Product getCurrentNewProduct() {
		return currentNewProduct;
	}

	public void setCurrentNewProduct(Product currentNewProduct) {
		this.currentNewProduct = currentNewProduct;
	}


	private Integer supplierId;
	private Integer categoryId;
	
	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}


	@Inject
	private ProductService productService;
	
	public void createNewProduct() {
		try {

			
			productService.createProduct(currentNewProduct, supplierId, categoryId);
			Messages.addGlobalInfo("Create product was successful.");
			currentNewProduct = new Product();
			supplierId = null;
			categoryId = null;
			
		} catch (Exception e) {
			Messages.addGlobalError("Create product was not succesful.");
			log.info(e.getMessage());
			//Messages.addGlobalError("Error creating artist with exception: {0} ", e.getMessage());  -----Use this for debugging
		}
	}
}
