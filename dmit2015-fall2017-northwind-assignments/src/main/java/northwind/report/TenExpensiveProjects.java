package northwind.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TenExpensiveProjects {
	

	private String productName;
	private BigDecimal unitPrice;
	
		public TenExpensiveProjects(String productName, BigDecimal unitPrice) {
		super();
		this.productName = productName;
		this.unitPrice = unitPrice;
	}
		
		public TenExpensiveProjects(String productName, Double unitPrice) {
		super();
		this.productName = productName;
		this.unitPrice = BigDecimal.valueOf(unitPrice).setScale(2, RoundingMode.HALF_UP);
	}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public BigDecimal getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(BigDecimal unitPrice) {
			this.unitPrice = unitPrice;
		}
	

}
