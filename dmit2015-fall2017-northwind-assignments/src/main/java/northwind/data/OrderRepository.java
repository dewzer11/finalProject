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

}
