package northwind.data;


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
			+" WHERE extract(year from o.shippedDate) = year and extract(month from o.shippedDate) = month  "
			,Double.class)
			.getSingleResult();		
}

public List<MonthlySalesByYear> findMonthSales() {
	for(int i=1; i<=12; i++){
        ;
   }
	return List<MonthlySalesByYear>;
					
}

}