package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;


import northwind.data.ProductRepository;
import northwind.model.Product;
import northwind.report.ProductSales1997;
import northwind.report.TenExpensiveProducts;

@Model
public class ProductController {
	private int currentSelectedProductId;		// getter/setter
	public int getCurrentSelectedProductId() {
		return currentSelectedProductId;
	}

	public void setCurrentSelectedProductId(int currentSelectedProductId) {
		this.currentSelectedProductId = currentSelectedProductId;
	}

	public Product getCurrentSelectedProduct() {
		return currentSelectedProduct;
	}
	private Product currentSelectedProduct;	// getter
	
	public void findProduct() {
		if( !FacesContext.getCurrentInstance().isPostback() ) {
			if( currentSelectedProductId > 0 ) {
				currentSelectedProduct = productRepository.find(currentSelectedProductId);
				if( currentSelectedProduct == null ) {
					Messages.addGlobalInfo("There is no Product with ProductID {0}", 
							currentSelectedProductId);
				} else {
					Messages.addGlobalInfo("Successfully retreived Product info.");
				}
			} else {
				Messages.addGlobalError("Bad request. A valid ProductID is required.");
			}
		}		
	}
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
	private List<Product> productsByCategory;	// getter
	private int currentSelectedCategoryId;	// getter/setter
//	private category currentSelectedcategory;	// getter
	
	public void findProductsByCategory() {
		if( !FacesContext.getCurrentInstance().isPostback() ) {
			// verify that a valid categoryId was set
			if( currentSelectedCategoryId > 0) {
				productsByCategory = productRepository.findAllByCategoryId(currentSelectedCategoryId);
				if( productsByCategory.size() == 0 ) {
					Messages.addGlobalInfo("There are no products for categoryID {0}", 
							currentSelectedCategoryId);
				}
			} else {
				Messages.addGlobalError("Bad request. A valid categoryID is required.");
			}
		}
	}

	public int getCurrentSelectedCategoryId() {
		return currentSelectedCategoryId;
	}

	public void setCurrentSelectedCategoryId(int currentSelectedCategoryId) {
		this.currentSelectedCategoryId = currentSelectedCategoryId;
	}

	public List<Product> getproductsByCategory() {
		return productsByCategory;
	}
	
	public List<Product> retrieveTenExpensiveProducts() {
		return productRepository.findTenMostExpensiveProducts();
	}
	
	public List<ProductSales1997> retrieveProductSales() {
		return productRepository.findProductSales();
	}
}