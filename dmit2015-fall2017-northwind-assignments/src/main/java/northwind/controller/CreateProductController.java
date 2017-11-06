package northwind.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.omnifaces.util.Messages;

import northwind.service.ProductService;

@Model
public class CreateProductController {
	
	@NotBlank(message="Product Name is required")
	@Length(min=2,max=40, message="Product name must be between 2 and 40 characters")
	private String productName; // getter and setter
	@NotBlank(message="Discontinued is required")
	private Byte Discontinued; // getter and setter
	private String quantityPerUnit; // getter and setter
	private String reorderLevel; // getter and setter
	private String unitPrice; // getter and setter
	private String unitsInStock; // getter and setter
	private String unitsOnOrder; // getter and setter
	
	public Byte getDiscontinued() {
		return Discontinued;
	}

	public void setDiscontinued(Byte discontinued) {
		Discontinued = discontinued;
	}

	public String getQuantityPerUnit() {
		return quantityPerUnit;
	}

	public void setQuantityPerUnit(String quantityPerUnit) {
		this.quantityPerUnit = quantityPerUnit;
	}

	public String getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(String reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(String unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public String getUnitsOnOrder() {
		return unitsOnOrder;
	}

	public void setUnitsOnOrder(String unitsOnOrder) {
		this.unitsOnOrder = unitsOnOrder;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Inject
	private ProductService productService;
	
	public void createNewProduct() {
		try {
			productService.createProduct(productName);
			Messages.addGlobalInfo("Create product was successful.");
			productName="";
		} catch (Exception e) {
			Messages.addGlobalWarn("Create product was not succesful.");
			//Messages.addGlobalError("Error creating artist with exception: {0} ", e.getMessage());  -----Use this for debugging
		}
	}
}
