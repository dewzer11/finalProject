package northwind.controller;

import java.math.BigDecimal;

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
	private short reorderLevel; // getter and setter
	private BigDecimal unitPrice; // getter and setter
	private short unitsInStock; // getter and setter
	private short unitsOnOrder; // getter and setter
	
	private int supplierId;
	private int categoryId;
	
	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

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

	public short getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(short reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public short getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(short unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public short getUnitsOnOrder() {
		return unitsOnOrder;
	}

	public void setUnitsOnOrder(short unitsOnOrder) {
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

			productService.createProduct(productName, supplierId, categoryId, Discontinued, productName, reorderLevel, unitPrice, reorderLevel, reorderLevel);
			Messages.addGlobalInfo("Create product was successful.");
			productName="";
		} catch (Exception e) {
			Messages.addGlobalWarn("Create product was not succesful.");
			//Messages.addGlobalError("Error creating artist with exception: {0} ", e.getMessage());  -----Use this for debugging
		}
	}
}
