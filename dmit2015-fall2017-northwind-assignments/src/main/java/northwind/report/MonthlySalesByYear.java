package northwind.report;

import java.math.BigDecimal;
import java.util.Date;

public class MonthlySalesByYear {

	private BigDecimal totalSales;
	private int month;
	private int Year;
	
	public int getYear() {
		return Year;
	}

	public void setYear(int year) {
		Year = year;
	}

	public MonthlySalesByYear(BigDecimal totalSales, int month, int year) {
		super();
		this.totalSales = totalSales;
		this.month = month;
	}
	public MonthlySalesByYear(Double totalSales, int month, int year) {
		super();
		this.totalSales = BigDecimal.valueOf(totalSales);
		this.month = month;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	
}
