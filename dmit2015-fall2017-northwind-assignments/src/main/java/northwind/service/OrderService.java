package northwind.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import northwind.data.OrderRepository;
import northwind.model.Order;

@Stateless
public class OrderService {

	@Inject
	private OrderRepository orderRepository;
	
	public void createOrder(Order currentOrder) {
		orderRepository.persist(currentOrder);
	}
	
	public void updateOrder(Order currentOrder) {
		orderRepository.persist(currentOrder);
	}
	
	public void removeOrder(Order currentOrder) {
		orderRepository.persist(currentOrder);
	}
	
	public Order findOneOrder(int orderID) {
		return orderRepository.findOne(orderID);
	}
}
