package northwind.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TenExpensiveProducts {
	

	private String productName;
	private BigDecimal unitPrice;
	
		public TenExpensiveProducts(String productName, BigDecimal unitPrice) {
		super();
		this.productName = productName;
		this.unitPrice = unitPrice;
	}
		
		public TenExpensiveProducts(String productName, Double unitPrice) {
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
