package northwind.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductSales1997 {

	private String productName;
	private BigDecimal totalSales;
	
	
	public ProductSales1997(String productName, BigDecimal totalSales) {
		super();
		this.productName = productName;
		this.totalSales = totalSales;
	}
	
	public ProductSales1997(String productName, double totalSales) {
		super();
		this.productName = productName;
		this.totalSales = BigDecimal.valueOf(totalSales).setScale(2, RoundingMode.HALF_UP);
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}
}
