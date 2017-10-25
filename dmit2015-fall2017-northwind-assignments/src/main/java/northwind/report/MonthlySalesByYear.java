package northwind.report;

import java.math.BigDecimal;
import java.util.Date;

public class MonthlySalesByYear {

	private BigDecimal totalSales;
	private Date month;
	
	public MonthlySalesByYear(BigDecimal totalSales, Date month) {
		super();
		this.totalSales = totalSales;
		this.month = month;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}
	
	
}
