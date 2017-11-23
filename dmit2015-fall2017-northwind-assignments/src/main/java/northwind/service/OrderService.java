package northwind.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import northwind.data.OrderRepository;
import northwind.exception.IllegalQuantityException;
import northwind.exception.NoInvoiceLinesException;
import northwind.model.Customer;
import northwind.model.Employee;
import northwind.model.Order;
import northwind.model.OrderDetail;
import northwind.model.Product;

@Stateless
public class OrderService {

	@Inject
	private OrderRepository orderRepository;
	
	@Resource
	private EJBContext context;
	
	@Inject
	private EntityManager entityManager;
	
	public void createOrder(Order currentOrder) {
		orderRepository.persist(currentOrder);
	}
	
	public Order createNewOrder(Customer customer, Set<Product> orderProducts, Employee employee, 
								Date requiredDate, int orderId, Date orderDate, String shipAddress,
								String shipCity, String shipCountry, String shipName, String shipPostalCode,
								String shipRegion) {
		// Create a new Order entity and persist the entity
		Order newOrder = new Order();

		newOrder.setCustomer(customer);
		newOrder.setEmployee(employee);
		newOrder.setRequiredDate(requiredDate);
		newOrder.setOrderID(orderId);
		newOrder.setOrderDate(orderDate);
		newOrder.setShipAddress(shipAddress);
		newOrder.setShipCity(shipCity);
		newOrder.setShipCountry(shipCountry);
		newOrder.setShipName(shipName);
		newOrder.setShipPostalCode(shipPostalCode);
		newOrder.setShipRegion(shipRegion);
		orderRepository.persist(newOrder);	
		return newOrder;
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
	
//	public void CancelOrder(int orderID) {
//		if(entityManager.shippedDate != null) {
//			Messages.addGlobalInfo(" {0} has already been shipped, cannot cancel., currentSelectedOrder ");
//		}
//		else {
//			entityManager.remove(currentSelectedOrder);
//			orderRepository.remove(currentSelectedOrder);
//		}
//	}
	/////////////////////////////////////////////////////////////COMPLETE ORDER SERVICE STUFF//////////////////////////////////////
	public int completeOrder(Order newOrder, List<OrderDetail> items)
			throws NoInvoiceLinesException, IllegalQuantityException {
		// ADD THE EXCEPTIONS
		int orderId = 0;
		if (items == null || items.size() == 0) {
			context.setRollbackOnly();
			throw new NoInvoiceLinesException("There are no items in the invoice");
		}
		
		for(OrderDetail singleItem : items) {
			if (singleItem.getQuantity() > singleItem.getProduct().getUnitsInStock() || singleItem.getQuantity() < 1 ) {
				context.setRollbackOnly();
				throw new IllegalQuantityException("Invalid quantity ordered.");
			}else {
				//update units in stock of the product
				short unitsInStock=(short)(singleItem.getProduct().getUnitsInStock() - singleItem.getQuantity());
				singleItem.getProduct().setUnitsInStock(unitsInStock);
				entityManager.persist(newOrder);
				orderId = newOrder.getOrderID();
			}
			singleItem.setOrder(newOrder);
			entityManager.persist(singleItem);
		}
		return orderId;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
