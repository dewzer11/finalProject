package northwind.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EmployeeSales1997 {

		private String firstName;
		private String lastName;
		private BigDecimal totalSales;
		
		
		public EmployeeSales1997(String firstName, String lastName, BigDecimal totalSales) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.totalSales = totalSales;
			
		}
		
		public EmployeeSales1997(String firstName, String lastName, double totalSales) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.totalSales = BigDecimal.valueOf(totalSales).setScale(2, RoundingMode.HALF_UP);
			
		}
		

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmployeeName() {
			return firstName + " " + lastName;
		}

//		public void setEmployeeName(String employeeName ) {
//			this.firstName = firstName;
//			this.lastName = lastName;
//			firstName + lastName = employeeName;
//		}
		
		

		public BigDecimal getTotalSales() {
			return totalSales;
		}

		public void setTotalSales(BigDecimal totalSales) {
			this.totalSales = totalSales;
		}
}
