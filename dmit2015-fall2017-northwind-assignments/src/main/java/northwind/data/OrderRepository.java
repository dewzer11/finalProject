package northwind.data;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import northwind.model.Order;
import northwind.report.EmployeeSales1997;
import northwind.report.MonthlySalesByYear;

public class OrderRepository extends AbstractJpaRepository<Order> {
	private static final long serialVersionUID = 1L;

	public OrderRepository() {
		super(Order.class);
		
	}

	public List<Order> findAllByCustomerId(String customerId) {
		return getEntityManager().createQuery(
				"SELECT o FROM Order o WHERE o.customer.customerID = :idValue", Order.class)
				.setParameter("idValue", customerId)
				.getResultList();
	}

	public List<Order> findAllByEmployeeId(int employeeId) {
		return getEntityManager().createQuery(
				"SELECT o FROM Order o WHERE o.employee.employeeID = :idValue", Order.class)
				.setParameter("idValue", employeeId)
				.getResultList();
	}

public Order findOne(int orderId) {
	return getEntityManager().createQuery(
"SELECT o from Order o JOIN FETCH o.orderDetails WHERE o.orderID =:idValue", Order.class)
			.setParameter("idValue",  orderId)
			.getSingleResult();
}

public Double findSubTotal(int orderID) {
	
	return getEntityManager().createQuery("SELECT SUM((1-o.discount) * o.unitPrice * o.quantity) FROM OrderDetail o WHERE o.order.orderID = :idValue", Double.class)
			.setParameter("idValue", orderID)
			.getSingleResult();
}

public Double findSalesAmountForYearAndMonth(int year, int month) {
	return getEntityManager().createQuery(
			" SELECT SUM(od.unitPrice * od.quantity * (1-od.discount)) As TotalSales " 
			+" FROM OrderDetail od, IN (od.order) o "
			+" WHERE Year(o.shippedDate) = :year and MONTH(o.shippedDate) = :month  "
			,Double.class)
			.setParameter("year", year)
			.setParameter("month", month)
			.getSingleResult();	
}

public List<MonthlySalesByYear> findMonthSales(int year) {
	List<MonthlySalesByYear> monthlysalesbyyear = new ArrayList<>();
	for(int month=1; month<=12; month++){
		Double totalSales = findSalesAmountForYearAndMonth(year, month);
		if (totalSales == null) {
			totalSales = Double.valueOf(0);
		}
        MonthlySalesByYear monthlydata =  new MonthlySalesByYear(totalSales, month - 1, year);
        monthlysalesbyyear.add(monthlydata);
             
   }
	return monthlysalesbyyear;
					
}

}