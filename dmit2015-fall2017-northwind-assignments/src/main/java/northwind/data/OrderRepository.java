package northwind.data;

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

// need to add a find thing for the products something something idunno ** ask sam later
public Order findOne(int orderId) {
	return getEntityManager().createQuery(
"SELECT o from Order o JOIN FETCH o.orderDetails WHERE o.orderID =:idValue", Order.class)
			.setParameter("idValue",  orderId)
			.getSingleResult();
}
}