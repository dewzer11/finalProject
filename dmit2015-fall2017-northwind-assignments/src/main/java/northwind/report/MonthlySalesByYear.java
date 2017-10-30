package northwind.report;

import java.math.BigDecimal;
import java.util.Date;

public class MonthlySalesByYear {

	private BigDecimal totalSales;
	private Date month;
	private Date Year;
	
	public Date getYear() {
		return Year;
	}

	public void setYear(Date year) {
		Year = year;
	}

	public MonthlySalesByYear(BigDecimal totalSales, Date month, Date year) {
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
