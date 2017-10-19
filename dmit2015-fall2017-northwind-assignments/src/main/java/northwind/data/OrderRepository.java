package northwind.data;

import java.math.BigDecimal;
import java.util.List;
import northwind.model.Order;

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

public BigDecimal findSubTotal(int orderID) {
	
	return getEntityManager().createQuery("SELECT SUM((1-o.discount) * o.unitPrice * o.quantity) FROM Order o WHERE o.order.orderID = :idValue", BigDecimal.class)
			.setParameter("idValue", orderID)
			.getSingleResult();
}
}